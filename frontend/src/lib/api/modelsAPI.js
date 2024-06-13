export const fetchModels = async () => {
	try {
		const response = await fetch('/api/models', {
			headers: {
				// Authorization: `Bearer ${localStorage.getItem('token')}`,
				Authorization: `Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrb25yYWRAcG9jemF0ZWsucGwiLCJpYXQiOjE3MTgzMDgxMzMsImV4cCI6MTcxODMxMTczM30.4mnikIF2cg9xK0_3TJcWCIDAd5bhTeHGRe6c0ztp7VQ`,
			}
		});
		if (!response.ok) {
			throw new Error('Failed to fetch models');
		}
		return await response.json();
	} catch (error) {
		console.error('Error fetching models:', error);
		return [];
	}
};
