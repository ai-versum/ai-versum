package aiversum.backend.rest;

import aiversum.backend.config.properties.PropertiesConfig;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessageDeserializer;
import dev.langchain4j.model.StreamingResponseHandler;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaStreamingChatModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.model.vertexai.VertexAiGeminiStreamingChatModel;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/chat")
public class ChatController {
    private final WebClient webClient;
    private final PropertiesConfig propertiesConfig;

    public ChatController(WebClient webClient, PropertiesConfig propertiesConfig) {
        this.webClient = webClient;
        this.propertiesConfig = propertiesConfig;
    }

    @PostMapping(value = "/{provider}", produces = "application/json")
    public Flux<String> generate(@PathVariable String provider, @RequestBody String chatCommand) {
        return switch (provider) {
            case "ollama" -> webClient.post().uri(propertiesConfig.ollama().baseUrl() + "/api/chat")
                    .body(Mono.just(chatCommand), String.class)
                    .retrieve()
                    .bodyToFlux(String.class);
            case "openai" -> webClient.post().uri("https://api.openai.com/v1/chat/completions")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + propertiesConfig.openai().apiKey())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Mono.just(chatCommand), String.class)
                    .retrieve()
                    .bodyToFlux(String.class);
            case "genai" -> {
                String endpoint = String.format("https://generativelanguage.googleapis.com/v1beta/models/%s:generateContent?key=%s"
                        ,chatCommand // what here?
                        ,propertiesConfig.genai().apiKey());

                yield webClient.post()
                .uri(endpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(chatCommand), String.class)
                .retrieve()
                .bodyToFlux(String.class);
            }
            case null, default -> throw new IllegalArgumentException("Unknown provider: " + provider);
        };
    }

    @PostMapping(value = "/{provider}/{model}", produces = "application/json")
    public Flux<String> chat(@PathVariable String provider, @PathVariable String model, @RequestBody String chatCommand) {
        return switch (provider) {
            case "ollama" -> generateOllama(model, chatCommand);
            case "openai" -> generateOpenai(model, chatCommand);
            case "genai" -> generateGenai(model, chatCommand);
            case null, default -> throw new IllegalArgumentException("Unknown provider: " + provider);
        };
    }

    public Flux<String> generateOpenai(String model, String messages) {
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
    public Flux<String> generateGenai(String model, String messages){
        StreamingChatLanguageModel streamingChatLanguageModel = VertexAiGeminiStreamingChatModel.builder()
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
