var i = 1;

//Yksittäisen gistin tiedostojen näyttäminen
function ShowGistFiles(gistId) {
	this.id = gistId;
}


//Poistetaan lisätyt kentät ja tyhjennetään editorit
ShowGistFiles.prototype.reset = function() {
	editors = [];
	editor.setValue("");
	
	$("#gistFiles").find('div[class^="gistFile"]').remove();
}


//Lähetetään hakupyyntö valitusta gististä
ShowGistFiles.prototype.getGistFiles = function() {
	var gistId = this.id;
	var data = {id : gistId};
	
	if(gistId) {
		$.get("http://localhost:8080/Opinnaytetyo/GetSingleGistAJAX", data, function(response) {
			console.log(response);
			addUtilityButtons(gistId);
			handleResponse(response);			
		});
	}
	else {
		$("#loading").hide();
	}
}

//Puretaan tiedostojen sisältö ACE-editoreihin.
function handleResponse(response) {
	var i = 0;
	for(var file in response) {
		var filename = response[file]["filename"];
		var fileContent = response[file]["content"];
		
		//Jaetaan koodi new line merkkien kohdalta, katsotaan miten moneen osaan koodi jaettiin ->
		//editorille allokoitava rivimäärä.
		var amountOfLines = fileContent.split("\n").length; 
		
		//Lisätään ensimmäisen tiedoston koodi jo olemassa olevaan editoriin.
		if(i == 0) {
			$(".filename").first().val(filename);
			editor = ace.edit("editor");
			editor.getSession().setMode("ace/mode/java");
			editor.setOptions({ maxLines: amountOfLines });
			editor.setValue(fileContent);
			editor.setReadOnly(true);
			editor.selection.moveTo((amountOfLines + 1), 0);
			
			i++;
		}
		//Seuraaville tiedostoille tehdään uudet editorit.
		else {
			addField(filename, fileContent, amountOfLines);
		}	
	}
	
	$("#loading").hide();
	$("#gistBase").show();
}

//Lisätään napit gistin muokkaamista ja poistamista varten
//Poistetaan vanhat napit, jos niitä löytyy
function addUtilityButtons(gistId) {
	$("#gistFiles").find("#deleteGist").remove();
	$("#gistFiles").find("#editGist").remove();

	$("#gistFiles").prepend("<input type=\"button\" id=\"deleteGist\" value=\"Poista\" data-gistid=" + gistId + "></input>");
	$("#gistFiles").prepend("<input type=\"button\" id=\"editGist\" value=\"Muokkaa\" data-gistid=" + gistId + "></input>");
}


//Lisätään uusi kenttä
function addField(filename, fileContent, amountOfLines) {
	$("#gistFiles").append("<div class=\"gistFile" + i + "\">" +
			"<div class=\"gistInfo\">" +
			"<input type=\"text\" class=\"filename\" value=\"" + filename + "\" readonly/>" + 
			"</div>" +
			"<div id=\"editor" + i + "\"</div>" 
	);
	
	
	
	//Tehdään luodusta <div> elementistä uusi ACE-editor ja lisätään editori taulukkoon.
	var makeEditorOf = "editor" + i;
	editors.push(ace.edit(makeEditorOf));
	editors[editors.length - 1].getSession().setMode("ace/mode/java");
	editors[editors.length - 1].setOptions({ maxLines : amountOfLines });
	editors[editors.length - 1].setValue(fileContent);
	editors[editors.length - 1].setReadOnly(true);
	editors[editors.length - 1].selection.moveTo((amountOfLines + 1), 0);
	
	i++;
}



