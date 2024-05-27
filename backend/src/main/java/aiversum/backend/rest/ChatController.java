package aiversum.backend.rest;

import aiversum.backend.config.properties.PropertiesConfig;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
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
            case "ollama" -> webClient.post().uri(STR."\{propertiesConfig.ollama().baseUrl()}/api/chat")
                    .body(Mono.just(chatCommand), String.class)
                    .retrieve()
                    .bodyToFlux(String.class);
            case "openai" -> webClient.post().uri("https://api.openai.com/v1/chat/completions")
                    .header(HttpHeaders.AUTHORIZATION, STR."Bearer \{propertiesConfig.openai().apiKey()}")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Mono.just(chatCommand), String.class)
                    .retrieve()
                    .bodyToFlux(String.class);
            case null, default -> throw new IllegalArgumentException("Unknown provider: " + provider);
        };
    }
}
