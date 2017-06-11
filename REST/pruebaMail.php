<?php 
$destinatario = "arnauinfantepinos@gmail.com"; 
$asunto = "Recovery password e-mail"; 
$username = "arnau";
$password = "arnau123";
$cuerpo = ' 
<html> 
<head> 
   <title>Recovery password</title> 
</head> 
<body> 
<h3>Hello, '.$username.': </h3> 
<p> We have recieved your request of recovery password in your SnapFit account and we have generated a new one for you. 
<b>Use this password to entry in your acount.</b>
</p> 
<p> New password: '.$password. '</p> 
<p> We highly recommend to change the password immediately when you log in.</p>
</body> 
</html> 
'; 

//para el envío en formato HTML 
$headers = "MIME-Version: 1.0\r\n"; 
$headers .= "Content-type: text/html; charset=iso-8859-1\r\n"; 

//dirección del remitente 
$headers .= "From: SnapFit Project <snapfitcep@gmail.com>\r\n"; 

//dirección de respuesta, si queremos que sea distinta que la del remitente 
$headers .= "Reply-To: snapfitcep@gmail.com\r\n"; 

//ruta del mensaje desde origen a destino 
$headers .= "Return-path: snapfitcep@gmail.com\r\n"; 

//direcciones que recibián copia 
$headers .= "Cc: snapfitcep@gmail.com\r\n"; 

$bool =mail($destinatario,$asunto,$cuerpo,$headers);

if($bool)
	{
		var_dump("enviado");
	}
?>