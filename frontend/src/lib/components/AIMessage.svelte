<script>
	import { marked } from 'marked';
	import CodeBlock from '$lib/components/CodeBlock.svelte';

	export let message;

	$: tokens = marked.lexer(message);

	marked.setOptions({
		renderer: new marked.Renderer(),
		gfm: true,
		breaks: true
	});

</script>

{#each tokens as token}
	{#if token.type === 'code'}
		<CodeBlock lang={token.lang} code={token.text} />
	{:else}
		{@html marked(token.raw)}
	{/if}
{/each}
