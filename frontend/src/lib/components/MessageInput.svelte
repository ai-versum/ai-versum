<script>
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

<div class="flex relative w-full rounded-3xl px-1.5 border border-gray-200 bg-white
dark:border-gray-850 dark:bg-gray-900 dark:text-gray-100">
	<textarea
		id="chat-textarea"
		class=" dark:bg-gray-900 dark:text-gray-100 outline-none w-full py-3 px-3 pl-4'} rounded-xl resize-none h-[48px]"
		placeholder={'Send a message'}
		bind:value={question}
		on:keydown={handleKeyPress}
	/>

	<div class="self-end mb-2 flex space-x-1 mr-1">
			<button
				class="{question !== ''
										? 'bg-black hover:bg-gray-900 dark:bg-white dark:text-black dark:hover:bg-gray-100 '
										: 'disabled bg-gray-100 dark:text-gray-900 dark:bg-gray-800 '} text-white transition rounded-full p-1.5 self-center"
				type="submit"
				disabled={question === ''}
			>
				<svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="size-6">
					<path stroke-linecap="round" stroke-linejoin="round" d="M6 12 3.269 3.125A59.769 59.769 0 0 1 21.485 12 59.768 59.768 0 0 1 3.27 20.875L5.999 12Zm0 0h7.5" />
				</svg>
			</button>
	</div>
</div>