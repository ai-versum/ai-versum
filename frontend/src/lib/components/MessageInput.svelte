<script>
	import { Input } from '@smui/textfield';
	import Paper from '@smui/paper';
	import Fab from '@smui/fab';
	import { Icon } from '@smui/common';

	let value = '';

	import { chatStore } from '../stores/ChatStore.js';
	import { fetchAiChat } from '$lib/api/chatAPI.js';

	export let selectedModel;

	let question = '';
	let isLoading = false;  // New state to track loading status

	const addNewUserMessage = (content) => {
		$chatStore = [...$chatStore, { type: 'USER', text: content }];
	};

	const addNewModelMessage = (content) => {
		if ($chatStore.length > 0 && $chatStore[$chatStore.length - 1].type === 'AI') {
			$chatStore[$chatStore.length - 1].text = content;
		} else {
			$chatStore = [...$chatStore, { type: 'AI', text: content }];
		}
	};

	function handleKeyPress(event) {
		if (event.key === 'Enter') {
			event.preventDefault();
			sendQuestion();
		}
	}

	async function sendQuestion() {
		if (isLoading) return;  // Prevent sending if already loading
		if (!selectedModel) {
			addNewModelMessage('Please select a model first.');
			return;
		}
		if (!question) {
			addNewModelMessage('Please enter a question.');
			return;
		}

		addNewUserMessage(question);
		isLoading = true; // Start loading indicator

		try {
			let messageContent = '';

			const updateMessage = (chunk) => {
				messageContent += chunk;
				addNewModelMessage(messageContent);
			};

			await fetchAiChat($chatStore, selectedModel, updateMessage);
		} catch (error) {
			addNewModelMessage(error);
		} finally {
			question = ''; // Clear the input after sending
			isLoading = false;
		}
	}
</script>

<div class="flex justify-center items-center pt-1">
	<Paper class="solo-paper" elevation={2}>
		<Icon class="material-icons">search</Icon>
		<Input
			bind:value={question}
			on:keydown={handleKeyPress}
			placeholder="Search"
			class="solo-input"
		/>
	</Paper>
	<Fab
		on:click={sendQuestion}
		disabled={value?.trim() === ''}
		color="primary"
		mini
		class="solo-fab"
	>
		<Icon class="material-icons ">arrow_forward</Icon>
	</Fab>
</div>

<style>
    * :global(.solo-paper) {
        display: flex;
        align-items: center;
        flex-grow: 1;
        max-width: 600px;
        margin: 0 12px;
        height: 48px;
    }
    * :global(.solo-paper > *) {
        display: inline-block;
        margin: 0 12px;
    }
    * :global(.solo-input) {
        flex-grow: 1;
        color: var(--mdc-theme-on-surface, #000);
    }
    * :global(.solo-input::placeholder) {
        color: var(--mdc-theme-on-surface, #000);
        opacity: 0.6;
    }
    * :global(.solo-fab) {
        flex-shrink: 0;
    }
</style>
