package aiversum.backend.rest;

import aiversum.backend.config.properties.PropertiesConfig;
import aiversum.backend.rest.dto.GeminiModelResponse;
import aiversum.backend.rest.dto.Model;
import aiversum.backend.rest.dto.OllamaModelResponse;
import aiversum.backend.rest.dto.OpenAiModelResponse;
import org.reactivestreams.Publisher;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/models")
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
        if (propertiesConfig.genai().enabled()){
            models = models.mergeWith(fetchGeminiModels());
        }
        return models;
    }

    private Publisher<Model> fetchOllamaModels() {
        return webClient.get().uri(propertiesConfig.ollama().baseUrl() + "/api/tags")
                .retrieve()
                .bodyToMono(OllamaModelResponse.class)
                .flatMapMany(response -> Flux.fromIterable(response.models()))
                .map(ollamaModel -> new Model(ollamaModel.name().split(":")[0], "ollama"))
                .onErrorResume(e -> Flux.empty());
    }

    private Publisher<Model> fetchOpenAIModels() {
        return webClient.get().uri("https://api.openai.com/v1/models")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + propertiesConfig.openai().apiKey())
                .retrieve()
                .bodyToMono(OpenAiModelResponse.class)
                .flatMapMany(response -> Flux.fromIterable(response.data()))
                .map(ollamaModel -> new Model(ollamaModel.id(), "openai"))
                .onErrorResume(e -> Flux.empty());
    }
    private Publisher<Model> fetchGeminiModels(){
        return webClient
                .get().uri("https://generativelanguage.googleapis.com/v1beta/models?key="
                + propertiesConfig.genai().apiKey()) // jakie uri
                .retrieve()
                .bodyToMono(GeminiModelResponse.class)
                .flatMapMany(response -> Flux.fromIterable(response.geminiModels()))
                .map(genaiModel -> new Model(genaiModel.name(), "genai"))
                .onErrorResume(e -> Flux.empty());

    }
}