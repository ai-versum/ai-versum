<script>
	import MessageInput from '$lib/components/MessageInput.svelte';
	import ModelSelect from '$lib/components/ModelSelect.svelte';
	import ChatContent from '$lib/components/ChatContent.svelte';

	let selectedModel;
	let responseMessage = '';
	let isError = false;
</script>

<ModelSelect on:modelChange={(e) => { selectedModel = e.detail; }} />
<hr>

<ChatContent
	isError="{isError}"
	responseMessage="{responseMessage}"
/>

<MessageInput
	on:errorMessage={(e) => { responseMessage = e.detail; isError = true; }}
	on:message={(e) => { responseMessage = e.detail; isError = false; }}
	selectedModel="{selectedModel}" />
