<script>
	import { chatStore } from '../stores/ChatStore.js';
	import Card from '@smui/card';
	import AIMessage from '$lib/components/AIMessage.svelte';
</script>

<ul>
	{#each $chatStore as message}
		<div class="mb-3 mx-auto flex flex-1 gap-3 text-base juice:gap-4 juice:md:gap-6 md:max-w-3xl lg:max-w-[40rem] xl:max-w-[48rem]">
			{#if (message.type === 'USER')}
				<Card class="bg-gray-200 w-1/2 right-0 ml-auto rounded-2xl" padded>{message.text}</Card>
			{:else if (message.type === 'AI')}
				<Card padded>
					<AIMessage message="{message.text}" />
				</Card>
			{:else}
				<p class:error={message.isError}>{message.text}</p>
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