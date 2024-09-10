package aiversum.backend.rest;

import aiversum.backend.config.properties.PropertiesConfig;
import aiversum.backend.rest.dto.ChatCommand;
import dev.langchain4j.data.image.Image;
import dev.langchain4j.model.image.ImageModel;
import dev.langchain4j.model.openai.OpenAiImageModel;
import dev.langchain4j.model.output.Response;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/image/")
public class ImageController {

    private final PropertiesConfig propertiesConfig;

    public ImageController(PropertiesConfig propertiesConfig) {
        this.propertiesConfig = propertiesConfig;
    }


    @PostMapping(value = "/openai/{model}", produces = "application/json")
    public String generateOpenaiImage(@PathVariable String model, @RequestBody ChatCommand chatCommand)  {

        Optional<Map<String, String>> options = Optional.ofNullable(chatCommand.options());
        String imageSize = options
                .map(it -> it.getOrDefault("size", propertiesConfig.defaultOptions().imageSize()))
                .orElse(propertiesConfig.defaultOptions().imageSize());
        String imageStyle = options
                .map(it -> it.getOrDefault("style", propertiesConfig.defaultOptions().imageStyle()))
                .orElse(propertiesConfig.defaultOptions().imageStyle());
        ImageModel imageModel = OpenAiImageModel.builder()
                .apiKey(propertiesConfig.openai().apiKey())
                .modelName(model)
                .size(imageSize)
                .style(imageStyle)
                .build();
        Response<Image> response = imageModel.generate(chatCommand.messages());
        return response.content().url().toString();
    }

}