<?php
require ('/home/snapfit/vendor/autoload.php');

 $entityBody = file_get_contents('php://input');
 $body = json_decode($entityBody,true);

    $user_found = null;
    $usercorrect = false;

 if(empty($body))
 {
    $username    = $_POST['username'];
 	$email       = $_POST['email'];
    $name        = $_POST['name'];
    $birthday    = $_POST['birthday'];
    $phoneNumber = $_POST['phoneNumber'];
    $sex         = $_POST['sex'];
    


 }
 else
 {
 	header('Content-type: application/json');

 	foreach ($body as $value) 
    {
        $username    = $value['username'];
 	    $email       = $value['email'];
        $name        = $value['name'];
        $birthday    = $value['birthday'];
        $phoneNumber = $value['phoneNumber'];
        $sex         = $value['sex'];
 	}
 }


?>