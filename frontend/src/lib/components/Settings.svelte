<script>
	import { enhance } from '$app/forms';
	import { settingsStore } from '$lib/stores/SettingsStore.js';
	import { onMount } from 'svelte';

	export let onclose;

	let config = [];

	onMount(async () => {
		fetch('api/config')
			.then(async settings => {
				config = await settings.json();
				settingsStore.set(config);
			})
			.catch(error => {
				console.log(error);
			});
	});

	function camelCaseToSentence(camelCaseText) {
		// Insert a space before any upper case letter and convert the string to lowercase
		const sentence = camelCaseText.replace(/([A-Z])/g, ' $1').toLowerCase();
		// Capitalize the first letter of the sentence
		return sentence.charAt(0).toUpperCase() + sentence.slice(1);
	}

	function saveConfig() {
		return ({ update }) => {
			update().then(() => onclose());
		};
	}
</script>

<div class="modal-box resize">
	<h3 class="font-bold text-lg mb-6">Settings</h3>

	<form class="flex flex-col gap-4" method="POST" action="/api/config" use:enhance={saveConfig}>

		{#each Object.entries(config) as [key, value]}
			<label class="input input-bordered flex items-center">
				{camelCaseToSentence(key)}:
				<input name={key} type="text" class="grow ml-2" value="{value}" />
			</label>
		{/each}

		<div class="modal-action">
			<div class="tooltip" data-tip="Close dialog (Esc)">
				<button class="btn" on:click|preventDefault="{() => onclose()}">Close</button>
			</div>
			<button class="btn-primary btn" type="submit">Save</button>
		</div>
	</form>
</div>
