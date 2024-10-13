<script>
	import { isAuthenticated, user } from '$lib/stores/auth.js';

	let register = false;
	let email = '';
	let password = '';

	async function loginOrRegister() {
		const response = await fetch('api/user/register', {
			method: 'POST',
			headers: {
				'Content-Type': 'application/x-www-form-urlencoded'
			},
			body: `email=${email}&password=${password}`,
			credentials: 'include' // Include credentials in the request
		});

		const responseBody = await response.text();
		console.log('Response:', responseBody);

		if (response.ok) {
			isAuthenticated.set(true);
			user.set({ email }); // Set the logged-in user
		} else {
			// Handle login error
			console.error('Login failed:', responseBody);
		}
	}
</script>

<div class="flex min-h-full flex-col justify-center px-6 py-12 lg:px-8">
	<div class="sm:mx-auto sm:w-full sm:max-w-sm">
		<h2 class="mt-10 text-center text-2xl font-bold leading-9 tracking-tight">
			Create account
		</h2>
	</div>

	<div class="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
		<form class="space-y-6 mb-3" on:submit|preventDefault={loginOrRegister}>
			<div>
				<label for="email" class="block text-sm font-medium leading-6 ">Email</label>
				<div class="mt-2">
					<input id="email" name="email" type="text" autocomplete="email"
								 bind:value={email}
								 required
								 class="block w-full rounded-md border-0 p-1.5 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6">
				</div>
			</div>

			<div>
				<div class="flex items-center justify-between">
					<label for="password" class="block text-sm font-medium leading-6">Password</label>
				</div>
				<div class="mt-2">
					<input id="password" name="password" type="password" autocomplete="current-password"
								 bind:value={password}
								 required
								 class="block w-full rounded-md border-0 p-1.5 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6">
				</div>
			</div>

			<div>
				<button type="submit"
								class="flex w-full justify-center rounded-md bg-indigo-600 px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">
					Sign up
				</button>
			</div>
		</form>
<!--		<button class="btn btn-link pl-0" on:click={() => register = !register}>-->
<!--			Already registered? Sign in here!-->
<!--		</button>-->
		<a class="link link-primary" href="/login">
			Already registered? Sign in here!
		</a>
	</div>
</div>
