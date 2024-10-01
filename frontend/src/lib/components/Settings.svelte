<script>
	import { enhance } from '$app/forms';

	export let onclose;

	const mockSettings = {
		'imageSize': '1024x1024',
		'imageStyle': 'vivid',
		'openAiApiKey': 'sk-proj-xxxx'
	};

	function loadSettings() {
		return new Promise((resolve) => {
			setTimeout(() => {
				resolve(mockSettings);
			}, 1000); // simulate network latency
		});
	}

	let config = [];

	loadSettings()
		.then(settings => {
			config = settings;
		})
		.catch(error => {
			console.log(error);
		});

	function camelCaseToSentence(camelCaseText) {
		// Insert a space before any upper case letter and convert the string to lowercase
		const sentence = camelCaseText.replace(/([A-Z])/g, ' $1').toLowerCase();
		// Capitalize the first letter of the sentence
		return sentence.charAt(0).toUpperCase() + sentence.slice(1);
	}
</script>

<div class="modal-box resize">
	<h3 class="font-bold text-lg mb-6">Settings</h3>

	<form method="post" action="/api/settings" class="flex flex-col gap-4" use:enhance>

		{#each Object.entries(config) as [key, value]}
			<label class="input input-bordered flex items-center">
				{camelCaseToSentence(key)}:
				<input name={key} type="text" class="grow ml-2" placeholder="{value}" />
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