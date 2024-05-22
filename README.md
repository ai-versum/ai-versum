# ai-versum

The chat to communicate with either offline and online models like Ollama, ChatGPT, VertexAI, Claude, Gemini, etc.
The repository will bontain both frontend and backend. 

The first step is to prepare environment and communication with Ollama.

## Local setup
### Requirements
1. Install ollama with their guide: https://github.com/ollama/ollama and download any model
2. Java 21
3. Node 20

### Setup backend
1. Run `mvn install` from the repository
2. Run `BackendApplication`
3. Open http://localhost:8080/model/ollama in the browser. You should be able to see a list of installed models.

### Setup frontend
1. Run `npm install` from the frontend directory
2. Run `npm start` from the frontend directory
3. Open http://localhost:5173 in the browser. You should be able to see the model selection.

## API Documentation available
http://localhost:8080/swagger-ui/index.html