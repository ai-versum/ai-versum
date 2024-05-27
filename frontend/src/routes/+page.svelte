<script>
	import TailwindCss from '../lib/TailwindCSS.svelte';
	import MessageInput from '$lib/components/MessageInput.svelte';
	import ModelSelect from '$lib/components/ModelSelect.svelte';
	import ChatContent from '$lib/components/ChatContent.svelte';

	let selectedModel;
</script>
<TailwindCss />

<div class="flex flex-col h-screen">
	<div>
		<ModelSelect on:modelChange={(e) => { selectedModel = e.detail; }} />
		<hr class="my-2" />
	</div>

	<div class="flex-grow overflow-y-auto">
		<ChatContent />
	</div>

	<div class="sticky bottom-0 bg-white">
		<MessageInput selectedModel="{selectedModel}" />
	</div>
</div>

