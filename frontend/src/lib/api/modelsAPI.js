export const fetchModels = async () => {
	try {
		const response = await fetch('/api/models');
		if (!response.ok) {
			throw new Error('Failed to fetch models');
		}
		return await response.json();
	} catch (error) {
		console.error('Error fetching models:', error);
		return [];
	}
};
