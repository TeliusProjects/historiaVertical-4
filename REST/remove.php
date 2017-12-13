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
        $username = $_POST['username'];
        $password = $_POST['password'];
        $pass_hash = md5($password);
  
    }else{
        header('Content-type: application/json');
    
        foreach ($body as $value) 
        { 
            
            $username = $value['username'];
            $pass_hash = $value['password'];
           
        }
    }
    
    $con = new MongoDB\Client;
    // Select Database
    if($con){
        $db = $con->users;
        // Select Collection
        $collection = $db->user;
        $filter = array(['Username' => $username,
                         'Password' => $pass_hash
                         ]);
        $options = [

            'projection' => ['Username' => 1], ['Password' => 1]
        ];
        $qry = new MongoDB\Driver\Query($filter, $options);
       

        $rows = $collection->find($qry);
       
         foreach ($rows as $row)
          {
          
            if($row['Username'] == $username && $row['Password'] == $pass_hash)
            {
                $usercorrect = true;
                $user_found = $row;
            }
        }
        
        if($usercorrect)
        {

               
                $collection -> deleteOne(['Username' => $user_found['Username']]);
                $message = "User succesfully deleted.";
            
                $deleted = true;
                echo json_encode(array('message'=>$message,'Deleted'=> $deleted));
                
        }else
        {
                $deleted= false;
                $message = "Couldn't delete yout account. Issue: incorrect password";
                echo json_encode(array('message'=>$message,'Deleted'=>$deleted));  

        }
    }else{
        die("Mongo DB not connected!");
    }
//}

 ?>
