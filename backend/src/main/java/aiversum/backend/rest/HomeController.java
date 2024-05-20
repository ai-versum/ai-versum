package aiversum.backend.rest;

import aiversum.backend.service.ChatServiceFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    private final ChatServiceFactory chatServiceFactory;

    public HomeController(ChatServiceFactory chatServiceFactory) {
        this.chatServiceFactory = chatServiceFactory;
    }

    @GetMapping("/{model}")
    public String home(@PathVariable String model) {
        return chatServiceFactory.getInstance(model).getResponse("hello");
    }
}
