<script>
	import MessageInput from '$lib/components/MessageInput.svelte';
	import ModelSelect from '$lib/components/ModelSelect.svelte';
	import ChatContent from '$lib/components/ChatContent.svelte';

	let selectedModel;

	import { onMount } from 'svelte';
	import { Icon } from '@smui/common';
	import Settings from '$lib/components/Settings.svelte';
	import { goto } from '$app/navigation';
	import ThemeSwap from '$lib/components/ThemeSwap.svelte';

	let loading = true;
	onMount(async () => {
		await fetch('/api/auth/check-session')
			.then((response) => {
				if (!response.ok) {
					goto('/login');
				}
			})
			.catch(() => goto('/login'))
			.finally(() => loading = false);
	});
	let settingsDialog;

</script>

{#if loading}
	<div class="absolute right-1/2 bottom-1/2  transform translate-x-1/2 translate-y-1/2 ">
		<span class="loading loading-infinity w-[5rem]"></span>
	</div>
{:else}
	<dialog bind:this={settingsDialog} class="modal">
		<Settings onclose="{() => settingsDialog.close()}" />
	</dialog>
	<div class="navbar bg-base-100">
		<div class="navbar-start">
			<a class="btn btn-ghost text-xl"><img width="30" src="./favicon.png">AI-Versum</a>
		</div>
		<div class="navbar-center">
			<ModelSelect on:modelChange={(e) => { selectedModel = e.detail; }} />
		</div>
		<div class="navbar-end gap-1">
			<ThemeSwap />
			<Icon class="material-icons my-auto text-3xl cursor-pointer" on:click={() => settingsDialog.showModal()}>settings</Icon>
		</div>
	</div>
	<div class="flex flex-col h-screen p-3 md:max-w-3xl m-auto ">

		<div class="flex-grow overflow-y-auto max-w-[64rem]">
			<ChatContent />
		</div>

		<MessageInput selectedModel="{selectedModel}" />
	</div>
{/if}
