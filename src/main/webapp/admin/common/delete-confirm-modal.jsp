<div class="modal fade" id="confirmDeleteModal" tabindex="-1"
	role="dialog" aria-labelledby="confirmDeleteModalLabel"
	aria-hidden="true">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="confirmDeleteModalLabel">Confirm
					Delete</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal"
					aria-label="Close"></button>
			</div>
			<div class="modal-body">
				Are you sure you want to delete <span id="deleteName"></span>?
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-secondary"
					data-bs-dismiss="modal">Cancel</button>
				<a id="confirmDeleteButton" class="btn btn-danger">Delete</a>
			</div>
		</div>
	</div>
</div>

<script>
	function confirmDelete(deleteUrl, name) {
		const modal = new bootstrap.Modal(document
				.getElementById('confirmDeleteModal'));
		const confirmDeleteButton = document
				.getElementById('confirmDeleteButton');
		const deleteNameSpan = document.getElementById('deleteName');
		confirmDeleteButton.href = deleteUrl;

		deleteNameSpan.textContent = name ? name : 'this item';
		modal.show();
	}
</script>