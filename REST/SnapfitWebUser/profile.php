<!-- <?php /*require "vistas.php";*/ ?> -->
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<link rel="icon" type="image/x-icon" href="img/iconos/logo.ico"/>
	<link rel="stylesheet" type="text/css" href="css/profile.css"/>
	<title>SnapFit</title>
	<meta name="description" content="Welcome to the recognition clothes social network. Find the outfits and share with your friends">
</head>
<body>
	<header>
		<h1>
			<img id="logo" src="img/logos/logo256.svg" alt="SnapFit Logo">
			<a href="#">
				<h3 id="name">Snapfit</h3>
				<h3 id="eslogan">Save your outfits!</h3>
			</a>
		</h1>
		<!-- <nav> -->
			<!-- <div id="menu">
				<img id="edit" src="img/iconos/edit.svg" alt="Edit">
			</div> -->
			<a href="edit.php">Edit Profile</a>
			<a href="initial.php">Log out</a>
			<!-- <a href="profile.php">
				<img id="profile" src="img/iconos/profile.svg" alt="Profile">
				Profile
			</a> -->
		<!-- </nav> -->
	</header>
	<section id="principal">
		<!-- <h1>Welcome to Snapfit. <br /> We hope you enjoy the experience</h1> -->
		<div class="answer"></div>
		<!--
		<?php /*showInfoUser();*/ ?>
		<?php /*showMyImages();*/ ?>
		-->

		<?php require "rellenarlist.php" ?>
		<!-- <table id="user">
			<tr>
				<td><img class="userimg" src="img/user/camisa-de-zara.jpg" alt=""></td>
				<td><img class="userimg" src="img/user/camiseta-levis.jpg" alt=""></td>
				<td><img class="userimg" src="img/user/lacoste-original.jpg" alt=""></td>
			</tr>
			<tr>
				<td><img class="userimg" src="img/user/ralph-lauren-rojo.jpg" alt=""></td>
				<td><img class="userimg" src="img/user/camiseta-nike.jpg" alt=""></td>
				<td><img class="userimg" src="" alt=""></td>
			</tr>
			<tr>
				<td><img class="userimg" src="" alt=""></td>
				<td><img class="userimg" src="" alt=""></td>
				<td><img class="userimg" src="" alt=""></td>
			</tr>
		</table> -->
	</section>
	<script src="js/profile.js"></script>
</body>
</html>