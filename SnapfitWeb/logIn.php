<!--<?php /*require "vistas.php"*/ ?>-->
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<link rel="icon" type="image/x-icon" href="img/iconos/logo.ico"/>
	<link rel="stylesheet" type="text/css" href="css/logIn.css"/>
	<title>SnapFit</title>
</head>
<body>
	<section id="secLogo">
		<img id="logo" class="fade" alt="SnapFit" src="img/logos/logo256.svg">
	</section>
	<section id="secLogIn">
		<div class="answer"></div>
		<form action="userlog.php" id="formLogIn">
			<!-- <legend>Log in</legend> -->
			<div id="user">
				<!-- <label for="eMail">User or eMail</label> -->
				<input type="text" name="username" id="eMail" placeholder="User or eMail"/>
			</div>
			<div id="pass">
				<!-- <label for="password">Password</label> -->
				<input type="password" name="Password" id="password" placeholder="Password"/>
			</div>
			<div id="button">
				<input type="submit" id="logIn" value="Log in"/>
				<!--<?php /*conectUser();*/ ?>-->
			</div>
		</form>
		<div>
			<a href="register.php">Don't have account. Register here!</a>
		</div>
	</section>
	<script src="js/logIn.js"></script>
</body>
</html>