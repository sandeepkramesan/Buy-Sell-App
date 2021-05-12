<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Home</title>
</head>
<body>
	<h1>Welcome to Buy and Sell App</h1><br><br>
	<h4>Steps</h4>
	<ul>
		<li>1.Enter details in this page</li>
		<li>2.Once the Member is Created, Go to Postman App</li>
		<li>3.Go to url localhost:port/login and give your username and password in JSON</li>
		<li>4.You'll get the Authentication Token if given correct credentials else 403 Forbidden Error</li>
		<li>5.When you get the Token, use that Token in the Header Authorisation under Bearer Token Option</li>
		<li>6.You can only get into links that you are permitted, for example, ONLY Admin can get get into user/all (like given in the Security Configuration Class)</li>
	</ul>
	<h1>Register</h1>
	<h4>***Username must be unique***</h4>
	<form action="/user/register/ui" method="post">
		<!-- <label for="username">Username</label> --> 
		<input type="text" name="username" placeholder="Username"/> 
		<input type="password" name="password" placeholder="Password"/> 
		<input type="text" name="role" placeholder="Role"/> 
		<input type="submit" value="Register" />
	</form>
	
	
</body>
</html>