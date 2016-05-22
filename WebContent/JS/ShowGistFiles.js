var editor;
var editors = [];
var currentGistId;
var fileNum = 2;
var pageNum = 2;


$("document").ready(function() {
	$(".content").css("height", ($(window).height() - 120));
	$(".singleGistFiles").hide();
	$(".loading").show();
	
	//Haetaan ensimmäisen gistin tiedostot sivun latauksen valmistuttua
	currentGistId = $(".listGists").find(".singleGist").first().attr("id");
	getGistFiles(currentGistId);
	$("#" + currentGistId).addClass("selected");
	
	
	//Klikatun gistin tiedostojen hakeminen
	$(".listGists").on("click", ".singleGist", function() {
		var selectedGistId = $(this).attr("id");
		if(currentGistId !== selectedGistId) {
			currentGistId = selectedGistId;
			resetFields();
			getGistFiles(currentGistId);
			
			$(".singleGist").removeClass("selected");
			$(this).addClass("selected");
			
			$(".singleGistFiles").hide();
			$(".loading").show();
		}
	});
	
	
	
	//Valitun gistin poistaminen
	$("#deleteGist").click(function() {
		if (window.confirm("Haluatko varmasti poistaa tämän gistin?")) { 
			var url = "http://localhost:8080/Opinnaytetyo/DeleteGist?id=" + currentGistId + "";
			window.location.href = url;
		}
		
	});
	
	//Valitun gistin muokkaaminen
	$("#editGist").click(function() {
		var url = "http://localhost:8080/Opinnaytetyo/EditGist?id=" + currentGistId + "";
		window.location.href = url;	
	});
	
	
	
	$("#getUsersGists").click(function() {
		var url = "http://localhost:8080/Opinnaytetyo/Gists?fetch=user";
		window.location.href = url;	
	});
	
	
	$("#getAllPublicGists").click(function() {
		var url = "http://localhost:8080/Opinnaytetyo/Gists?fetch=all";
		window.location.href = url;	
	});
	
	$(".listGists").on("click", "#loadMore", function() {
		alert("clicked");
		loadMoreGists();
	});
	
});



//Lähetetään hakupyyntö valitusta gististä
function getGistFiles(gistId) {
	var data = {id : gistId};
	
	if(gistId) {
		$.get("http://localhost:8080/Opinnaytetyo/GetSingleGistAJAX", data, function(response) {
			handleResponse(gistId, response);			
		});
	}
	else {
		$(".loading").hide();
	}
}

//Puretaan tiedostojen sisältö ACE-editoreihin.
function handleResponse(gistId, response) {
	var i = 0;
	for(var file in response) {
		//var owner = response[file]["owner"];
		var filename = response[file]["filename"];
		var fileContent = response[file]["content"];
		
		//Jaetaan koodi new line merkkien kohdalta, katsotaan miten moneen osaan koodi jaettiin ->
		//editorille allokoitava rivimäärä.
		var amountOfLines = fileContent.split("\n").length; 
		
		//Lisätään ensimmäisen tiedoston koodi jo olemassa olevaan editoriin.
		if(i === 0) {
			
			$("#toGist").attr("href", "");
			$("#toGist").text("<Author> / " + filename);
			
			$(".gistFirstFile a").text(filename);
			editor = ace.edit("editor1");
			editor.setTheme("ace/theme/cobalt");
			editor.getSession().setMode("ace/mode/java");
			editor.setOption("showPrintMargin", false);
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
	editors[editors.length - 1].setOption("showPrintMargin", false);
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




function loadMoreGists() {
	var lastPage = $("#lastPageNum").val();
	var data = {page: pageNum};
	
	if(pageNum < lastPage) {
		$.get("http://localhost:8080/Opinnaytetyo/GetMoreGists", data, function(response) {
			console.log(response);
			handleNewGists(response);			
		});
	}
	
	pageNum++;
}

function handleNewGists(response) {
	for(var file in response) {
		var owner = response[file]["owner"];
		var gistId = response[file]["id"];
		var filename = response[file]["files"];
		var description = response[file]["description"];
		
		appendGistToList(owner, gistId, filename, description);
	}
}

var index = 1;
function appendGistToList(owner, gistId, filename, description) {
	$(".listGists").append("<div class=\"singleGist\" id=\"" + gistId + "\">" +
			"<p class=\"gistOwner\">" + owner + " / <a href=\"\">" + filename + "</a></p>" +
			"<p class=\"descPara\">" + description + "</p>" +
			"<br><p class=\"descPara\">" + index + "</p>" +
			"</div>"
	);
	index++;
	
}
