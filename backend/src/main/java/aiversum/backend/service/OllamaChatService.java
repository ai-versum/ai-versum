package aiversum.backend.service;

import org.springframework.stereotype.Service;

@Service
public class OllamaChatService implements ChatService {
    @Override
    public String getResponse(String message) {
        return "ollama";
    }
}
