package aiversum.backend.rest;

import aiversum.backend.config.properties.PropertiesConfig;
import dev.langchain4j.data.image.Image;
import dev.langchain4j.model.image.ImageModel;
import dev.langchain4j.model.openai.OpenAiImageModel;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.data.message.*;
import dev.langchain4j.model.StreamingResponseHandler;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.net.URI;


@RestController
@RequestMapping("/api/image/")
public class ImageGenerationController {

    private final PropertiesConfig propertiesConfig;

    public ImageGenerationController(PropertiesConfig propertiesConfig) {
        this.propertiesConfig = propertiesConfig;
    }


    @PostMapping(value = "/openai/{model}", produces = "application/json")
    public URI generateOpenaiImage(@PathVariable String model, @RequestBody String prompt) {

        ImageModel imageModel = OpenAiImageModel.builder()
                .apiKey(propertiesConfig.openai().apiKey())
                .modelName(model)
//                .size("1024x1024") // czy tutaj określamy z góry wielkość czy będzie ona do ustawienia?
                .build();

        Response<Image> response = imageModel.generate(prompt);
        return response.content().url();
    }

}
