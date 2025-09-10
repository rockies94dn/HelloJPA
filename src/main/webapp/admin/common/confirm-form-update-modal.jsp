<div class="modal fade" id="confirmUpdateModal" tabindex="-1"
	role="dialog" aria-labelledby="confirmUpdateModalLabel"
	aria-hidden="true">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="confirmUpdateModalLabel">Confirm
					Delete</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal"
					aria-label="Close"></button>
			</div>
			<div class="modal-body">
				Are you sure you want to update <span id="updatedName"></span>?
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-secondary"
					data-bs-dismiss="modal">Cancel</button>
				<a id="confirmUpdateButton" class="btn btn-danger">Update</a>
			</div>
		</div>
	</div>
</div>

<script>
	function confirmFormUpdate(updateButton, name) {
		const form = updateButton.form;
		
		if (!form.reportValidity()) {
			return false;
		}
		
		const modal = new bootstrap.Modal(document
				.getElementById('confirmUpdateModal'));
		const confirmUpdateButton = document
				.getElementById('confirmUpdateButton');
		const updatedName = document.getElementById('updatedName');
		
		updatedName.textContent = name ? name : 'this';
		
		confirmUpdateButton.onclick = function() {
			const form = updateButton.form;
			form.action = updateButton.formAction || form.action;
			form.method = updateButton.formMethod || form.method;
			updateButton.form.submit();
		}
		modal.show();
		return false;
	}
</script>