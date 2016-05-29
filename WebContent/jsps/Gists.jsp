<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="tatuputto.opinnaytetyo.gists.Gist" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<!-- <link href="styles.css " rel="stylesheet" type="text/css" />-->

<link href="http://localhost:8080/Opinnaytetyo/css/Header.css" rel="stylesheet" type="text/css"/>
<link href="http://localhost:8080/Opinnaytetyo/css/Gists.css" rel="stylesheet" type="text/css"/>
<link href='https://fonts.googleapis.com/css?family=Roboto' rel='stylesheet' type='text/css'/>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/ace/1.2.3/ace.js" type="text/javascript"></script>
<script src="http://localhost:8080/Opinnaytetyo/js/ShowGistFiles.js" type="text/javascript"></script>

<title>Gists</title>
</head>
<body>
<div class="container">
	<%@ include file="Header.jsp" %>
	<div class="content">
		<!-- 
		<div class="filters">
			<input type="button" id="addFilters" value="Lisää suodattimia"/>
			
			<div class="filteringOptions">
				<input type="button" id="getUsersGists" value="Omat gistit"/>
				<input type="button" id="getAllPublicGists" value="Kaikki gistit"/>
			</div>
		</div>
		 -->
		<div>
			<input type="button" id="getUsersGists" value="Omat gistit"/>
			<input type="button" id="getAllPublicGists" value="Kaikki gistit"/>
		</div>
		
		<div class="contentLeft">
			<!-- Listataan gistit -->
			
			<% 
			if(request.getAttribute("lastPage") != null) {
				%><input type="hidden" id="lastPageNum" value="<%=request.getAttribute("lastPage") %>"/><br><%
			}
			%>
			
			
			
			<div class="listGists">
				
				<% 
				if(request.getAttribute("gists") != null) {
					@SuppressWarnings("unchecked")
					ArrayList<Gist> gistList = (ArrayList<Gist>)request.getAttribute("gists");
					
					for (int i = 0; i < gistList.size(); i++) {
						if(gistList.get(i).getFiles().size() > 0) {
							String id = gistList.get(i).getId();
							String description = gistList.get(i).getDescription();
							String owner = gistList.get(i).getOwner().getLogin();
							String avatar = gistList.get(i).getOwner().getAvatarUrl();
							String name = gistList.get(i).getFiles().get(0).getFilename();
							String rawUrl = gistList.get(i).getFiles().get(0).getRawUrl();
							String viewUrl = "http://localhost:8080/Opinnaytetyo/GetSingleGist?id=" + id;
							
							%>
							<div class="singleGist" id="<%=id %>">
								<div class="singleGistContainer">
									<p class="gistOwner"><%=owner %> / <a href="<%=viewUrl %>"><%=name %></a></p>
									<p class="descPara"><%=description %></p>
								</div>
							</div>
							<%
						}	
					}
					
					if(request.getAttribute("fetchMethod").equals("all")) {
						%>
						<input type="button" id="loadMore" value="Lataa lisää"/>
						<%
					}
					
				} 
				else {
					out.println("Gistejä ei löytynyt");
				}
				%>
			
			</div>
			
		</div>
		
		
		<div class="contentRight">
			<!-- Yksittäisen gistin tiedostot -->
			<div class="singleGistFiles">
				<!-- Gistin tiedot, nimi, kuvaus, tekijä jne. -->
				<div class="gistInfo">
					<img class="ownerAvatar" src=""><a id="toGist" href=""></a> 
					<%
					//TODO suodata toiminnot tarkemmin, kaikkien gistien listauksesta voi löytyä omia gistejä
					if(request.getAttribute("fetchMethod").equals("user")) {
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
				</div>
				
				<!-- Yksittäinen tiedosto -->
				<div class="gistFirstFile">
					<div class="fileInfo">
						<!-- <input type="text" class="filename" value=""></input>-->
						<a class="filename" href=""/></a>
					</div>
					<!-- div-elementti, joka muutetaan ace-editoriksi -->
					<div id="editor1"></div>
				</div>
			</div>
			<div class="loading"></div>
		</div>
	</div>
</div>
</body>
</html>