<script>
	import { createEventDispatcher, onMount } from 'svelte';
	import { fetchModels } from '$lib/api/modelsAPI.js';

	let models = [];
	let selectedModel = '';

	const dispatch = createEventDispatcher();

	onMount(async () => {
		fetchModels().then(fetchedModels => {
			models = fetchedModels;
		});
	});


	function handleModelChange(event) {
		selectedModel = event.target.value;
		dispatch('modelChange', models.find(it => it.id === selectedModel));
	}
</script>

<select id="model-dropdown" bind:value={selectedModel} on:change={handleModelChange}>
	<option value="" disabled>Select a model</option>
	{#each models as model}
		<option value={model.id}>{model.id}</option>
	{/each}
</select>

<style>
    select {
        width: 100%;
        padding: 8px 10px;
        margin-bottom: 20px;
        box-sizing: border-box;
        border: 1px solid #ccc;
        border-radius: 4px;
    }
</style>