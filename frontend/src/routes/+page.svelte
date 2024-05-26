<script>
	import { onMount } from 'svelte';
	import { marked } from 'marked';
	import MessageInput from '$lib/components/MessageInput.svelte';
	import { fetchOllamaModels } from '$lib/api/ollamaAPI.js';
	import { fetchOpenAIModels } from '$lib/api/openaiAPI.js';

	let models = [];
	let selectedModel = '';
	let responseMessage = '';
	let isError = false;

	onMount(async () => {
		Promise.all([
			fetchOpenAIModels(),
			fetchOllamaModels()
		]).then(([openaiModels, ollamaModels]) => {
			const parsedOpenAIModels = openaiModels.map(model => ({
				id: model.id,
				name: model.id,
				model: model.id,
				provider: "openai"
			}));
			const parsedOllamaModels = ollamaModels.map(model => ({
				id: model.name,
				name: model.name,
				model: model.id,
				provider: "ollama"
			}));
			models = parsedOpenAIModels.concat(parsedOllamaModels);
		});
	});

	function handleModelChange(event) {
		selectedModel = event.target.value;
	}
</script>

<label for="model-dropdown">Choose a model:</label>
<select id="model-dropdown" bind:value={selectedModel} on:change={handleModelChange}>
	<option value="" disabled>Select a model</option>
	{#each models as model}
		<option value={model.id}>{model.name}</option>
	{/each}
</select>
<hr>

<p class:error={isError}>{@html marked(responseMessage)}</p>

<MessageInput
	on:errorMessage={(e) => { responseMessage = e.detail; isError = true; }}
	on:message={(e) => { responseMessage = e.detail; isError = false; }}
	selectedModel="{models.find(it => it.id === selectedModel)}"/>

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

    p {
        color: var(--text-color);
        background-color: #e9e9e9;
        padding: 10px;
        border-radius: 4px;
        border-left: 5px solid var(--primary-color);
    }

    p.error {
        background-color: var(--error-color);
        color: #fff;
        border-color: #f00; /* Make it stand out more */
    }
</style>
