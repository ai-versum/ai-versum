<script>
	import { settingsStore } from '$lib/stores/SettingsStore.js';
	import { onMount } from 'svelte';

	export let onclose;

	let config = {};

	onMount(async () => {
		fetch('api/config')
			.then(async settings => {
				config = await settings.json();
				console.log(config);
				settingsStore.set(config);
			})
			.catch(error => {
				console.log(error);
			});
	});

	const handleSubmit = (e) => {
		const formData = new FormData(e.target);

		// Set explicit false values for unchecked checkboxes
		if (!config.ollamaEnabled) formData.set('ollamaEnabled', 'false');
		if (!config.openaiEnabled) formData.set('openaiEnabled', 'false');
		if (!config.vertexaiEnabled) formData.set('vertexaiEnabled', 'false');

		fetch('/api/config', {
			method: 'POST',
			body: formData
		})
			.then(response => response.json())
			.then(data => {
				console.log('Success:', data);
				settingsStore.set(data);
				onclose();
			})
			.catch((error) => {
				console.error('Error:', error);
			});
	};
</script>

<div class="modal-box resize">
	<h3 class="font-bold text-lg mb-6">Settings</h3>

	<form class="flex flex-col gap-4" on:submit={handleSubmit}>

		<!-- Ollama Section -->
		<div class="form-control gap-4">
			<h3 class="text-lg font-medium">Ollama Settings</h3>

			<label class="label cursor-pointer">
				<span class="label-text">Enable Ollama</span>
				<input
					type="checkbox"
					class="toggle toggle-primary"
					bind:checked={config.ollamaEnabled}
					name="ollamaEnabled"
				/>
			</label>

			<label class="form-control">
				<span class="label-text">Base URL</span>
				<input
					type="text"
					class="input input-bordered w-full"
					placeholder="http://localhost:11434"
					bind:value={config.ollamaBaseUrl}
					name="ollamaBaseUrl"
				/>
			</label>
			<div class="divider"></div>
		</div>

		<!-- OpenAI Section -->
		<div class="form-control gap-4">
			<h3 class="text-lg font-medium">OpenAI Settings</h3>

			<label class="label cursor-pointer">
				<span class="label-text">Enable OpenAI</span>
				<input
					type="checkbox"
					class="toggle toggle-primary"
					bind:checked={config.openaiEnabled}
					name="openaiEnabled"
				/>
			</label>

			<label class="form-control">
				<span class="label-text">API Key</span>
				<input
					type="password"
					class="input input-bordered w-full"
					placeholder="Enter your OpenAI API key"
					bind:value={config.openaiApiKey}
					name="openaiApiKey"
				/>
			</label>
			<div class="divider"></div>
		</div>

		<!-- Vertex AI Section -->
		<div class="form-control gap-4">
			<h3 class="text-lg font-medium">Vertex AI Settings</h3>

			<label class="label cursor-pointer">
				<span class="label-text">Enable Vertex AI</span>
				<input
					type="checkbox"
					class="toggle toggle-primary"
					bind:checked={config.vertexaiEnabled}
					name="vertexaiEnabled"
				/>
			</label>

			<label class="form-control">
				<span class="label-text">API Key</span>
				<input
					type="password"
					class="input input-bordered w-full"
					placeholder="Enter your Vertex AI API key"
					bind:value={config.vertexaiApiKey}
					name="vertexaiApiKey"
				/>
			</label>

			<label class="form-control">
				<span class="label-text">Project Name</span>
				<input
					type="text"
					class="input input-bordered w-full"
					placeholder="Enter project name"
					bind:value={config.vertexaiProjectName}
					name="vertexaiProjectName"
				/>
			</label>

			<label class="form-control">
				<span class="label-text">Location</span>
				<input
					type="text"
					class="input input-bordered w-full"
					placeholder="Enter location"
					bind:value={config.vertexaiLocation}
					name="vertexaiLocation"
				/>
			</label>
			<div class="divider"></div>
		</div>

		<!-- Image Generation Settings -->
		<div class="form-control gap-4">
			<h3 class="text-lg font-medium">Image Generation Settings</h3>

			<label class="form-control">
				<span class="label-text">Image Size</span>
				<select
					class="select select-bordered w-full"
					bind:value={config.imageSize}
					name="imageSize"
				>
					<option value="256x256">256x256</option>
					<option value="512x512">512x512</option>
					<option value="1024x1024">1024x1024</option>
				</select>
			</label>

			<label class="form-control">
				<span class="label-text">Image Style</span>
				<select
					class="select select-bordered w-full"
					bind:value={config.imageStyle}
					name="imageStyle"
				>
					<option value="vivid">Vivid</option>
					<option value="natural">Natural</option>
				</select>
			</label>
			<div class="divider"></div>
		</div>

		<!-- Web search -->
		<div class="form-control gap-4">
			<h3 class="text-lg font-medium">Web Search Engine Settings</h3>

			<label class="form-control">
				<span class="label-text">Search engine provider</span>
				<select
					class="select select-bordered w-full"
					bind:value={config.searchEngine}
					name="searchEngine"
				>
					<option value="TAVILY">TAVILY</option>
				</select>
			</label>

			<label class="form-control">
				<span class="label-text">API Key</span>
				<input
					type="password"
					class="input input-bordered w-full"
					placeholder="Enter your provider API key"
					bind:value={config.searchEngineApiKey}
					name="searchEngineApiKey"
				/>
			</label>
			<div class="divider"></div>
		</div>

		<div class="modal-action">
			<div class="tooltip" data-tip="Close dialog (Esc)">
				<button class="btn" on:click|preventDefault="{() => onclose()}">Close</button>
			</div>
			<button class="btn-primary btn" type="submit">Save</button>
		</div>
	</form>
</div>
