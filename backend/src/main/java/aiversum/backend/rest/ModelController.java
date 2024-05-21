package aiversum.backend.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/model")
public class ModelController {
    private final WebClient webClient;

    public ModelController(WebClient webClient) {
        this.webClient = webClient;
    }

    @GetMapping(value = "/ollama", produces = "application/json")
    public Mono<String> listModels() {
        return webClient.get().uri("http://localhost:11434/api/tags")
                .retrieve()
                .bodyToMono(String.class);
    }
}
