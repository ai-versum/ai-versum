<script>
  import { onMount } from 'svelte';
  let models = [];
  let selectedModel = "";

  onMount(async () => {
    try {
      const response = await fetch('/api/model/ollama');
      if (!response.ok) {
        throw new Error('Failed to fetch models');
      }
      models = (await response.json()).models;
    } catch (error) {
      console.error('Error fetching models:', error);
    }
  });

  function handleModelChange(event) {
    selectedModel = event.target.value;
    console.log('Selected model:', selectedModel);
  }
</script>
<label for="model-dropdown">Choose a model:</label>
<select id="model-dropdown" bind:value={selectedModel} on:change={handleModelChange}>
    <option value="" disabled>Select a model</option>
    {#each models as model}
        <option value={model.model}>{model.name}</option>
    {/each}
</select>

<p>Selected Model ID: {selectedModel}</p>
