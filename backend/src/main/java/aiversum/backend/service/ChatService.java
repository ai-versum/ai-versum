package aiversum.backend.service;

import aiversum.backend.rest.ImageController;
import aiversum.backend.rest.dto.ChatCommand;
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
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

@Service
public class ChatService {
    private final ImageController imageController;
    private final UserConfigService userConfigService;

    public ChatService(ImageController imageController, UserConfigService userConfigService) {
        this.imageController = imageController;
        this.userConfigService = userConfigService;
    }

    public Flux<String> chat(String provider, String model, ChatCommand chatCommand) {
        return switch (provider) {
            case "ollama" -> generateOllama(model, chatCommand);
            case "openai" -> generateOpenai(model, chatCommand);
            case "vertexai" -> generateVertexai(model, chatCommand);
            case null, default -> throw new IllegalArgumentException("Unknown provider: " + provider);
        };
    }

    public Flux<String> generateOpenai(String model, ChatCommand chatCommand)  {
        var apiKey = userConfigService.getConfig().getOpenaiApiKey();
        if (model.contains("dall")) {
            String imageUrl = imageController.generateOpenaiImage(model, chatCommand);
            return Flux.just(MarkdownUtil.wrapImage("image", imageUrl));
        }
        StreamingChatLanguageModel streamingChatLanguageModel = OpenAiStreamingChatModel.builder()
                .modelName(model)
                .apiKey(apiKey)
                .temperature(0.0)
                .build();

        return generateResponse(chatCommand.messages(), streamingChatLanguageModel);
    }

    public Flux<String> generateOllama(String model, ChatCommand chatCommand) {
        var userConfig = userConfigService.getConfig();

        StreamingChatLanguageModel streamingChatLanguageModel = OllamaStreamingChatModel.builder()
                .baseUrl(userConfig.getOllamaBaseUrl())
                .modelName(model)
                .temperature(0.0)
                .build();

        return generateResponse(chatCommand.messages(), streamingChatLanguageModel);
    }

    public Flux<String> generateVertexai(String model, ChatCommand chatCommand) {
        var userConfig = userConfigService.getConfig();

        StreamingChatLanguageModel streamingChatLanguageModel = VertexAiGeminiStreamingChatModel.builder()
                .project(userConfig.getVertexaiProjectName())
                .location(userConfig.getVertexaiLocation())
                .modelName(model)
                .build();

        return generateResponse(chatCommand.messages(), streamingChatLanguageModel);
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
