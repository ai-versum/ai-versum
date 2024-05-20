package aiversum.backend.service;

import org.springframework.stereotype.Service;

@Service
public class ChatServiceFactory {
    private final OpenAIChatService openAIChatService;
    private final GeminiChatService geminiChatService;
    private final OllamaChatService ollamaChatService;
    private final VertexChatService vertexChatService;

    public ChatServiceFactory(OpenAIChatService openAIChatService, GeminiChatService geminiChatService, OllamaChatService ollamaChatService, VertexChatService vertexChatService) {
        this.openAIChatService = openAIChatService;
        this.geminiChatService = geminiChatService;
        this.ollamaChatService = ollamaChatService;
        this.vertexChatService = vertexChatService;
    }

    public ChatService getInstance(String model) {
        return switch (model) {
            case "openai" -> openAIChatService;
            case "gemini" -> geminiChatService;
            case "ollama" -> ollamaChatService;
            case "vertex" -> vertexChatService;
            default -> openAIChatService;
        };
    }
}
