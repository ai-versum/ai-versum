export const fetchOpenAIModels = async () => {
	try {
		const response = await fetch('/api/models/openai');
		if (!response.ok) {
			throw new Error('Failed to fetch models');
		}
		const data = await response.json();
		return data.data;
	} catch (error) {
		console.error('Error fetching models:', error);
	}
}

export const fetchOpenAICompletion = async (question, model) => {
	try {
		const response = await fetch(`/api/completion/generate/openai`, {
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
		const content = data.choices[0]?.message?.content;
		if (content) return content;
		throw new Error('No response from model.');
	} catch (error) {
		console.error('Error sending question:', error);
		throw error;
	}
}