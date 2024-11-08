<script>
	import { createEventDispatcher, onMount } from 'svelte';
	import { fetchModels } from '$lib/api/modelsAPI.js';

	let models = [];
	let selectedModel = '';

	const dispatch = createEventDispatcher();

	onMount(async () => {
		models = await fetchModels();
		const modelString = localStorage.getItem('aiversum.model');
		if (modelString) {
			const model = JSON.parse(modelString);
			if (models.some(it => it.id === model.id)) {
				selectedModel = model.id;
				dispatch('modelChange', model);
			}
		}
	});

	function handleModelChange(event) {
		const model = models.find(it => it.id === event.target.value);
		localStorage.setItem('aiversum.model', JSON.stringify(model));
		dispatch('modelChange', model);
	}

	function groupModelsByProvider(models) {
		return Object.entries(models.reduce((acc, model) => {
			acc[model.provider] = acc[model.provider] || [];
			acc[model.provider].push(model);
			return acc;
		}, {})).map(([provider, models]) => ({ provider, models }));
	}
</script>

<select
	id="model-dropdown"
	class="select w-full max-w-xs select-bordered mb-1"
	bind:value={selectedModel}
	on:change={handleModelChange}
>
	<option value="" disabled>Select a model</option>
	{#each groupModelsByProvider(models) as { provider, models: providerModels }}
		<optgroup label={provider}>
			{#each providerModels as model}
				<option value={model.id}>{model.id}</option>
			{/each}
		</optgroup>
	{/each}
</select>
