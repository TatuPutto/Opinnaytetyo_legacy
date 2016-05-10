<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="tatuputto.opinnaytetyo.gists.Gist" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<link href="styles.css " rel="stylesheet" type="text/css" />

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/ace/1.2.3/ace.js" type="text/javascript" charset="utf-8"></script>
<script src="ShowGistFiles.js" type="text/javascript"></script>
<script src="ListGistFiles.js" type="text/javascript"></script>



<title>Gists</title>
</head>
<body>
<%@ include file="Header.jsp" %>

<div id="wrapper">
	<!-- Listataan gistit -->
	<div id="listGists">

		<% 
		if (request.getAttribute("gists") != null) {
			ArrayList<Gist> gistList = (ArrayList) request.getAttribute("gists");

			for (int i = 0; i < gistList.size(); i++) { 
				%>
				<div class="singleGist" id="<%=gistList.get(i).getId()%>"
				  data-rawurl="<%=gistList.get(i).getFiles().get(0).getRawUrl()%>">
					<p><%=gistList.get(i).getFiles().get(0).getFilename()%></p>
					<p><%=gistList.get(i).getDescription()%></p>
				</div>
				<%
			}
		} 
		else {
			out.println("Gistejä ei löytynyt");
		}
		%>
	</div>

	
	
	
	<!-- Yksittäisen gistin tiedostot -->
	<div id="gistFiles">
		<div id="gistSource"></div>
		<div id="loading"></div>
	</div>
</div>

</body>
</html>