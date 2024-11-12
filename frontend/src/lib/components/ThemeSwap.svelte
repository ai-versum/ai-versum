<script>
	import { Icon } from '@smui/common';

	// let lightTheme = localStorage.getItem('theme') !== 'dark';
	let lightTheme;
	let storedTheme = localStorage.getItem('theme');
	if (storedTheme === null) {
		// First visit: Use system preference
		lightTheme = window.matchMedia('(prefers-color-scheme: light)').matches;
		storedTheme = lightTheme ? 'light' : 'dark';
		localStorage.setItem('theme', storedTheme);
	} else {
		// Subsequent visits: Use stored preference, fallback to light
		lightTheme = storedTheme !== 'dark';
	}
	document.documentElement.setAttribute('data-theme', storedTheme);


	function handleThemeChange() {
		let themeToSet = lightTheme ? 'light' : 'dark';
		document.documentElement.setAttribute('data-theme', themeToSet);
		localStorage.setItem('theme', themeToSet);
	}

</script>

<label class="swap swap-rotate" aria-label="Toggle theme">
	<input type="checkbox" bind:checked={lightTheme} on:change={handleThemeChange} />

	<Icon class="swap-on material-icons my-auto text-3xl" aria-hidden="true">light_mode</Icon>
	<Icon class="swap-off material-icons my-auto text-3xl" aria-hidden="true">dark_mode</Icon>
</label>
