var editors = [];
var editorNum = 0;

$("document").ready(function() {
	getFiles();
	
	$(".OptionsContainer").on("click", "#editGist", function() {
		var gistId = $(".gistId").val();
		var url = "http://localhost:8080/Opinnaytetyo/EditGist?id=" + gistId
		window.location.href = url;
	});
	
	$(".OptionsContainer").on("click", "#deleteGist", function() {
		if (window.confirm("Haluatko varmasti poistaa tämän gistin?")) { 
			var gistId = $(".gistId").val();
			var url = "http://localhost:8080/Opinnaytetyo/DeleteGist?id=" + gistId
			window.location.href = url;
		}
	});
});


//Otetaan gistin tiedot 
function getFiles() {
	var fields = $("[class^=gistFile]");
	
	for(var i = 0; i < fields.length; i++) {
		var content = $("." + $(fields[i]).attr("class") + " div p:last").text();
		var amountOfLines = content.split("\n").length; 

		createEditor(content, amountOfLines);
	}
}

//Tehdään div-elementistä ACE-editori
function createEditor(content, amountOfLines) {
	var makeEditorOf = "editor" + editorNum;
	editors.push(ace.edit(makeEditorOf));
	
	editors[editors.length - 1].setTheme("ace/theme/cobalt");
	editors[editors.length - 1].getSession().setMode("ace/mode/java");
	editors[editors.length - 1].setOption("showPrintMargin", false);
	editors[editors.length - 1].setReadOnly(true);
	editors[editors.length - 1].setOptions({maxLines: amountOfLines});
	editors[editors.length - 1].setValue(content);
	editors[editors.length - 1].selection.moveTo((amountOfLines + 1), 0);

	editorNum++;
}