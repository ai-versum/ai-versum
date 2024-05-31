<script>
	import TailwindCss from '../lib/TailwindCSS.svelte';
	import MessageInput from '$lib/components/MessageInput.svelte';
	import ModelSelect from '$lib/components/ModelSelect.svelte';
	import ChatContent from '$lib/components/ChatContent.svelte';

	let selectedModel;
</script>
<TailwindCss />

<div class="flex flex-col h-screen p-3">
	<div>
		<ModelSelect on:modelChange={(e) => { selectedModel = e.detail; }} />
		<hr class="my-2" />
	</div>

	<div class="flex-grow overflow-y-auto">
		<ChatContent />
	</div>

	<MessageInput selectedModel="{selectedModel}" />
</div>
