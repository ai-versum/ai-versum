<script>
 async function saveSettings(event) {
	 console.log('event', event.target.elements['imageSize'].value);
 }
</script>

<div class="modal-box">
	<h3 class="font-bold text-lg mb-6">Settings</h3>

	<form method="dialog" class="flex flex-col gap-4" on:submit|preventDefault={saveSettings}>

		<label class="input input-bordered flex items-center">
			<input name="imageSize" type="text" class="grow" placeholder="Image size: 1024x1024" />
		</label>
		<label class="input input-bordered flex items-center">
			<input name="imageStyle" type="text" class="grow" placeholder="Image style: vivid" />
		</label>

		<div class="modal-action">
			<div class="tooltip" data-tip="Close dialog (Esc)">
				<button class="btn">Close</button>
			</div>
			<button class="btn-primary btn" type="submit">Save</button>
		</div>

	</form>

</div>
