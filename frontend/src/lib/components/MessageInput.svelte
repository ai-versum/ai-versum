<script>
	import { Icon } from '@smui/common';
	import { chatStore } from '../stores/ChatStore.js';
	import { fetchAiChat } from '$lib/api/chatAPI.js';

	let value = '';

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
	<label class="input input-bordered flex items-center gap-2 w-full">
		<Icon class="material-icons">search</Icon>
		<input type="text" class="grow" placeholder="Search" bind:value={question} on:keydown={handleKeyPress} />
		<Icon
			class="material-icons {question.trim() ==='' ? 'disabled' : 'cursor-pointer rounded-xl hover:text-secondary transition duration-150 ease-in-out'}"
			on:click={sendQuestion}>
			arrow_forward
		</Icon>
	</label>
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

    textarea {
        resize: none;
    }
</style>
