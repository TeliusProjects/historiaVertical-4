<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<link rel="icon" type="image/x-icon" href="img/iconos/logo.ico"/>
	<link rel="stylesheet" type="text/css" href="css/edit.css"/>
	<title>Edit your profile</title>
</head>
<body>
	<section id="secLogo">
		<img id="logo" class="fade" alt="SnapFit" src="img/logos/logo256.svg">
	</section>
	<section id="secRegister">
		<div class="answer"></div>
		<form action="modifyProfile.php" id="formModify" method='post'>
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
			<div id="name">
				<!-- <label for="eMail">eMail</label> -->
				<input type="text" name="name" id="name" placeholder="Account property name" required/>
			</div>
			<div id="num">
				<!-- <label for="password">Password</label> -->
				<input type="text" name="phone" id="phone" placeholder="Phone number" required/>
			</div>
			<div id="gen">
				<!-- <label for="password">Password</label> -->
				<select name="gender" id="gender">
					<option value="female">Women</option>
					<option value="male">Man</option>
				</select>
			</div>
			<div id="button">
				<input type="submit" id="change" value="Modify"/>
				<!-- <?php /*registerUser();*/ ?> -->
			</div>
		</form>
		<!--
		<div>
			<a href="password.php">Do you want to change your password? Do it here!</a>
		</div>
		-->
	</section>
	<script src="js/edit.js"></script>
</body>
</html>