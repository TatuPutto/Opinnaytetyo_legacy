var i = 1;

//Yksittäisen gistin tiedostojen näyttäminen
function ShowGistFiles(gistId) {
	this.id = gistId;
}


//Poistetaan lisätyt kentät ja tyhjennetään editorit
ShowGistFiles.prototype.reset = function() {
	editors = [];
	editor.setValue("");
	
	$("#gistFiles").find('div[id^="gistExtra"]').remove();
}


//Lähetetään hakupyyntö valitusta gististä
ShowGistFiles.prototype.getGistFiles = function() {
	var gistId = this.id;
	var data = {id : gistId};
	
	$.get("http://localhost:8080/Opinnaytetyo/GetSingleGistAJAX", data, function(response) {
		$("#loading").hide();
		$("#gistSource").show();
		addUtilityButtons(gistId);
		handleResponse(response);
	});
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
			editor = ace.edit("gistSource");
			editor.getSession().setMode("ace/mode/java");
			editor.setReadOnly(true);
			editor.setOptions({ maxLines: amountOfLines });
			editor.setValue(fileContent);
			editor.$blockScrolling = Infinity;
			
			i++;
		}
		//Seuraaville tiedostoille tehdään uudet editorit.
		else {
			addField(filename, fileContent, amountOfLines);
		}	
	}
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
	$("#gistFiles").append("<div id=\"gistExtra" + i + "\" style=\"border-style: solid;border-width: 1px;\"></div>");
	
	//Tehdään luodusta <div> elementistä uusi ACE-editor ja lisätään editori taulukkoon.
	var makeEditorOf = "gistExtra" + i;
	editors.push(ace.edit(makeEditorOf));
	editors[editors.length - 1].getSession().setMode("ace/mode/java");
	editors[editors.length - 1].setReadOnly(true);
	editors[editors.length - 1].setOptions({ maxLines : amountOfLines });
	editors[editors.length - 1].setValue(fileContent);
	editors[editors.length - 1].$blockScrolling = Infinity
	i++;
}



