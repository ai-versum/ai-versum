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

	function groupModelsByProvider(models) {
		const groupedModels = {};

		// Group models by provider
		models.forEach((model) => {
			if (!groupedModels[model.provider]) {
				groupedModels[model.provider] = [];
			}
			groupedModels[model.provider].push(model);
		});

		// Convert the grouped object into an array format suitable for iteration
		return Object.entries(groupedModels).map(([provider, models]) => ({ provider, models }));
	}
</script>

<select id="model-dropdown" class="select w-full max-w-xs select-bordered mb-1" bind:value={selectedModel}
				on:change={handleModelChange}>
	<option value="" disabled>Select a model</option>
	{#each groupModelsByProvider(models) as { provider, models: providerModels }}
		<optgroup label={provider}>
			{#each providerModels as model}
				<option value={model.id}>{model.id}</option>
			{/each}
		</optgroup>
	{/each}
</select>
