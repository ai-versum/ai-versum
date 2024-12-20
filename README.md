# ai-versum

The chat to communicate with either offline or online models like Ollama, ChatGPT, VertexAI, Claude, Gemini, etc.
The repository will contain both frontend and backend. 

## Features

* 🤖 OpenAI support for chat streaming and model listing
* 🤖 Ollama support for chat streaming and model listing
* 🤖 Anthropic support for chat streaming and model listing
* 🤖 Vertex AI and Gemini support for chat streaming and model listing
* 🖼️ Image generation through Dall-e
* 💻 Code Syntax Highlighting: Enjoy enhanced code readability with our syntax highlighting feature.

## Documentation
Is built with GitHub pages and available [here](https://ai-versum.github.io/ai-versum/).

API Documentation available after app is up and running: http://localhost:8080/swagger-ui/index.html

## Local setup
### Requirements
1. You need either Ollama, OpenAI API, Gemini API or Claude API setup to play with the application. See more in [docs](https://ai-versum.github.io/ai-versum/#configuration).
2. Java 21
3. Node 20

### Setup backend
1. Run `mvn install` from the repository
2. Run `BackendApplication`
3. Open http://localhost:8080/model/ollama in the browser. You should be able to see a list of installed models.

### Setup frontend
1. Run `npm install` from the frontend directory
2. Run `npm run dev` from the frontend directory
3. Open http://localhost:5173 in the browser. You should be able to see the model selection.
