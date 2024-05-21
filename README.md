# ai-versum

The chat to communicate with either offline and online models like Ollama, ChatGPT, VertexAI, Claude, Gemini, etc.
The repository will bontain both frontend and backend. 

The first step is to prepare environment and communication with Ollama.

## Local setup
1. Install ollama with their guide: https://github.com/ollama/ollama
2. Download any model
3. Run `mvn install` from the repository
4. Run `BackendApplication`
5. Open http://localhost:8080/model/ollama in the browser. You should be able to see a list of installed models.
