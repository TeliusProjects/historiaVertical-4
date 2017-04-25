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
  
    foreach($rows as $row)
        {
            if($row['Username']== $username)
            {
                $criteria = ['Username' => $row['Username']];
                $newData = ['$set' => ['Email' => $email, 'Name'=> $name, 
                                       'Birthday' => $birthday, 'PhoneNumber' => $phoneNumber, 
                                       'Gender' => $gender]]; 
               if(empty($email) && empty($name) && empty($birthday) && empty($phonrNumber) && empty($gender))
               {
                     echo json_encode(array('email' => $row['Email']
                                            ,'name' => $row['Name'],
                                            'birthday' => $row['Birthday'],
                                            'phoneNumber'=> $row['PhoneNumber'],
                                            'gender' => $row['Gender']));
             
                }
              else{
                    $collection -> updateOne($criteria,$newData, ['$upsert' => true]);
                    $usercorrect = true;
               }
            }
            
        }
  

 }else{
     die("db is not connected");
 }
 


?>
