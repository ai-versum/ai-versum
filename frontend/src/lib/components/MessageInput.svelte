<script>
	export let selectedModel = '';

	let question = '';
	let isLoading = false;  // New state to track loading status

	import { createEventDispatcher } from 'svelte';

	const dispatch = createEventDispatcher();

	function handleKeyPress(event) {
		if (event.key === 'Enter') {
			sendQuestion();
		}
	}

	async function sendQuestion() {
		if (isLoading) return;  // Prevent sending if already loading
		if (!selectedModel) {
			dispatch('errorMessage', 'Please select a model first.');
			return;
		}
		if (!question) {
			dispatch('errorMessage', 'Please enter a question.');
			return;
		}

		isLoading = true; // Start loading indicator

		try {
			const response = await fetch(`/api/completion/generate/ollama`, {
				method: 'POST',
				headers: {
					'Content-Type': 'application/json'
				},
				body: JSON.stringify({ prompt: question, model: selectedModel })
			});

			if (!response.ok) {
				throw new Error('Failed to send question');
			}

			const data = await response.json();
			if (data.response) {
				dispatch('message', data.response);
			} else {
				dispatch('errorMessage', 'No response from model.');
			}
		} catch (error) {
			console.error('Error sending question:', error);
			dispatch('errorMessage', 'Failed to send question.');
		} finally {
			isLoading = false;
		}
	}
</script>

<label for="question-input">Enter your question:</label>
<input id="question-input" type="text" bind:value={question} on:keydown={handleKeyPress} autocomplete="off" />

<button on:click={sendQuestion} disabled={isLoading}>
	{#if isLoading}
		<div class="loader"></div>
	{:else}
		Send Question
	{/if}
</button>

<style>
    input[type="text"] {
        width: 100%;
        padding: 8px 10px;
        margin-bottom: 20px;
        box-sizing: border-box;
        border: 1px solid #ccc;
        border-radius: 4px;
    }
    button {
        background-color: var(--button-bg-color);
        color: var(--button-text-color);
        border: none;
        padding: 10px 20px;
        text-transform: uppercase;
        border-radius: 4px;
        cursor: pointer;
        transition: background-color 0.3s;
    }

    button:hover {
        background-color: var(--button-bg-color-darker)
    }

    button:disabled {
        background-color: #aaa; /* Grayed out */
        cursor: not-allowed;
        opacity: 0.6;
    }

    .loader {
        border: 4px solid #f3f3f3; /* Light grey */
        border-top: 4px solid #3498db; /* Blue */
        border-radius: 50%;
        width: 20px;
        height: 20px;
        animation: rotate 2s linear infinite;
    }

    @keyframes rotate {
        from {
            transform: rotate(0deg);
        }
        to {
            transform: rotate(360deg);
        }
    }

</style>