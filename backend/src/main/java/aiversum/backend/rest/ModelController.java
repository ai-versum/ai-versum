package aiversum.backend.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/models")
public class ModelController {
    private final WebClient webClient;

    @Value("${ai.versum.ollama.baseUrl}")
    private String ollamaBaseUrl;

    @Value("${ai.versum.ollama.enabled}")
    private boolean ollamaEnabled;

    @Value("${ai.versum.openai.enabled}")
    private boolean openaiEnabled;

    @Value("${ai.versum.openai.apiKey}")
    private String openaiApiKey;

    public ModelController(WebClient webClient) {
        this.webClient = webClient;
    }

    @GetMapping(value = "/ollama", produces = "application/json")
    public Mono<String> listOllamaModels() {
        if (!ollamaEnabled) {
            return Mono.empty();
        }
        return webClient.get().uri(STR."\{ollamaBaseUrl}/api/tags")
                .retrieve()
                .bodyToMono(String.class);
    }

    @GetMapping(value = "/openai", produces = "application/json")
    public Mono<String> listOpenAIModels() {
        if (!openaiEnabled) {
            return Mono.empty();
        }
        return webClient.get().uri("https://api.openai.com/v1/models")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + openaiApiKey)
                .retrieve()
                .bodyToMono(String.class);
    }
}
