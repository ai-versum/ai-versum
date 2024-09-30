<script>
let imageSize
let imageStyle

</script>

<div class="modal-box">
    <h3 class="font-bold text-lg">Settings</h3>
    <p class="py-4">Press ESC key or click the button below to close</p>
    <div class="modal-action">
        <form method="dialog">
            <input id="imageSize" name="imageSize" type="text"
                   bind:value={imageSize}
                   class="">
            <input id="imageStyle" name="imageStyle" type="text"
                   bind:value={imageStyle}
                   class="">
            <div class="tooltip" data-tip="Close dialog (Esc)">
                <button class="btn">Close</button>
            </div>
            <button class="btn-primary btn">Save</button>
        </form>
    </div>
</div>
