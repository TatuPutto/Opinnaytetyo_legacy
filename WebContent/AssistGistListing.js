var editor;

$("document").ready(function() {
	
	$(".singleGist").click(function() {
		var rawUrl = $(this).data("rawurl");
		getSource(rawUrl);
	});
	
	editor = ace.edit("gistSource");
	editor.getSession().setMode("ace/mode/java");
	editor.setReadOnly(true);
});



function getSource(rawUrl) {
	var data = {};
	data = {rawurl : rawUrl};
	
	$.post("http://localhost:8080/Opinnaytetyo/GetGistSourceServlet", data, function(response) {
		//alert(response);
		editor.setValue(response);
	});
}
	
