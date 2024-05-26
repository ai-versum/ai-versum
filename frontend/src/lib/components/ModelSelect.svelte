<script>
	import { createEventDispatcher, onMount } from 'svelte';
	import { fetchOpenAIModels } from '$lib/api/openaiAPI.js';
	import { fetchOllamaModels } from '$lib/api/ollamaAPI.js';

	let models = [];
	let selectedModel = '';

	const dispatch = createEventDispatcher();

	onMount(async () => {
		Promise.all([
			fetchOpenAIModels(),
			fetchOllamaModels()
		]).then(([openaiModels, ollamaModels]) => {
			const parsedOpenAIModels = openaiModels.map(model => ({
				id: model.id,
				name: model.id,
				model: model.id,
				provider: 'openai'
			}));
			const parsedOllamaModels = ollamaModels.map(model => ({
				id: model.name,
				name: model.name,
				model: model.id,
				provider: 'ollama'
			}));
			models = parsedOpenAIModels.concat(parsedOllamaModels);
		});
	});


	function handleModelChange(event) {
		selectedModel = event.target.value;
		dispatch('modelChange', models.find(it => it.id === selectedModel));
	}
</script>

<label for="model-dropdown">Choose a model:</label>
<select id="model-dropdown" bind:value={selectedModel} on:change={handleModelChange}>
	<option value="" disabled>Select a model</option>
	{#each models as model}
		<option value={model.id}>{model.name}</option>
	{/each}
</select>

<style>
    label {
        display: block;
        margin-bottom: 5px;
        font-weight: bold;
    }

    select {
        width: 100%;
        padding: 8px 10px;
        margin-bottom: 20px;
        box-sizing: border-box;
        border: 1px solid #ccc;
        border-radius: 4px;
    }
</style>