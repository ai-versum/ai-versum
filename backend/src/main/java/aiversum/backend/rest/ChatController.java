package aiversum.backend.rest;

import aiversum.backend.config.properties.PropertiesConfig;
import aiversum.backend.util.MarkdownUtil;
import aiversum.backend.rest.dto.ImageQuery;
import aiversum.backend.util.MarkdownUtil;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessageDeserializer;
import dev.langchain4j.model.StreamingResponseHandler;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaStreamingChatModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.model.vertexai.VertexAiGeminiStreamingChatModel;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
    private final PropertiesConfig propertiesConfig;
    private final ImageController imageController;

    public ChatController(PropertiesConfig propertiesConfig, ImageController imageController) {
        this.propertiesConfig = propertiesConfig;
        this.imageController = imageController;
    }

    @PostMapping(value = "/{provider}/{model}", produces = "application/json")
    public Flux<String> chat(@PathVariable String provider, @PathVariable String model, @RequestBody ImageQuery imageQuery, @RequestBody String chatCommand) {
        return switch (provider) {
            case "ollama" -> generateOllama(model, chatCommand);
            case "openai" -> generateOpenai(model, imageQuery, chatCommand);
            case "vertexai" -> generateVertexai(model, chatCommand);
            case null, default -> throw new IllegalArgumentException("Unknown provider: " + provider);
        };
    }

    public Flux<String> generateOpenai(String model, ImageQuery imageQuery, String messages) {
        if (model.contains("dall")) {
            String imageUrl = imageController.generateOpenaiImage(model, imageQuery);
            return Flux.just(MarkdownUtil.wrapImage("image", imageUrl));
        }
        StreamingChatLanguageModel streamingChatLanguageModel = OpenAiStreamingChatModel.builder()
                .modelName(model)
                .apiKey(propertiesConfig.openai().apiKey())
                .temperature(0.0)
                .build();

        return generateResponse(messages, streamingChatLanguageModel);
    }

    public Flux<String> generateOllama(String model, String messages) {
        StreamingChatLanguageModel streamingChatLanguageModel = OllamaStreamingChatModel.builder()
                .baseUrl(propertiesConfig.ollama().baseUrl())
                .modelName(model)
                .temperature(0.0)
                .build();

        return generateResponse(messages, streamingChatLanguageModel);
    }

    public Flux<String> generateVertexai(String model, String messages) {
        StreamingChatLanguageModel streamingChatLanguageModel = VertexAiGeminiStreamingChatModel.builder()
                .project(propertiesConfig.vertexai().projectName())
                .location(propertiesConfig.vertexai().location())
                .modelName(model)
                .build();

        return generateResponse(messages, streamingChatLanguageModel);
    }

    @NotNull
    private static Flux<String> generateResponse(String messages, StreamingChatLanguageModel streamingChatLanguageModel) {
        var chatMessages = ChatMessageDeserializer.messagesFromJson(messages);

        return Flux.create(sink -> streamingChatLanguageModel.generate(chatMessages, new StreamingResponseHandler<>() {
            @Override
            public void onNext(String token) {
                sink.next(token); // Emit each token to the Flux
            }

            @Override
            public void onComplete(Response<AiMessage> response) {
                sink.complete(); // Complete the Flux when done
            }

            @Override
            public void onError(Throwable error) {
                sink.error(error); // Emit an error if one occurs
            }
        }), FluxSink.OverflowStrategy.BUFFER);
    }
}
