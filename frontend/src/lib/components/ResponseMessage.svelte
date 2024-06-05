<script>
	import { marked } from 'marked';
	import CodeBlock from '$lib/components/CodeBlock.svelte';

	export let message;

	$: tokens = marked.lexer(message);

	const renderer = new marked.Renderer();

	// For code blocks with simple backticks
	// renderer.codespan = (code) => {
	// 	return `<code>${code?.replaceAll('&amp;', '&')}</code>`;
	// };

	const { extensions, ...defaults } = marked.getDefaults();

</script>

{#each tokens as token}
	{#if token.type === 'code'}
		<CodeBlock lang={token.lang} code={token.text} />
	{:else}
		{@html marked.parse(token.raw, {
			...defaults,
			gfm: true,
			breaks: true,
			renderer
		})}
	{/if}
{/each}
