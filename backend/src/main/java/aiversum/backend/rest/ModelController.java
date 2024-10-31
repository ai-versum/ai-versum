package aiversum.backend.rest;

import aiversum.backend.rest.dto.Model;
import aiversum.backend.service.ModelService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/models")
public class ModelController {
    private final ModelService modelService;

    public ModelController(ModelService modelService) {
        this.modelService = modelService;
    }

    @Cacheable("models")
    @GetMapping(produces = "application/json")
    public Flux<Model> listModels() {
        return modelService.fetchAllModels();

    }
}
