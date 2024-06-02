export const fetchOllamaCompletion = async (question, model) => {
	try {
		const response = await fetch(`/api/completion/generate/ollama`, {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify({ prompt: question, model, stream: true })
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
};

export const fetchOllamaChat = async (chatStore, model, onDataReceived) => {
	try {
		const response = await fetch(`/api/chat/ollama`, {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify({ messages: [...chatStore], model, stream: true})
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
				onDataReceived(JSON.parse(chunk).message.content);
			}
		}
	} catch (error) {
		console.error('Error sending question:', error);
		throw error;
	}
};
