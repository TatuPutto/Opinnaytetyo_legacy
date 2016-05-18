<div id="header">
	<!--<div class="logo"><img src="../logo.png"/></div>-->

	<% 
	if(session.getAttribute("accessToken") != null) {
		String username = (String)session.getAttribute("username");
		String avatarUrl = (String)session.getAttribute("avatar");
		
		%>
		<div id="userInfo">
			<img src="<%=avatarUrl %>"/>
			<p><%=username %></p>
		</div>
		
		<ul id="navmenu">
			<li><a href="GetGists">Listaa gistit</a></li>
			<li><a href="CreateNewGist">Luo uusi Gist</a></li>
			<li><a href="Logout">Logout</a></li>
		</ul>
		<%	
	} 
	else { 
		%>
		<ul id="navmenu">
			<li><a href="GetGists">Listaa gistit</a></li>
			<li><a href="CreateNewGist">Luo uusi Gist</a></li>
			<li><a href="Login">Login</a></li>
		</ul>	
		<%
	}
	%>				

</div>
