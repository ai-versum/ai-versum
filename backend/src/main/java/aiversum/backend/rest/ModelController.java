package aiversum.backend.rest;

import aiversum.backend.config.properties.PropertiesConfig;
import aiversum.backend.rest.dto.Model;
import aiversum.backend.rest.dto.OllamaModelResponse;
import aiversum.backend.rest.dto.OpenAiModelResponse;
import aiversum.backend.rest.dto.VertexaiModelResponse;
import org.reactivestreams.Publisher;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping("/api/models")
public class ModelController {
    private final WebClient webClient;
    private final PropertiesConfig propertiesConfig;

    public ModelController(WebClient webClient, PropertiesConfig propertiesConfig) {
        this.webClient = webClient;
        this.propertiesConfig = propertiesConfig;
    }

    @Cacheable("models")
    @GetMapping(produces = "application/json")
    public Flux<Model> listModels() {
        Flux<Model> models = Flux.empty();
        if (propertiesConfig.ollama().enabled()) {
            models = models.mergeWith(fetchOllamaModels());
        }
        if (propertiesConfig.openai().enabled()) {
            models = models.mergeWith(fetchOpenAIModels());
        }
        if (propertiesConfig.vertexai().enabled()) {
            models = models.mergeWith(fetchVertexaiModels());
        }
        if (propertiesConfig.anthropic().enabled()) {
            models = models.mergeWith(fetchAnthropicModels());
        }
        return models;
    }

    private Publisher<Model> fetchAnthropicModels() {
        Model sonnet = new Model("claude-3-5-sonnet-latest", "anthropic");
        return Flux.fromIterable(List.of(sonnet));
    }

    private Publisher<Model> fetchOllamaModels() {
        return webClient.get().uri(propertiesConfig.ollama().baseUrl() + "/api/tags")
                .retrieve()
                .bodyToMono(OllamaModelResponse.class)
                .flatMapMany(response -> Flux.fromIterable(response.models()))
                .map(ollamaModel -> new Model(ollamaModel.name(), "ollama"))
                .onErrorResume(e -> Flux.empty());
    }

    private Publisher<Model> fetchOpenAIModels() {
        return webClient.get().uri("https://api.openai.com/v1/models")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + propertiesConfig.openai().apiKey())
                .retrieve()
                .bodyToMono(OpenAiModelResponse.class)
                .flatMapMany(response -> Flux.fromIterable(response.data()))
                .map(ollamaModel -> new Model(ollamaModel.id(), "openai"))
                .doOnError(Throwable::printStackTrace)
                .onErrorResume(e -> Flux.empty());
    }

    private Publisher<Model> fetchVertexaiModels() {
        return webClient
                .get().uri("https://generativelanguage.googleapis.com/v1/models?key="
                        + propertiesConfig.vertexai().apiKey())
                .retrieve()
                .bodyToMono(VertexaiModelResponse.class)
                .flatMapMany(response -> Flux.fromIterable(response.models()))
                .map(vertexAiModel -> new Model(vertexAiModel.name().replace("models/", ""), "vertexai"))
                .doOnError(Throwable::printStackTrace)
                .onErrorResume(e -> Flux.empty());

    }
}
