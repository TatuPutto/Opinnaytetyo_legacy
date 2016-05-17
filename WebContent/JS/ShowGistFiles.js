var editor;
var editors = [];
var fileNum = 2;

$("document").ready(function() {
	$(".content").css("height", ($(window).height() - 120));
	$(".singleGistFiles").hide();
	$(".loading").show();
	
	//Haetaan ensimmäisen gistin tiedostot sivun latauksen valmistuttua
	var firstGistId = $(".listGists").find(".singleGist").first().attr("id");
	getGistFiles(firstGistId);
	
	
	//Klikatun gistin tiedostojen hakeminen
	$(".singleGist").click(function() {
		var gistId = $(this).attr("id");
		
		resetFields();
		getGistFiles(gistId);
		
		$(".singleGist").removeClass("selected");
		$(this).addClass("selected");
		
		$(".singleGistFiles").hide();
		$(".loading").show();
	});
	
	
	//Valitun gistin muokkaaminen
	$(".singleGistFiles").on("click", "#editGist", function() {
		var gistId = $(this).data("gistid");
		var url = "http://localhost:8080/Opinnaytetyo/GetSingleGist?id=" + gistId + "";
		window.location.href = url;
	});
	
	
	//Valitun gistin poistaminen
	$(".singleGistFiles").on("click", "#deleteGist", function() {
		if (confirm("Haluatko varmasti poistaa tämän gistin?")) {
			var gistId = $(this).data("gistid");
			var url = "http://localhost:8080/Opinnaytetyo/DeleteGist?id=" + gistId + "";
			window.location.href = url;
		}
	});
	
});



//Lähetetään hakupyyntö valitusta gististä
function getGistFiles(gistId) {
	var data = {id : gistId};
	
	if(gistId) {
		$.get("http://localhost:8080/Opinnaytetyo/GetSingleGistAJAX", data, function(response) {
			console.log(response);
			//addUtilityButtons(gistId);
			handleResponse(response);			
		});
	}
	else {
		$(".loading").hide();
	}
}


//Puretaan tiedostojen sisältö ACE-editoreihin.
function handleResponse(response) {
	var i = 0;
	for(var file in response) {
		var owner = response[file]["filename"];
		var filename = response[file]["filename"];
		var fileContent = response[file]["content"];
		
		//Jaetaan koodi new line merkkien kohdalta, katsotaan miten moneen osaan koodi jaettiin ->
		//editorille allokoitava rivimäärä.
		var amountOfLines = fileContent.split("\n").length; 
		
		//Lisätään ensimmäisen tiedoston koodi jo olemassa olevaan editoriin.
		if(i === 0) {
			//$(".gistInfo").append("<a href=\"\">" + filename + "</a>");
			$("#toGist").attr("href", "");
			$("#toGist").text("<Author> / " + filename);
			
			$(".gistFirstFile a").text(filename);
			editor = ace.edit("editor1");
			editor.setTheme("ace/theme/cobalt");
			editor.getSession().setMode("ace/mode/java");
			editor.setOption("showPrintMargin", false)
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
	
	$(".loading").hide();
	$(".singleGistFiles").show();
}


//Lisätään uusi kenttä
function addField(filename, fileContent, amountOfLines) {
	//$(".singleGistFiles").append("<div class=\"gistFile" + fileNum + "\">" +
	$(".singleGistFiles").append("<div class=\"gistFile\">" +
			"<div class=\"fileInfo\">" +
			//"<input type=\"text\" class=\"filename\" value=\"" + filename + "\" readonly/>" + 
			"<a href=\"\">" + filename + "</a>" + 
			"</div>" +
			"<div id=\"editor" + fileNum + "\"</div>" +
			"</div>"
	);
	
	createEditor(filename, fileContent, amountOfLines);
}


//Tehdään luodusta div-elementistä uusi ACE-editor ja lisätään editori taulukkoon.
function createEditor(filename, fileContent, amountOfLines) {
	var makeEditorOf = "editor" + fileNum;
	editors.push(ace.edit(makeEditorOf));
	
	editors[editors.length - 1].setTheme("ace/theme/cobalt");
	editors[editors.length - 1].getSession().setMode("ace/mode/java");
	editors[editors.length - 1].setOption("showPrintMargin", false)
	editors[editors.length - 1].setOptions({ maxLines: amountOfLines });
	editors[editors.length - 1].setValue(fileContent);
	editors[editors.length - 1].setReadOnly(true);
	editors[editors.length - 1].selection.moveTo((amountOfLines + 1), 0);
	
	fileNum++;
}
	

//Poistetaan lisätyt kentät ja tyhjennetään editorit
function resetFields() {
	editor.setValue("");
	editors = [];
	
	$(".singleGistFiles").find(".gistFile").remove();
}

