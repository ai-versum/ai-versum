<script>
import { marked } from 'marked';

export let responseMessage = '';
export let isError = false;
</script>

<p class:error={isError}>{@html marked(responseMessage)}</p>

<style>
    p {
        color: var(--text-color);
        background-color: #e9e9e9;
        padding: 10px;
        border-radius: 4px;
        border-left: 5px solid var(--primary-color);
    }

    p.error {
        background-color: var(--error-color);
        color: #fff;
        border-color: #f00; /* Make it stand out more */
    }
</style>