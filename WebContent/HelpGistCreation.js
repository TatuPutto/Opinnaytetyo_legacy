var i = 2;
var editors = [];

//Alustetaan ensimmäinen editori, kun sivusto on ladattu
$(document).ready(function() {
	editor = ace.edit("snippetCode");
	editor.getSession().setMode("ace/mode/java");
	editors.push(editor);
	
	//Julkinen Gist
	$("#createPublic").click(function() {
		initiateGistCreation("true");
	});

	//Salainen Gist
	$("#createSecret").click(function() {
		initiateGistCreation("false");
	});

	//Poistetaan kenttä
	$("#gistFile").on("click", ".removeField", function() {
		$(this).parent().remove();
	});
});




//Kerätään Gistin luomiseen tarvittava data dokumentista ja lähetetään se eteenpäin.
function initiateGistCreation(isPublic) {
	var data = {};
	var sources = [];
	var filenames = [];
	var description = $("#snippetDescription").val();
	var filenameFields = document.getElementsByClassName("filename");

	//Lisätään tiedostonimet ja lähdekoodit taulukoihin.
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
	}

	
	
	//Koostetaan kerätty data olioon.
	data = {description : description, ispublic : isPublic, filenames: filenames, sources : sources};

	//AJAX-kutsu Gistin luontimetodiin.
	$.post("http://localhost:8080/Opinnaytetyo/CreateGist", data, function(response) {
		alert(response);
	});
}

//Lisätään kenttä uudelle tiedostolle.
function addFile() {
	$("#gistFile").append("<div class=\"extraFile\">" +
			"<input type=\"text\" class=\"filename\" placeholder=\"Tiedostonimi, esim. File.java\"/>" + 
			"<input type=\"button\" class=\"removeField\" value=\"Remove file\">" +
			"<div id=\"snippetCode" + i + "\" style=\"height:200px;width:600px;\"></div>" +
			"</div>" 
	);

	//Tehdään luodusta <div> elementistä uusi ACE-editor ja lisätään editori taulukkoon.
	var makeEditorOf = "snippetCode" + i;
	editors.push(ace.edit(makeEditorOf));
	editors[editors.length - 1].getSession().setMode("ace/mode/java");
	
	i++;
}

