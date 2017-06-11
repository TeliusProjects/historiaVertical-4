<!-- <?php /*require "vistas.php"*/ ?> -->
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<link rel="icon" type="image/x-icon" href="img/iconos/logo.ico"/>
	<link rel="stylesheet" type="text/css" href="css/register.css"/>
	<title>SnapFit</title>
</head>
<body>
	<section id="secLogo">
		<img id="logo" class="fade" alt="SnapFit" src="img/logos/logo256.svg">
	</section>
	<section id="secRegister">
		<div class="answer"></div>
		<form action="userreg.php" id="formLogIn" method='post'>
			<!-- <div id="name">
				<label for="Name">Name</label>
				<input type="text" name="name" id="Name" placeholder="Name"/>
			</div> -->
			<div id="user">
				<!-- <label for="eMail">User or eMail</label> -->
				<input type="text" name="username" id="username" placeholder="Username" required/>
			</div>
			<div id="email">
				<!-- <label for="eMail">eMail</label> -->
				<input type="text" name="eMail" id="mail" placeholder="eMail" required/>
			</div>
			<div id="email">
				<!-- <label for="eMail">eMail</label> -->
				<input type="text" name="eMail2" id="mail2" placeholder="Confirm the eMail" required/>
			</div>
			<div id="pass">
				<!-- <label for="password">Password</label> -->
				<input type="password" name="Password" id="password" placeholder="Password" required/>
			</div>
			<div id="pass">
				<!-- <label for="password">Password</label> -->
				<input type="password" name="Password2" id="password2" placeholder="Confirm the password" required/>
			</div>
			<div id="button">
				<input type="submit" id="register" value="Register"/>
				<!-- <?php /*registerUser();*/ ?> -->
			</div>
		</form>
		<!-- 
		<div>
			<a href="register.php">Don't have account. Register here!</a>
		</div> 
		-->
	</section>
	<script src="js/register.js"></script>
</body>
</html>