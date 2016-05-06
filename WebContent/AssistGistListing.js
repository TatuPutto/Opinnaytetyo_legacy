var editor;
var editors = [];
var i = 1;

$("document").ready(function() {
	var firstGist = $("#listGists").find(".singleGist").first().attr("id");
	getGistFiles(firstGist);
	
	
	$(".singleGist").click(function() {
		//var rawUrl = $(this).data("rawurl");
		//getSource(rawUrl);
		$(".singleGist").removeClass("selected");
		$(this).addClass("selected");
		
		var gistId = $(this).attr("id");
		removeFields();
		getGistFiles(gistId);
	});
	
});



function getSource(rawUrl) {
	var data = {rawurl : rawUrl};
	
	$.post("http://localhost:8080/Opinnaytetyo/GetGistSourceServlet", data, function(response) {
		//alert(response);
		editor.setValue(response);
	});
}


//Lähetetään hakupyyntö valitusta gististä ja puretaan vastauksen sisältö ACE-editoreihin.
function getGistFiles(gistId) {
	var data = {id : gistId};
	
	$.post("http://localhost:8080/Opinnaytetyo/GetSingleGistServlet", data, function(files) {
		
		var j = 0;
		for(var file in files) {
			var filename = files[file]["filename"];
			var fileContent = files[file]["content"];
			
			//Jaetaan koodi new line merkkien kohdalta, katsotaan miten moneen osaan koodi jaettiin ->
			//editorille allokoitava rivimäärä.
			var amountOfLines = fileContent.split("\n").length; 
			
			//Lisätään ensimmäisen tiedoston koodi jo olemassa olevaan editoriin.
			if(j == 0) {			
				editor = ace.edit("gistSource");
				editor.getSession().setMode("ace/mode/java");
				editor.setReadOnly(true);
				editor.setOptions({ maxLines: amountOfLines });
				editor.setValue(fileContent);
				
				j++;
			}
			//Seuraaville tiedostoille tehdään uudet editorit.
			else {
				addField(filename, fileContent, amountOfLines);
			}	
		}
	});
}


//Lisätään uusi kenttä
function addField(filename, fileContent, amountOfLines) {	
	$("#gistFiles").append("<div id=\"gistExtra" + i + "\" style=\"border-style: solid;border-width: 1px;\"></div>");

	//Tehdään luodusta <div> elementistä uusi ACE-editor ja lisätään editori taulukkoon.
	var makeEditorOf = "gistExtra" + i;
	editors.push(ace.edit(makeEditorOf));
	editors[editors.length - 1].getSession().setMode("ace/mode/java");
	editors[editors.length - 1].setReadOnly(true);
	editors[editors.length - 1].setOptions({ maxLines : amountOfLines });
	editors[editors.length - 1].setValue(fileContent);
	
	i++;
}


function removeFields() {
	editors = [];
	editor.setValue("");
	
	$("#gistFiles").find('div[id^="gistExtra"]').remove();
}
	
