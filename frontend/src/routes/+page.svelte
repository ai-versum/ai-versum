<script>
	import { marked } from 'marked';
	import MessageInput from '$lib/components/MessageInput.svelte';
	import ModelSelect from '$lib/components/ModelSelect.svelte';

	let selectedModel;
	let responseMessage = '';
	let isError = false;
</script>

<ModelSelect on:modelChange={(e) => { selectedModel = e.detail; }} />
<hr>

<p class:error={isError}>{@html marked(responseMessage)}</p>

<MessageInput
	on:errorMessage={(e) => { responseMessage = e.detail; isError = true; }}
	on:message={(e) => { responseMessage = e.detail; isError = false; }}
	selectedModel="{selectedModel}" />

<style>
    p {
        color: var(--text-color);
        background-color: #e9e9e9;
        padding: 10px;
        border-radius: 4px;
        border-left: 5px solid var(--primary-color);
    }

    p.error {
        background-color: var(--error-color);
        color: #fff;
        border-color: #f00; /* Make it stand out more */
    }
</style>
