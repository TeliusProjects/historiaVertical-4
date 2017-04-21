<?php
 require ('/home/snapfit/vendor/autoload.php');

 $entityBody = file_get_contents('php://input');
 $body = json_decode($entityBody,true);

 if(empty($body))
 {
    $username    = $_POST['username'];
 	  $oldPassword = md5($_POST['oldPassword']);
    $newPassword = md5($_POST['newPassword']);
 }
 else{
 	header('Content-type: application/json');

 	foreach ($body as $value) 
    {
 		     $username    = $value['username'];
        $oldPassword = md5($value['oldPassword']);
        $newPassword = md5($value['newPassword']);
 	}
 }

 $con = new MongoDB\Client;

 if($con)
 {
 	$db = $con->users;
 	$collection = $db->user;
 	$filter = array(['Username' => $username,
                    'Password' => $oldPassword
                    ]);
    $options = [

        'projection' => ['Username' => 1], ['Password' => 1]
    ];
    $qry = new MongoDB\Driver\Query($filter, $options);
       
    $rows = $collection->find($qry);
       
    $usercorrect = false;

    foreach ($rows as $row) {
          
        if($row['Username']== $username && $row['Password'] == $oldPassword)
        {
            $collection -> update(array('$set'=> array('Password'=> $newPassword)));

            $usercorrect = true;
            $user_found = $row;

        }
    }

    if($usercorrect)
    {      
          //$collection -> update($user_found['Password'],$newPassword);


        $changed = true;

        //echo json_encode(array('status'=> '1','isChanged' => $changed));
    }
    elseif (!$usercorrect)
    {

        $changed = false;
        //echo json_encode(array('status'=> '2','isChanged' => $changed));

    }
    else{
        die("Mongo DB not connected!");
    }
  
    echo json_encode(array('isChanged' => $changed));
 }
?>
