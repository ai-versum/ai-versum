export const fetchOllamaModels = async () => {
	try {
		const response = await fetch('/api/models/ollama');
		if (!response.ok) {
			throw new Error('Failed to fetch models');
		}
		const data = await response.json();
		return data.models;
	} catch (error) {
		console.error('Error fetching models:', error);
	}
}

export const fetchOllamaCompletion = async (question, model) => {
	try {
		const response = await fetch(`/api/completion/generate/ollama`, {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify({ prompt: question, model })
		});

		if (!response.ok) {
			throw new Error('Failed to send question');
		}

		const data = await response.json();
		if (data.response) {
			return data.response;
		} else {
			throw new Error('No response from model.');
		}
	} catch (error) {
		console.error('Error sending question:', error);
		throw error;
	}
}