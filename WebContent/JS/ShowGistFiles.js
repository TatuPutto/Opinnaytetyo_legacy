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
	
	$(".content").on("click", "#addFilters", function() {
		$(".filteringOptions").toggle("slow");
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
	var viewUrl = "http://localhost:8080/Opinnaytetyo/GetSingleGist?id=" + gistId;
	var owner = response["login"];
	var ownerAvatarUrl = response["avatarUrl"];
	var files = response["files"];
	
	
	for(var singleFile in files) {
		var filename = files[singleFile]["filename"];
		var fileContent = files[singleFile]["content"];
		
		//Jaetaan koodi new line merkkien kohdalta, katsotaan miten moneen osaan koodi jaettiin ->
		//editorille allokoitava rivimäärä.
		var amountOfLines = fileContent.split("\n").length; 
		
		//Lisätään ensimmäisen tiedoston koodi jo olemassa olevaan editoriin.
		if(i === 0) {
			$(".ownerAvatar").attr("src", ownerAvatarUrl);
			$("#toGist").attr("href", viewUrl);
			$("#toGist").text(owner + " / " + filename);
			
			var uri = encodeURIComponent(filename);
			$(".gistFirstFile a").text(filename);
			$(".gistFirstFile a").attr("href", viewUrl + "#" + uri);
			
			
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
			addField(filename, fileContent, viewUrl, amountOfLines);
		}	
	}
	
	$(".loading").hide();
	$(".singleGistFiles").show();
}


//Lisätään uusi kenttä
function addField(filename, fileContent, viewUrl, amountOfLines) {
	$(".singleGistFiles").append("<div class=\"gistFile\">" +
			"<div class=\"fileInfo\">" +
			"<a href=\"" + viewUrl + "\">" + filename + "</a>" + 
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



//Ladataan lisää gistejä
function loadMoreGists() {
	var lastPage = $("#lastPageNum").val();
	var data = {page: pageNum};
	
	if(pageNum <= lastPage) {
		$.get("http://localhost:8080/Opinnaytetyo/GetMoreGists", data, function(response) {
			handleNewGists(response);			
		})
		.fail(function() {
		    alert("Uusien gistien lataaminen ei onnistunut.");
		});
	}
	
	pageNum++;
}

//
function handleNewGists(response) {
	for(var file in response) {
		var owner = response[file]["owner"];
		var gistId = response[file]["id"];
		var filename = response[file]["files"];
		var description = response[file]["description"];
		var viewUrl = "http://localhost:8080/Opinnaytetyo/GetSingleGist?id=" + gistId;
		
		appendGistToList(owner, gistId, filename, description, viewUrl);
	}
	
	$("#loadMore").appendTo(".listGists");
}


function appendGistToList(owner, gistId, filename, description, viewUrl) {
	$(".listGists").append("<div class=\"singleGist\" id=\"" + gistId + "\">" +
			"<p class=\"gistOwner\">" + owner + " / <a href=\"" + viewUrl + "\">" + filename + "</a></p>" +
			"<p class=\"descPara\">" + description + "</p>" +
			"</div>"
	);
}
