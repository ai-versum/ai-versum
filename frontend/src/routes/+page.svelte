<script>
	import TailwindCss from '../lib/TailwindCSS.svelte';
	import MessageInput from '$lib/components/MessageInput.svelte';
	import ModelSelect from '$lib/components/ModelSelect.svelte';
	import ChatContent from '$lib/components/ChatContent.svelte';

	let selectedModel;
</script>
<TailwindCss />

<div class="flex flex-col h-screen p-3 md:max-w-3xl m-auto ">
	<ModelSelect on:modelChange={(e) => { selectedModel = e.detail; }} />

	<div class="flex-grow overflow-y-auto max-w-[48rem]">
		<ChatContent />
	</div>

	<MessageInput selectedModel="{selectedModel}" />
</div>
