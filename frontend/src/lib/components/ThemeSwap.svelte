<script>
	import { Icon } from '@smui/common';

	let lightTheme = localStorage.getItem('theme') === 'light';

	function handleThemeChange() {
		let themeTOSet = lightTheme ? 'light' : 'dark';
		document.documentElement.setAttribute('data-theme', themeTOSet);
		localStorage.setItem('theme', themeTOSet);
	}

</script>

<label class="swap swap-rotate">
	<input type="checkbox" bind:checked={lightTheme} on:change={handleThemeChange} />

	<Icon class="swap-on material-icons my-auto text-3xl">light_mode</Icon>
	<Icon class="swap-off material-icons my-auto text-3xl">dark_mode</Icon>


</label>
