var editors = [];
var filesUnmodified = [];
var filesToBeEdited = {};
var editorNum = 0;


$("document").ready(function() {
	getUnmodifiedData();

	$("#updateGist").click(function() {
		getFieldValues();
	});
		
	//Lisätään kenttä
	$("#addFile").click(function() {
		addFile();
	});
	
	//Poistetaan tiedosto
	$(".removeFile").click(function() {
		removeFile($(this));
	});
	
	$("#files").on("click", ".removeFile", function() {
		removeFile($(this));
	});
});


//Otetaan gistin tiedot 
function getUnmodifiedData() {
	var fields = $("[class^=gistFile]");
	var filenameFields = document.getElementsByClassName("filename");
	
	for(var i = 0; i < fields.length; i++) {
		fname = filenameFields[i].value;
		var content = $("." + $(fields[i]).attr("class") + " div p:first").text();
		var amountOfLines = content.split("\n").length; 

		createEditor(fields[i], content, amountOfLines);
		filesUnmodified.push({filename: fname, content: content});	
	}
}



//Tehdään div-elementistä ACE-editori
function createEditor(fields, content, amountOfLines) {
	var makeEditorOf = "editor" + editorNum;
	editors.push(ace.edit(makeEditorOf));
	editors[editors.length - 1].getSession().setMode("ace/mode/java");
	editors[editors.length - 1].setOptions({ minLines :  20});
	editors[editors.length - 1].setOptions({ maxLines :  40});
	editors[editors.length - 1].setValue(content);
	editors[editors.length - 1].selection.moveTo((amountOfLines + 1), 0);

	editorNum++;
}


//Lisätään kenttä uudelle tiedostolle.
function addFile() {
	$("#files").append("<div class=\"gistFile" + editorNum + "\">" +
			"<div class=\"gistInfo\">" +
			"<input type=\"text\" class=\"filename\" placeholder=\"Tiedostonimi, esim. File.java\"/>" + 
			"<input type=\"button\" class=\"removeFile\" value=\"Poista\" style=\"margin-left: 4px;\"/>" +
			"</div>" +
			"<div id=\"editor" + editorNum +  "\"</div>" 
	);

	//Tehdään luodusta <div> elementistä uusi ACE-editor ja lisätään editori taulukkoon.
	var makeEditorOf = "editor" + editorNum;
	editors.push(ace.edit(makeEditorOf));
	editors[editors.length - 1].getSession().setMode("ace/mode/java");
	editors[editors.length - 1].setOptions({ minLines :  20});
	editors[editors.length - 1].setOptions({ maxLines :  40});
	
	editorNum++;
}


//Piilotetaan kenttä ja asetetaan tiedostonimi ja koodi tyhjäksi
function removeFile(target) {
	$(target).closest("[class^=gistFile]").hide();
	
	var parentClass = $(target).closest("[class^=gistFile]").attr("class");
	var fileNum = parentClass.substring(parentClass.length - 1);
	editors[fileNum].setValue("");
	$(target).prev().val("");
}


function getFieldValues() {
	var data = {};
	var gistId = $("#gistId").text();
	var description = $("#description").val();
	var fname = document.getElementsByClassName("filename");

	//Tarkistetaan onko alkuperäisiin tiedostioihin tehty muutoksia
	var i = 0;
	for(var property in filesUnmodified) {
		var nameChanged = false;
		var contentChanged = false;
		
		var filenameUnmodified = filesUnmodified[property]["filename"];
		var contentUnmodified = filesUnmodified[property]["content"];
		var filenameOnUpdate = fname[i].value;
		var contentOnUpdate = editors[i].getValue();
		
		
		//Tarkistetaan onko tiedostonimiä muokattu
		if(filenameUnmodified !== filenameOnUpdate) {
			nameChanged = true;
		}
		//Tarkistetaan onko tiedoston koodia muokattu
		if(contentUnmodified !== contentOnUpdate) {
			contentChanged = true;
		}
		
		
		//Riippuen muutoksista, lisätään päivitetty tiedostonimi ja/tai koodi
		//Jos tiedostoon ei ole tehty muutoksia ei lisätä mitään
		if(nameChanged === true && contentChanged === true && filenameOnUpdate === "" && contentOnUpdate === "") {
		}
		else if(nameChanged === true && contentChanged === true) {
			filesToBeEdited[filenameUnmodified] = {filename: filenameOnUpdate, content: contentOnUpdate};
		}
		else if(nameChanged === true) {
			filesToBeEdited[filenameUnmodified] = {filename: filenameOnUpdate};
		}
		else if(contentChanged === true) {
			filesToBeEdited[filenameUnmodified] = {content: contentOnUpdate};
		}
			
		i++;
	}
	
	//Lisätään uudet tiedostot
	var amountOfFiles = Object.keys(filesUnmodified).length;
	console.log(amountOfFiles);
	
	for(var i = amountOfFiles; i < fname.length; i++) {
		filesToBeEdited[fname[i].value] = {filename: fname[i].value, content: editors[i].getValue()};
	}
	
	//Muodostetaan lähetettävän datan sisältävä olio
	data["id"] = gistId;
	data["description"] = description;

	if(Object.keys(filesToBeEdited).length > 0) {
		data["files"] = filesToBeEdited;
		//files =  {filesToBeEdited};
	}
	
	console.log(JSON.stringify(data));
		
	
	
	$.post("http://localhost:8080/Opinnaytetyo/EditGist", JSON.stringify(data), function(response) {
		alert(response);
		//window.location.href = "http://localhost:8080/Opinnaytetyo/";
	}, "json");
	//});
	

	
}

