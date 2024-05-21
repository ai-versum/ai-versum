package aiversum.backend.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/model")
public class ModelController {
    private final WebClient webClient;

    @Value("${ai.versum.ollama.baseUrl}")
    private String ollamaBaseUrl;

    public ModelController(WebClient webClient) {
        this.webClient = webClient;
    }

    @GetMapping(value = "/ollama", produces = "application/json")
    public Mono<String> listModels() {
        return webClient.get().uri(STR."\{ollamaBaseUrl}/api/tags")
                .retrieve()
                .bodyToMono(String.class);
    }
}
