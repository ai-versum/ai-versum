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