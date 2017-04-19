<?php
 require ('/home/snapfit/vendor/autoload.php');

 $entityBody = file_get_contents('php://input');
 $body = json_decode($entityBody,true);

 if(empty($body)){
 	$password = md5($_POST['contra']);

    $user = array(['Password' => $password]
                    );
 }else{
 	header('Content-type: application/json');

 	foreach ($body as $key => $value) {
 		$contra = $value['contra'];
 	}
 }

 $con = new MongoDB\Client;

 if($con){
 	$db = $con->users;
 	$collection = $db->user;
 	$filter = array(['Username' => $username,
                    'Password' => $pass_hash
                    ]);
    $options = [

        'projection' => ['Username' => 1], ['Password' => 1]
    ];
    $qry = new MongoDB\Driver\Query($filter, $options);
       
    $rows = $collection->find($qry);
       
    foreach ($rows as $row) {
          
        if($row['Username']== $username && $row['Password'] == $pass_hash){
            $usercorrect = true;
            $user_found = $row;
        }
    }
    if($usercorrect){

        $collection -> update($user_found['Password'], $_POST['contra']);


        //echo json_encode(array('status'=> '3','message' => $message, 'Username' => $user_found['Username'] ));
    }else{
        die("Mongo DB not connected!");
    }
 }
?>