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
### VertexAI for Gemini
To be able to interact with Gemini models it's required to setup Project API Key by creating a new project and inclusion of API services (VertexAI API) on https://console.cloud.google.com to interact with Gemini API.

There are available four configuration variables with default values as follow:
```yml
ai.versum:
  vertexai:
    # Enables Gemini models
    enabled: true
    # Secret key used to interact with Gemini models
    apiKey: api_test_1234
    # Project name used to connect with project on Google Cloud
    projectName: test_project
    # Setting proper location for Gemini models. Gemini is served from different regions
    location: your-location
```
### Default Options
Options can be overriden on request. The default used by the application:
```yml
ai.versum:
  default-options:
    # Only ['256x256', '512x512', '1024x1024', '1024x1792', '1792x1024']
    # When using DALL·E 3, images can have a size of 1024x1024, 1024x1792 or 1792x1024 pixels.
    image-size: 1024x1024
    image-style: vivid
```
