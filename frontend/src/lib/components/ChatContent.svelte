<script>
	import { chatStore } from '../stores/ChatStore.js';
	import AIMessage from '$lib/components/AIMessage.svelte';
</script>

<ul>
	{#each $chatStore as message}
		{#if (message.type === 'USER')}
			<div class="chat chat-end m-2">
				<div class="chat-bubble">
					{message.text}
				</div>
			</div>
		{:else if (message.type === 'AI')}
			<div class="chat chat-start w-full ">
				<div class="w-full max-w-full">
					<AIMessage message="{message.text}" />
				</div>
			</div>
		{:else}
			<p class:error={message.isError}>{message.text}</p>
		{/if}
	{/each}
</ul>

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