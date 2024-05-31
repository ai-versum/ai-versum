<script>
	import { Input } from '@smui/textfield';
	import Paper from '@smui/paper';
	import Fab from '@smui/fab';
	import { Icon } from '@smui/common';

	let value = '';

	import { fetchOllamaChat } from '$lib/api/ollamaAPI.js';
	import { fetchOpenAIChat } from '$lib/api/openaiAPI.js';
	import { chatStore } from '../stores/ChatStore.js';

	export let selectedModel;

	let question = '';
	let isLoading = false;  // New state to track loading status

	const addNewUserMessage = (content, isError = false) => {
		$chatStore = [...$chatStore, { role: 'user', content, isError }];
	};

	const addNewModelMessage = (content, isError = false) => {
		if ($chatStore.length > 0 && $chatStore[$chatStore.length - 1].role === 'assistant') {
			$chatStore[$chatStore.length - 1].content = content;
		} else {
			$chatStore = [...$chatStore, { role: 'assistant', content, isError }];
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
			addNewModelMessage('Please select a model first.', true);
			return;
		}
		if (!question) {
			addNewModelMessage('Please enter a question.', true);
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

			if (selectedModel.provider === 'ollama') {
				await fetchOllamaChat($chatStore, selectedModel.id, updateMessage);
			} else if (selectedModel.provider === 'openai') {
				await fetchOpenAIChat($chatStore, selectedModel.id, updateMessage);
			}
		} catch (error) {
			addNewModelMessage(error, true);
		} finally {
			question = ''; // Clear the input after sending
			isLoading = false;
		}
	}
</script>

<div class="flex justify-center items-center">
	<Paper class="solo-paper" elevation={6}>
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
        padding: 0 12px;
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
