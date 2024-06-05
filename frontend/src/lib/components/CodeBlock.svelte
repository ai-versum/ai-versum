<script>
	import hljs from 'highlight.js';
	import 'highlight.js/styles/github-dark.min.css';
	import { Icon } from '@smui/common';
	// import CopyButtonPlugin from 'highlightjs-copy';

	export let lang = '';
	export let code = '';

	let copied = false;

	const copyCode = () => {
		console.log("copied");
	};

	// hljs.addPlugin(new CopyButtonPlugin({
	// 	callback: (text, el) => copied = true,
	// }))

	console.log("lang:", lang);
	$: highlightedCode = code ? hljs.highlightAuto(code, hljs.getLanguage(lang)?.aliases).value : '';
</script>

{#if code}
	<div class="dark bg-gray-950 rounded-md border-[0.5px] border-token-border-medium text-white">
		<div
			class="flex items-center relative text-token-text-secondary bg-token-main-surface-secondary px-4 py-2 text-xs font-sans justify-between rounded-t-md"
		>
			<span>{@html lang}</span>
			<button class="flex items-center" on:click={copyCode}
			>
				<Icon class="material-icons text-xs">content_copy</Icon>{copied ? 'Copied' : 'Copy Code'}</button
			>
		</div>

		<pre class="overflow-y-auto p-4"><code
			class="language-{lang} !whitespace-pre hljs">{@html highlightedCode || code}</code
		></pre>
	</div>
{/if}

<style>
    .material-icons {
        font-size: 0.5rem;
    }
</style>