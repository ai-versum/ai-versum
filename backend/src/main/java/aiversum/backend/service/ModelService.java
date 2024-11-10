package aiversum.backend.service;

import aiversum.backend.rest.dto.*;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
public class ModelService {
    private final WebClient webClient;
    private final UserConfigService userConfigService;

    public ModelService(WebClient webClient, UserConfigService userConfigService) {
        this.webClient = webClient;
        this.userConfigService = userConfigService;
    }

    public Flux<Model> fetchAllModels() {
        var userConfig = userConfigService.getConfig();
        Flux<Model> models = Flux.empty();
        if (userConfig.isOllamaEnabled()) {
            models = models.mergeWith(fetchOllamaModels(userConfig.getOllamaBaseUrl()));
        }
        if (userConfig.isOpenaiEnabled()) {
            models = models.mergeWith(fetchOpenAIModels(userConfig.getOpenaiApiKey()));
        }
        if (userConfig.isVertexaiEnabled()){
            models = models.mergeWith(fetchVertexaiModels(userConfig.getVertexaiApiKey()));
        }
        if(userConfig.isAnthropicEnabled()){
            models = models.mergeWith(fetchAnthropicModels());
        }
        return models;
    }

    private Publisher<Model> fetchOllamaModels(String baseUrl) {
        return webClient.get().uri(baseUrl + "/api/tags")
                .retrieve()
                .bodyToMono(OllamaModelResponse.class)
                .flatMapMany(response -> Flux.fromIterable(response.models()))
                .map(ollamaModel -> new Model(ollamaModel.name(), "ollama"))
                .onErrorResume(e -> Flux.empty());
    }

    private Publisher<Model> fetchOpenAIModels(String apiKey) {
        return webClient.get().uri("https://api.openai.com/v1/models")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .retrieve()
                .bodyToMono(OpenAiModelResponse.class)
                .flatMapMany(response -> Flux.fromIterable(response.data()))
                .map(ollamaModel -> new Model(ollamaModel.id(), "openai"))
                .doOnError(Throwable::printStackTrace)
                .onErrorResume(e -> Flux.empty());
    }
    private Publisher<Model> fetchVertexaiModels(String apiKey){
        return webClient
                .get().uri("https://generativelanguage.googleapis.com/v1/models?key=" + apiKey)
                .retrieve()
                .bodyToMono(VertexaiModelResponse.class)
                .flatMapMany(response -> Flux.fromIterable(response.models()))
                .map(vertexAiModel -> new Model(vertexAiModel.name().replace("models/", ""), "vertexai"))
                .doOnError(Throwable::printStackTrace)
                .onErrorResume(e -> Flux.empty());

    }
    private Publisher<Model> fetchAnthropicModels(){
        Model sonnet = new Model("claude-3-5-sonnet-20241022", "anthropic");
        return Flux.fromIterable(List.of(sonnet));
    }
}
