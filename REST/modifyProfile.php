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
    $gender        = $_POST['gender'];
    


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
        $gender         = $value['gender'];
 	}
 }
 $con = new MongoDB\Client;

 if($con)
 {
   $db = $con ->users;
   $collection -> $db -> user;
   $filter = array('Username'=>$username);
   $options= [ 'projection' => ['Username'=> 1]];
  
   $qry = new MongoDB\Driver\Query($filter,$options);
  
  
  

 }else{
     die("db is not connected");
 }
 


?>
