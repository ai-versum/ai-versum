<script>
	import hljs from 'highlight.js';
	import 'highlight.js/styles/github-dark.min.css';
	import { Icon } from '@smui/common';

	export let lang = '';
	export let code = '';

	let copied = false;

	const copyText = () => {
		navigator.clipboard.writeText(code).then(
			() => copied = true,
			(err) => console.error('copy failed', err)
		);
	};

	$: highlightedCode = hljs.highlightAuto(code, hljs.getLanguage(lang)?.aliases).value;

</script>

{#if code}
	<div class="dark bg-gray-950 rounded-md border-[0.5px] border-token-border-medium text-white">
		<div class="flex items-center relative px-4 py-2 text-xs font-sans justify-between rounded-t-md">
			<span>{@html lang}</span>
			<button class="flex items-center" on:click={copyText}>
				{#if copied}
					<Icon class="material-icons text-xs mr-0.5">check</Icon>
					Copied!
				{:else}
					<Icon class="material-icons text-xs mr-0.5">content_copy</Icon>
					Copy code
				{/if}
			</button>
		</div>

		<pre class="overflow-y-auto p-4">
			<code class="language-{lang} !whitespace-pre hljs">{@html highlightedCode || code}</code>
		</pre>
	</div>
{/if}
