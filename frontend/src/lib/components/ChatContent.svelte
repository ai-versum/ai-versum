<script>
	import { marked } from 'marked';
	import { chatStore } from '../stores/ChatStore.js';
	import Card from '@smui/card';
</script>

<ul>
	{#each $chatStore as message}
		<div class="mb-3">
			{#if (message.type === 'USER')}
				<Card class="bg-gray-200 w-1/2 right-0 ml-auto rounded-2xl" padded>{@html marked(message.text)}</Card>
			{:else if (message.type === 'AI')}
				<Card padded>{@html marked(message.text)}</Card>
			{:else}
				<p class:error={message.isError}>{@html marked(message.text)}</p>
			{/if}
		</div>
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