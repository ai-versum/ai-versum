<script>
	import TailwindCss from '../lib/TailwindCSS.svelte';
	import MessageInput from '$lib/components/MessageInput.svelte';
	import ModelSelect from '$lib/components/ModelSelect.svelte';
	import ChatContent from '$lib/components/ChatContent.svelte';

	let selectedModel;

	import { isAuthenticated } from '../lib/stores/auth';
	import Login from '$lib/components/Login.svelte';
	import { onMount } from 'svelte';
	import {Icon} from "@smui/common";
	import Settings from "$lib/components/Settings.svelte";

	let loading = true;
	onMount(async () => {
		await fetch('/api/auth/check-session')
			.then((response) => {
				if (response.ok) {
					isAuthenticated.set(true);
				} else {
					isAuthenticated.set(false);
				}
			})
			.catch(() => isAuthenticated.set(false))
			.finally(() => loading = false);
	});
	let settingsDialog

</script>

<TailwindCss />
{#if $isAuthenticated}
	<dialog open bind:this={settingsDialog} class="modal">
		<Settings />
	</dialog>
	<div class="flex flex-col h-screen p-3 md:max-w-3xl m-auto ">
		<div class="flex justify-between">
			<ModelSelect on:modelChange={(e) => { selectedModel = e.detail; }} />
			<Icon class="material-icons my-auto text-3xl" on:click={() => settingsDialog.showModal()}>settings</Icon>
		</div>

		<div class="flex-grow overflow-y-auto max-w-[64rem]">
			<ChatContent />
		</div>

		<MessageInput selectedModel="{selectedModel}" />
	</div>
{:else}
	{#if loading}
		<div class="absolute right-1/2 bottom-1/2  transform translate-x-1/2 translate-y-1/2 ">
			<span class="loading loading-infinity w-[5rem]"></span>
		</div>
	{:else}
		<Login />
	{/if}
{/if}
