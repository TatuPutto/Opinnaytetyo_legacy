<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<link href='https://fonts.googleapis.com/css?family=Roboto' rel='stylesheet' type='text/css'/>

<link href="http://localhost:8080/Opinnaytetyo/css/Header.css" rel="stylesheet" type="text/css"/>
<link href="http://localhost:8080/Opinnaytetyo/css/CreateGist.css" rel="stylesheet" type="text/css"/>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/ace/1.2.3/ace.js" type="text/javascript"></script>
<script src="http://localhost:8080/Opinnaytetyo/js/AssistGistCreation.js" type="text/javascript"></script> 

<!--<link href="styles.css" rel="stylesheet" type="text/css"></link> -->


<title>Luo uusi gist</title>
</head>
<body>
<div class="container">
	<%@ include file="Header.jsp" %>
	<div class="content">
		<div class="files">
			<input type="text" class="description" placeholder="Kuvaus"/><br><br>
			
			<div class="gistFile0">
				<div class="fileInfo">
					<input type="text" class="filename" placeholder="Tiedostonimi, esim. File.java"/>
				</div>
				<div id="editor0"></div>
			</div>
		</div>
		
		<input type="button" id="addFile" value="Lisää tiedosto"/>
		<input type="button" id="createSecret" value="Luo salainen gist" style="float:right;"/>
		<input type="button" id="createPublic" value="Luo julkinen gist" style="float:right;"/>

	</div>
</div>
</body>
</html>