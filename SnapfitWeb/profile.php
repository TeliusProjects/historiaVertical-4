<?php require "vistas.php"; ?>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<link rel="icon" type="image/x-icon" href="img/iconos/logo.ico"/>
	<!-- <link rel="stylesheet" type="text/css" href="css/principal.css"/> -->
	<title>SnapFit</title>
	<meta name="description" content="Bienvenido a la red social de detección de ropa. Encuentra las prendas que más te gusten y compártelas con tus amigos!">
</head>
<body>
	<header>
		<h1>
			<a href="#">
				<img src="img/logos/logo256.svg" alt="SnapFit Logo">
				<h5>Snapfit</h5>
			</a>
		</h1>
		<nav>
			<!-- <a href="notify.php">
				<img src="img/notify.svg" alt="Notify">
			</a> -->
			<a href="#">
				<img src="img/profile.svg" alt="Profile">
			</a>
		</nav>
	</header>
	<section id="principal">
		<!-- <h1>Welcome to Snapfit. <br /> We hope you enjoy the experience</h1> -->
		<section id="infoDB">
			<div class="answer"><p>Conected. Waiting for images</p></div>
			<!--
			<?php /*showInfoUser();*/ ?>
			<?php /*showMyImages();*/ ?>
			-->
		</section>
	</section>
	<script src="js/profile.js"></script>
</body>
</html>