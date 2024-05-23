<script>
    import { onMount } from 'svelte';
    import { marked } from 'marked';

    let models = [];
    let selectedModel = "";
    let question = "";
    let responseMessage = "";
    let isLoading = false;  // New state to track loading status
    let isError = false;

    onMount(async () => {
        try {
            const response = await fetch('/api/model/ollama');
            if (!response.ok) {
                throw new Error('Failed to fetch models');
            }
            const data = await response.json();
            models = data.models;
        } catch (error) {
            console.error('Error fetching models:', error);
        }
    });

    function handleModelChange(event) {
        selectedModel = event.target.value;
    }

    function handleKeyPress(event) {
        if (event.key === 'Enter') {
            sendQuestion();
        }
    }

    async function sendQuestion() {
        if (isLoading) return;  // Prevent sending if already loading
        if (!selectedModel) {
            responseMessage = "Please select a model first.";
            return;
        }
        if (!question) {
            responseMessage = "Please enter a question.";
            return;
        }

        responseMessage = ""; // Clear previous messages
        isLoading = true; // Start loading indicator
        isError = false; // Reset error state

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
            responseMessage = data.response || 'No response from model.';
        } catch (error) {
            console.error('Error sending question:', error);
            responseMessage = 'Failed to send question.';
            isError = true; // Set error state
        } finally {
            isLoading = false;
        }
    }
</script>

<label for="model-dropdown">Choose a model:</label>
<select id="model-dropdown" bind:value={selectedModel} on:change={handleModelChange}>
    <option value="" disabled>Select a model</option>
    {#each models as model}
        <option value={model.model}>{model.name}</option>
    {/each}
</select>
<hr>
<label for="question-input">Enter your question:</label>
<input id="question-input" type="text" bind:value={question} on:keydown={handleKeyPress} autocomplete="off"/>

<button on:click={sendQuestion} disabled={isLoading}>
    {#if isLoading}
        <div class="loader"></div>
    {:else}
        Send Question
    {/if}
</button>

<p class:error={isError}>{@html marked(responseMessage)}</p>


<style>

    label {
        display: block;
        margin-bottom: 5px;
        font-weight: bold;
    }

    select, input[type="text"] {
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
        background-color: darken(var(--button-bg-color), 10%);
    }

    p {
        color: var(--text-color);
        background-color: #e9e9e9;
        padding: 10px;
        border-radius: 4px;
        border-left: 5px solid var(--primary-color);
    }

    button:disabled {
        background-color: #aaa; /* Grayed out */
        cursor: not-allowed;
        opacity: 0.6;
    }

    p.error {
        background-color: var(--error-color);
        color: #fff;
        border-color: #f00; /* Make it stand out more */
    }

    @keyframes rotate {
        from {
            transform: rotate(0deg);
        }
        to {
            transform: rotate(360deg);
        }
    }

    .loader {
        border: 4px solid #f3f3f3; /* Light grey */
        border-top: 4px solid #3498db; /* Blue */
        border-radius: 50%;
        width: 20px;
        height: 20px;
        animation: rotate 2s linear infinite;
    }
</style>
