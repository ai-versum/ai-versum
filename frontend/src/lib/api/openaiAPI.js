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

export const fetchOpenAIChat = async (chatStore, model, onDataReceived) => {
	try {
		const response = await fetch(`/api/chat/openai`, {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify({ messages: [...chatStore], model , stream: true})
		});

		if (!response.ok) {
			throw new Error('Failed to send question');
		}

		const reader = response.body.getReader();
		const decoder = new TextDecoder('utf-8');
		let buffer = '';

		while (true) {
			const { done, value } = await reader.read();
			if (done) {
				break;
			}
			buffer += decoder.decode(value, { stream: true });

			let boundary;
			while ((boundary = buffer.indexOf('}{')) !== -1) {
				const chunk = buffer.slice(0, boundary + 1);
				buffer = buffer.slice(boundary + 1);
				console.debug('Chunk:', chunk);
				onDataReceived(JSON.parse(chunk).choices[0].delta.content);
			}
		}
	} catch (error) {
		console.error('Error sending question:', error);
		throw error;
	}
};