# AI VERSUM

## Configuration
### Ollama
To be able to interact with Ollama models it's required to setup and run Ollama locally. Follow instructions on https://github.com/ollama/ollama, or use docker command: `docker run -d -v ollama:/root/.ollama -p 11434:11434 --name ollama ollama/ollama`.

There are available two configuration variables with default values as follow:
```yml
ai.versum:
  ollama:
    # Enables Ollama models
    enabled: true
    # Default link to interact with Ollama server
    baseUrl: http://localhost:11434
```
### OpenAI
To be able to interact with OpenAI models, such as ChatGPT it's required to setup Project API Key on https://platform.openai.com/api-keys. The API key will be used to interact with openai API.

There are available two configuration variables with default values as follow:
```yml
ai.versum:
  openai:
    # Enables OpenAI models such as ChatGPT
    enabled: true
    # Secret key used to interact with OpenAI models
    apiKey: sk_test_1234567890
```
