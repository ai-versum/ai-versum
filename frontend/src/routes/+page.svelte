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
    :root {
        --primary-color: #4A90E2;
        --text-color: #333;
        --background-color: #f5f5f5;
        --button-bg-color: #4A90E2;
        --button-text-color: #fff;
        --error-color: #D32F2F;
    }

    body {
        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        color: var(--text-color);
        background-color: var(--background-color);
        margin: 0;
        padding: 20px;
    }

    label {
        display: block;
        margin-bottom: 5px;
        font-weight: bold;
    }

    select, input[type="text"] {
        width: 100%;
        padding: 8px 10px;
        margin-bottom: 20px;
        box-sizing: border-box;
        border: 1px solid #ccc;
        border-radius: 4px;
    }

    button {
        background-color: var(--button-bg-color);
        color: var(--button-text-color);
        border: none;
        padding: 10px 20px;
        text-transform: uppercase;
        border-radius: 4px;
        cursor: pointer;
        transition: background-color 0.3s;
    }

    button:hover {
        background-color: darken(var(--button-bg-color), 10%);
    }

    p {
        color: var(--text-color);
        background-color: #e9e9e9;
        padding: 10px;
        border-radius: 4px;
        border-left: 5px solid var(--primary-color);
    }

    p.error {
        color: var(--error-color);
        border-color: var(--error-color);
    }
</style>
