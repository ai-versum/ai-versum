<script>
	import MessageInput from '$lib/components/MessageInput.svelte';
	import ModelSelect from '$lib/components/ModelSelect.svelte';
	import ChatContent from '$lib/components/ChatContent.svelte';

	let selectedModel;
</script>

<ModelSelect on:modelChange={(e) => { selectedModel = e.detail; }} />
<hr>

<ChatContent />

<MessageInput selectedModel="{selectedModel}" />
