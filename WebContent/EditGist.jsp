<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ page import="java.util.ArrayList" %>
<%@ page import="tatuputto.opinnaytetyo.gists.Gist" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/ace/1.2.3/ace.js" type="text/javascript" charset="utf-8"></script>
<script src="AssistGistEdit.js" type="text/javascript"></script>

<link href="styles.css" rel="stylesheet" type="text/css"></link>

<title>Edit gist</title>
</head>
<body>
<%@ include file="Header.jsp" %>
<div id="wrapper">
	<!-- Listataan gistit -->
	<div id="editableFiles">

		<% //data-content="<%=gist.getFiles().get(i).getContent()
		if (request.getAttribute("gist") != null) {
			Gist gist = (Gist)request.getAttribute("gist");
			%>
			<input type="text" id="description" value="<%=gist.getDescription() %>"></input><br><br>
			<%
			for (int i = 0; i < gist.getFiles().size(); i++) { 
				String fileNum = "gistFile" + i;
				String editorNum = "editor" + i;
				
				%>
				<div class="<%=fileNum %>">
					<input type="text" class="filename" value="<%=gist.getFiles().get(i).getFilename() %>"></input>
					<input type="button" class="removeFile" value="Poista"/>
					<div id="<%=editorNum %>">
						<p id="content"><%=gist.getFiles().get(i).getContent() %></p>	
					</div>
				</div><br><br>
				<%
			}
		} 
		else {
			out.println("Gisti� ei l�ytynyt");
		}
		%>
		
	
	</div>
	
	<br><input type="button" id="addFile" value="Lis�� tiedosto"/>
	<input type="button" id="updateGist" value="P�ivit�" style="float: right;"/>

</div>

</body>
</html>