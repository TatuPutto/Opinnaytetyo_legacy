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
	
});


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
	editors[editors.length - 1].setOptions({ minLines :  10});
	editors[editors.length - 1].setOptions({ maxLines :  30});
	editors[editors.length - 1].setValue(content);
	editors[editors.length - 1].selection.moveTo((amountOfLines + 1), 0);

	editorNum++;
}


//Lisätään kenttä uudelle tiedostolle.
function addFile() {
	$("#editableFiles").append("<br><br><div class=\"gistFile\">" +
			"<input type=\"text\" class=\"filename\" placeholder=\"Tiedostonimi, esim. File.java\"/>" + 
			"<div id=\"editor" + editorNum +  "\"</div>" 
	);

	//Tehdään luodusta <div> elementistä uusi ACE-editor ja lisätään editori taulukkoon.
	var makeEditorOf = "editor" + editorNum;
	editors.push(ace.edit(makeEditorOf));
	editors[editors.length - 1].getSession().setMode("ace/mode/java");
	editors[editors.length - 1].setOptions({ minLines :  10});
	editors[editors.length - 1].setOptions({ maxLines :  30});
	
	editorNum++;
}

//Poistetaan tiedosto
function removeFile(target) {
	$(target).parent().hide();
	//var fname = $("." + $(target.parent()).attr("class") + " input:first").val();
	//var fname = $("." + target).next().val();
	var fname = $(target).prev().val();
	$(target).prev().val("");
	
	
	
	
	
	var id = $(target).parent().attr("class");
	
	var fileNum = id.substring(id.length - 1);
	//$("." + $(target.parent()).attr("class") + " input:first").val("");
	
	
	
	editors[fileNum].setValue("");
	
	
	//filesToBeEdited[fname] = {};
}



function getFieldValues() {
	var data = {};
	var description = $("#description").val();
	var fname = document.getElementsByClassName("filename");
	
	/*//Lisätään tiedostonimet ja lähdekoodit taulukoihin.
	for(var j = 0; j < editors.length; j++) {
		if(filenameFields[j].value != "") {
			filenames.push(filenameFields[j].value);
			sources.push(editors[j].getValue());
			alert(filenames[j]);
		}
		else {
			alert("Tiedostonimi ei voi olla tyhjä.");
			return;
		}
	}*/
	
	//Tarkistetaan onko alkuperäisiin tiedostioihin tehty muutoksia
	var i = 0;
	for(var property in filesUnmodified) {
		var nameChanged = false;
		var contentChanged = false;
		
		var filenameOriginal = filesUnmodified[property]["filename"];
		var contentOriginal = filesUnmodified[property]["content"];
		var filenameOnUpdate = fname[i].value;
		var contentOnUpdate = editors[i].getValue();
		
		
		
		
		//Tarkistetaan onko tiedostonimiä muokattu
		if(filenameOriginal === filenameOnUpdate) {
			console.log("Tiedostonimeä ei muutettu");
		}
		else {
			console.log("Tiedostonimeä on muutettu")
			nameChanged = true;
		}
		
		//Tarkistetaan onko tiedoston koodia muokattu
		if(contentOriginal === contentOnUpdate) {
			console.log("content ei muutosta");
		}
		else {
			console.log("content on muutettu")
			contentChanged = true;
		}
		
		
		//Riippuen muutoksista, lisätään päivitetty tiedostonimi ja/tai koodi
		//Jos tiedostoon ei ole tehty muutoksia ei lisätä mitään
		if(nameChanged === true && contentChanged === true && filenameOnUpdate === "" && contentOnUpdate === "") {
			filesToBeEdited[filenameOriginal] = {};
		}
		else if(nameChanged === true && contentChanged === true) {
			filesToBeEdited[filenameOriginal] = {filename: filenameOnUpdate, content: contentOnUpdate};
		}
		else if(nameChanged === true) {
			filesToBeEdited[filenameOriginal] = {filename: filenameOnUpdate};
		}
		else if(contentChanged === true) {
			filesToBeEdited[filenameOriginal] = {content: contentOnUpdate};
		}
			
		i++;
	}
	
	//Lisätään uudet tiedostot
	var amountOfFiles = Object.keys(filesUnmodified).length;
	console.log(amountOfFiles);
	
	for(var i = amountOfFiles; i < fname.length; i++) {
		filesToBeEdited[fname[i].value] = {filename: fname[i].value, content: editors[i].getValue()};
	}
	
	
	
	if(Object.keys(filesToBeEdited).length > 0) {
		data = {description: description, files: filesToBeEdited};
	}
	else {
		data = {description: description};
	}
	
	console.log(JSON.stringify(data));
		
	
	
	$.post("http://localhost:8080/Opinnaytetyo/EditGistServlet", JSON.stringify(data), function(response) {
		alert(response);
		window.location.href = "http://localhost:8080/Opinnaytetyo/";
	}, "json");
	

	
}

