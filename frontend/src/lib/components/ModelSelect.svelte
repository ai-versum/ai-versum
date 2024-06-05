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

<select id="model-dropdown" class="select w-full max-w-xs select-bordered mb-1" bind:value={selectedModel} on:change={handleModelChange}>
	<option value="" disabled>Select a model</option>
	{#each models as model}
		<option value={model.id}>{model.id}</option>
	{/each}
</select>
