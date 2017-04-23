<?php
 require ('/home/snapfit/vendor/autoload.php');

 $entityBody = file_get_contents('php://input');
 $body = json_decode($entityBody,true);

    $user_found = null;
    $usercorrect = false;

 if(empty($body))
 {
    $username    = $_POST['username'];
 	$oldPassword = md5($_POST['oldPassword']);
    $newPassword = md5($_POST['newPassword']);


 }
 else
 {
 	header('Content-type: application/json');

 	foreach ($body as $value) 
    {
        $username    = $value['username'];
        $oldPassword = $value['oldPassword'];
        $newPassword = $value['newPassword'];
 	}
 }

  $con = new MongoDB\Client;
    // Select Database
    if($con){
        $db = $con->users;
        // Select Collection
        $collection = $db->user;
        $filter = array(['Username' => $username,
                         'Password' => $oldPassword
                         ]);
        $options = [

            'projection' => ['Username' => 1], ['Password' => 1]
        ];
        $qry = new MongoDB\Driver\Query($filter, $options);
       

        $rows = $collection->find($qry);
    
    foreach ($rows as $row) {
          
        if($row['Username']== $username && $row['Password'] == $oldPassword)
        {

            //$collection -> update(array('$set'=> array('Password'=> $newPassword)));

            $usercorrect = true;
            $user_found = $row;

        }
    }

    if($usercorrect)
    {      
        $collection -> update(array('Username'=> $username, '$set'=>array('Password'=> $newPassword)));


        $changed = true;

        echo json_encode(array('status'=> '1','isChanged' => $changed));
    }
    else
    {

        $changed = false;
        echo json_encode(array('status'=> '2','isChanged' => $changed));

    }
   
   
 }
 else
 {
        die("Mongo DB not connected!");
    }
  
?>
