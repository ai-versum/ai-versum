package aiversum.backend.rest;

import aiversum.backend.config.properties.PropertiesConfig;
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
    private final PropertiesConfig propertiesConfig;

    public ModelController(WebClient webClient, PropertiesConfig propertiesConfig) {
        this.webClient = webClient;
        this.propertiesConfig = propertiesConfig;
    }

    @GetMapping(value = "/ollama", produces = "application/json")
    public Mono<String> listOllamaModels() {
        if (!propertiesConfig.ollama().enabled()) {
            return Mono.empty();
        }
        return webClient.get().uri(STR."\{propertiesConfig.ollama().baseUrl()}/api/tags")
                .retrieve()
                .bodyToMono(String.class);
    }

    @GetMapping(value = "/openai", produces = "application/json")
    public Mono<String> listOpenAIModels() {
        if (!propertiesConfig.openai().enabled()) {
            return Mono.empty();
        }
        return webClient.get().uri("https://api.openai.com/v1/models")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + propertiesConfig.openai().apiKey())
                .retrieve()
                .bodyToMono(String.class);
    }
}
