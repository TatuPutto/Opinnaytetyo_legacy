<div id="header">
	<!--<div class="logo"><img src="../logo.png"/></div>-->
	
	<p style="float: left"><%= session.getAttribute("accessToken") %></p>
	
	<ul id="navmenu">
		<li><a href="GetGists">Listaa gistit</a></li>
		<li><a href="CreateNewGist.jsp">Luo uusi Gist</a></li>
		<li><a href="Login">Login</a></li>
		<li><a href="Logout">Logout</a></li>
	</ul>
	
</div>
