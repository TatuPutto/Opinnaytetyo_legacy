<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/ace/1.2.3/ace.js" type="text/javascript" charset="utf-8"></script>
<script src="HelpGistCreation.js" type="text/javascript"></script> 

<link href="styles.css" rel="stylesheet" type="text/css"></link>

<title>Luo uusi gist</title>
</head>
<body>
	<%@ include file="Header.jsp" %>
	<div id="files">
		<input type="text" id="description" placeholder="Kuvaus"/><br><br>
		
		<div class="gistFile">
			<div class="gistInfo">
				<input type="text" class="filename" placeholder="Tiedostonimi, esim. File.java"/>
			</div>
			<div id="editor"></div>
		</div>
	</div>
	
	<div id="buttons">
			<input type="button" id="addFile" value="Lisää tiedosto"/>
			<input type="button" id="createSecret" value="Luo salainen gist" style="float:right;"/>
			<input type="button" id="createPublic" value="Luo julkinen gist" style="float:right;"/>
	</div>
</body>
</html>