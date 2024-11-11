export const fetchAiChat = async (chatStore, model, onDataReceived, options) => {
	try {
		const response = await fetch(`/api/chat/${model.provider}/${model.id}`, {
			method: 'POST', headers: {
				'Content-Type': 'application/json'
			}, body: JSON.stringify(
				{
					messages: JSON.stringify([...chatStore]),
					options: options
				})
		});

		if (!response.ok) {
			console.error('Failed to send question');
		}

		const reader = response.body.getReader();
		const decoder = new TextDecoder('utf-8');

		while (true) {
			const { done, value } = await reader.read();
			if (done) break;
			const chunk = decoder.decode(value, { stream: true });
			console.debug('Chunk:', chunk);
			onDataReceived(chunk);
		}
	} catch (error) {
		console.error('Error sending question:', error);
		throw error;
	}
};
