<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import="java.util.ArrayList" %>
<%@ page import="tatuputto.opinnaytetyo.gists.Gist" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/ace/1.2.3/ace.js" type="text/javascript"></script>
<script src="http://localhost:8080/Opinnaytetyo/js/AssistGistEdit.js" type="text/javascript"></script>

<link href='https://fonts.googleapis.com/css?family=Roboto' rel='stylesheet' type='text/css'/>
<link href="http://localhost:8080/Opinnaytetyo/css/Header.css" rel="stylesheet" type="text/css"/>
<link href="http://localhost:8080/Opinnaytetyo/css/EditGist.css" rel="stylesheet" type="text/css"/>

<title>Edit gist</title>
</head>
<body>
<div class="container">
	<%@ include file="Header.jsp" %>
	<!-- Listataan gistit -->
	<div class="content">
		<div class="files">
			<% 
			if (request.getAttribute("gist") != null && request.getAttribute("id") != null) {
				Gist gist = (Gist)request.getAttribute("gist");
	
				
				%>
				<input type="hidden" class="gistId" value="<%=gist.getId() %>"/>
				<input type="text" class="description" value="<%=gist.getDescription() %>"></input><br><br>
				<%
				
				for (int i = 0; i < gist.getFiles().size(); i++) { 
					String fileNum = "gistFile" + i;
					String editorNum = "editor" + i;
					
					%>
					<div class="<%=fileNum %>">
						<div class="fileInfo">
							<input type="text" class="filename" value="<%=gist.getFiles().get(i).getFilename() %>"></input>
							<input type="button" class="removeFile" value="Poista"/>
						</div>
						
						<div id="<%=editorNum %>">
							<p id="content"><%=gist.getFiles().get(i).getContent() %></p>	
						</div>
					</div>
					<%
				}
			} 
			else {
				out.println("Gistiä ei löytynyt");
			}
			
			%>
			</div>
			<div class="buttons">
				<input type="button" class="addFile" value="Lisää tiedosto"/>
				<input type="button" class="updateGist" value="Päivitä" style="float: right;"/>
			</div>
	
	</div>
</div>
</body>
</html>