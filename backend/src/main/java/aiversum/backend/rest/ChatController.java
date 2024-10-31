package aiversum.backend.rest;

import aiversum.backend.rest.dto.ChatCommand;
import aiversum.backend.service.ChatService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping(value = "/{provider}/{model}", produces = "application/json")
    public Flux<String> chat(@PathVariable String provider, @PathVariable String model, @RequestBody ChatCommand chatCommand) {
       return chatService.chat(provider, model, chatCommand);
    }
}
