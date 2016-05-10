var editor;
var editors = [];

$("document").ready(function() {
	$("#loading").show();
	
	//Haetaan ensimmäisen gistin tiedostot sivun latauksen valmistuttua
	var firstGistId = $("#listGists").find(".singleGist").first().attr("id");
	var showFilesOnLoad = new ShowGistFiles(firstGistId);
	showFilesOnLoad.getGistFiles();
	
	
	//Klikatun gistin tiedostojen hakeminen
	$(".singleGist").click(function() {
		var gistId = $(this).attr("id");
		var showFilesOnClick = new ShowGistFiles(gistId);
		showFilesOnClick.reset();
		showFilesOnClick.getGistFiles();
		
		$(".singleGist").removeClass("selected");
		$(this).addClass("selected");
		$("#gistSource").hide();
		$("#loading").show();
	});
	
	
	//Valitun gistin muokkaaminen
	$("#gistFiles").on("click", "#editGist", function() {
		var gistId = $(this).data("gistid");
		var url = "http://localhost:8080/Opinnaytetyo/GetSingleGist?id=" + gistId + "";
		window.location.href = url;
	});
	
	
	//Valitun gistin poistaminen
	$("#gistFiles").on("click", "#deleteGist", function() {
		if (confirm("Haluatko varmasti poistaa tämän gistin?")) {
			var gistId = $(this).data("gistid");
			var url = "http://localhost:8080/Opinnaytetyo/DeleteGist?id=" + gistId + "";
			window.location.href = url;
		}
	});

});
