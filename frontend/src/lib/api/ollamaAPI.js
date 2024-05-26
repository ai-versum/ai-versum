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