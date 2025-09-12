<!-- Bootstrap 5 JS bundle (includes Popper) -->
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-..." crossorigin="anonymous"></script>

<script>
	// Client-side Bootstrap validation
	(function() {
		'use strict'
		const form = document.getElementById('loginForm')
		form.addEventListener('submit', function(event) {
			if (!form.checkValidity()) {
				event.preventDefault()
				event.stopPropagation()
			}
			form.classList.add('was-validated')
		}, false)
	})()
</script>

<script>
	// Client-side Bootstrap validation
	(function() {
		'use strict'
		const form = document.getElementById('forgotForm')
		form.addEventListener('submit', function(event) {
			if (!form.checkValidity()) {
				event.preventDefault()
				event.stopPropagation()
			}
			form.classList.add('was-validated')
		}, false)
	})()
</script>