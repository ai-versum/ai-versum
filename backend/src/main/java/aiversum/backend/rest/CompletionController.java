package aiversum.backend.rest;

import aiversum.backend.rest.dto.CompletionCommand;
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

    public CompletionController(WebClient webClient) {
        this.webClient = webClient;
    }

    @PostMapping(value = "/generate/{provider}", produces = "application/json")
    public Mono<String> generate(@PathVariable String provider, @RequestBody CompletionCommand completionCommand) {
        if (provider.equals("ollama")) {
            return webClient.post().uri("http://localhost:11434/api/generate")
                    .body(Mono.just(completionCommand), CompletionCommand.class)
                    .retrieve()
                    .bodyToMono(String.class);
        } else {
            throw new IllegalArgumentException("Unknown provider: " + provider);
        }
    }
}
