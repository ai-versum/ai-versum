package aiversum.backend.rest;

import aiversum.backend.config.properties.PropertiesConfig;
import aiversum.backend.rest.dto.CompletionCommand;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/completion")
public class CompletionController {
    private final WebClient webClient;
    private final PropertiesConfig propertiesConfig;

    @Value("${ai.versum.ollama.baseUrl}")
    private String ollamaBaseUrl;

    public CompletionController(WebClient webClient, PropertiesConfig propertiesConfig) {
        this.webClient = webClient;
        this.propertiesConfig = propertiesConfig;
    }

    @PostMapping(value = "/generate/{provider}", produces = "application/json")
    public Mono<String> generate(@PathVariable String provider, @RequestBody CompletionCommand completionCommand) {
        return switch (provider) {
            case "ollama" -> webClient.post().uri(ollamaBaseUrl + "/api/generate")
                    .body(Mono.just(completionCommand), CompletionCommand.class)
                    .retrieve()
                    .bodyToMono(String.class);
            case "openai" -> {
                String body = """
                        {
                            "model": "%s",
                                "messages": [
                                  {
                                    "role": "user",
                                    "content": "%s"
                                  }
                                ]
                        }
                        """.formatted(completionCommand.model(), completionCommand.prompt());
                yield webClient.post().uri("https://api.openai.com/v1/chat/completions")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + propertiesConfig.openai().apiKey())
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(Mono.just(body), String.class)
                        .retrieve()
                        .bodyToMono(String.class);
            }
            case "genai" -> {
                String body = STR."""
                        {
                            "contents":[
                                {"parts":[
                                    {
                                        "text":"\{completionCommand.prompt()}"
                                    }
                                ],
                                  "role": "user"
                                 }
                        }
                        """;
                yield webClient.post()
                        .uri(STR."https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent?key=\{propertiesConfig.genai().GOOGLE_API_KEY()}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(Mono.just(body), String.class)
                        .retrieve()
                        .bodyToMono(String.class);
            }
            case null, default -> throw new IllegalArgumentException("Unknown provider: " + provider);
        };
    }
}
