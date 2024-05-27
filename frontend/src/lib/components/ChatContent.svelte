<script>
	import { marked } from 'marked';
	import { chatStore } from '../stores/ChatStore.js';
</script>

<ul>
	{#each $chatStore as message}
		{#if (message.role === 'user')}
			<p class:error={message.isError} style="color: blue;">{@html marked(message.content)}</p>
		{:else if (message.role === 'assistant')}
			<p class:error={message.isError} style="color: green;">{@html marked(message.content)}</p>
		{:else}
			<p class:error={message.isError}>{@html marked(message.content)}</p>
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