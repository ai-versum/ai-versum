<script>
  import {onMount} from 'svelte';

  let models = [];
  let selectedModel = "";
  let question = "";
  let responseMessage = "";

  onMount(async () => {
    try {
      const response = await fetch('/api/model/ollama');
      if (!response.ok) {
        throw new Error('Failed to fetch models');
      }
      const data = await response.json();
      models = data.models; // Access the models array within the response
    } catch (error) {
      console.error('Error fetching models:', error);
    }
  });

  function handleModelChange(event) {
    const target = event.target;
    selectedModel = target.value;
    console.log('Selected model:', selectedModel);
  }

  async function sendQuestion() {
    if (!selectedModel) {
      responseMessage = "Please select a model first.";
      return;
    }
    if (!question) {
      responseMessage = "Please enter a question.";
      return;
    }

    try {
      const response = await fetch(`/api/completion/generate/ollama`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({prompt: question, model: selectedModel})
      });

      if (!response.ok) {
        throw new Error('Failed to send question');
      }

      const data = await response.json();
      responseMessage = data.response || 'No response from model.';
    } catch (error) {
      console.error('Error sending question:', error);
      responseMessage = 'Failed to send question.';
    }
  }
</script>

<label for="model-dropdown">Choose a model:</label>
<select id="model-dropdown" bind:value={selectedModel} on:change={handleModelChange}>
    <option value="" disabled>Select a model</option>
    {#each models as model}
        <option value={model.model}>{model.name}</option>
    {/each}
</select>
<hr>
<label for="question-input">Enter your question:</label>
<input id="question-input" type="text" bind:value={question}/>

<button on:click={sendQuestion}>Send Question</button>

<p>Response: {responseMessage}</p>

<style>
    label {
      margin-bottom: 0.5rem;
      display: block;
    }

    select, input {
      margin-bottom: 1rem;
      width: 100%;
      padding: 0.5rem;
      font-size: 1rem;
    }

    button {
      padding: 0.5rem 1rem;
      background-color: #007bff;
      color: white;
      border: none;
      cursor: pointer;
    }

    button:hover {
      background-color: #0056b3;
    }

    p {
      margin-top: 1rem;
    }

</style>