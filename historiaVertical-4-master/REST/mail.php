<?php

 require ('/home/snapfit/vendor/autoload.php');
//header('Content-type: application/json');

$entityBody = file_get_contents('php://input');
    $body = json_decode($entityBody,true);
    //header('Content-type: application/json');

//if($_SERVER['REQUEST_METHOD'] == 'POST')
//{
    //$entityBody = file_get_contents('php://input');
    //$body = json_decode($entityBody,true);
    if(empty($body)){
        $username   = $_POST['username'];
  
    }else{
        header('Content-type: application/json');
    
        foreach ($body as $value) 
        { 
            $username   = $value['username'];
        }
    }
    
    if(empty($username)){

        $message = "Empty or invalid email address";
        echo json_encode(array('status'=> '1','message' => $message ));
         
    }

    $con = new MongoDB\Client;
    // Select Database
    if($con){
        $db = $con->users;
        // Select Collection
        $collection = $db->user;
        $filter = array(['Username' => $username,
                    ]);
        $options = [

            'projection' => ['Username' => 1]
        ];
        $qry = new MongoDB\Driver\Query($filter, $options);
       
        $rows = $collection->find($qry);
       
         foreach ($rows as $row) {
          
            if($row['Username']== $username){
                $usercorrect = true;
                $user_found = $row;
            }
        }
        
        if($usercorrect){
            
            $npassword='';

            for ($i=0; $i < 12; $i++){
                $npassword = $npassword + chr(rand(32,126));
            }

            $collection -> update($user_found['Password'], $npassword);

            $mensaje = "If you have recived this mail/n it means that you want/n to retrive your forgotten password/n
            Forgotten Password: " + $npassword;

            mail($_POST['username'], 'Forgotten Password', $mensaje);
            
        }
    }else{
        die("Mongo DB not connected!");
    }
?>
