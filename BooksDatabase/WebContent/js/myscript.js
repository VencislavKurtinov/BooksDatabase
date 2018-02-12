$(function() {

	function loadBooks(query) {
		$.get('Home?query=' + query).then(function(data) {

			var result = '';
			for (var index = 0; index < data.length; index++) {
				var book = data[index];
				result += "<tr>";
				result += "<td>" + book.name + "</td>";
				result += "<td>" + book.pages + "</td>";
				result += "<td>" + book.date + "</td>";
				result += "<td>" + book.ganre + "</td>";
				result += "</tr>";

			}

			$('table > tbody').html(result);

		});
	}

	$('#book').on('keyup', function() {
		var text = this.value;
		console.log(text)
		loadBooks(text);
	});

	$('#bookGanre').on(
			'click',
			function() {
				var form = document.forms["byGanre"];

				var ganre = form.elements["ganre"];
				var minPages = forms.elements["minPages"];
				var maxPages = forms.elements["maxPages"];
				$.get(
						'Home?ganre=' + ganre.value + '?minPages='
								+ minPages.value + '?maxPages='
								+ maxPages.value).then(function(data) {

					var result = '';
					for (var index = 0; index < data.length; index++) {
						var book = data[index];
						result += "<tr>";
						result += "<td>" + book.name + "</td>";
						result += "<td>" + book.pages + "</td>";
						result += "<td>" + book.date + "</td>";
						result += "<td>" + book.ganre + "</td>";
						result += "</tr>";

					}

					$('table > tbody').html(result);

				});
			});

	loadBooks('');
});
