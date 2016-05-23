<%@ page language="java" contentType="text/html;UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import="java.util.ArrayList" %>
<%@ page import="tatuputto.opinnaytetyo.gists.Gist" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/ace/1.2.3/ace.js" type="text/javascript"></script>
<script src="http://localhost:8080/Opinnaytetyo/js/ShowSingleGist.js" type="text/javascript"></script>

<link href='https://fonts.googleapis.com/css?family=Roboto' rel='stylesheet' type='text/css'/>
<link href="http://localhost:8080/Opinnaytetyo/css/Header.css" rel="stylesheet" type="text/css"/>
<link href="http://localhost:8080/Opinnaytetyo/css/SingleGist.css" rel="stylesheet" type="text/css"/>

<title>Gist</title>
</head>
<body>
<div class="container">
	<%@ include file="Header.jsp" %>
	<div class="content">
	
		
		
		<div class="files">
			<% 
			if (request.getAttribute("gist") != null && request.getAttribute("id") != null) {
				Gist gist = (Gist)request.getAttribute("gist");
		
				%>
				<div class="gistOptions">
					<div class="OptionsContainer">
						<input type="hidden" class="gistId" value="<%=gist.getId() %>"/>
						<img src="<%=gist.getOwner().getAvatarUrl() %>"/>
						<p class="gistName"><%=gist.getOwner().getLogin() + " / " + gist.getFiles().get(0).getFilename() %></p>
							
						<%
						if((Boolean)request.getAttribute("gistOwner")) {
							%>
							<input type="button" id="deleteGist" value="Poista"/>
							<input type="button" id="editGist" value="Muokkaa"/>
							<%
						}
						else {
							%>
							<input type="button" id="forkGist" value="Fork"/>
							<%
						}
						%>
						
						<br><p><%=gist.getDescription() %></p>
					</div>		
				</div><br>
				<%
				
				
				for (int i = 0; i < gist.getFiles().size(); i++) { 
					String fileNum = "gistFile" + i;
					String editorNum = "editor" + i;
					
					%>
					<div class="<%=fileNum %>">
						<div class="fileInfo">
							<p><%=gist.getFiles().get(i).getFilename() %></p> 
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
			
	</div>
</div>



</body>
</html>