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

    $user_found = null;
    $usercorrect = false; //Esta variable se mantendrá en false siempre y cuando el usuario no sea igual a uno de la BBDD.

    if(empty($body)){
        $username   = $_POST['username'];
        $password   = $_POST['Password'];

        $pass_hash = md5($password);
       
       
  
    }else{
        header('Content-type: application/json');
    
        foreach ($body as $value) 
        { 
            $username   = $value['username'];
            $password   = $value['Password'];
        }
    }
    
        if(empty($username)){

            $message = "Empty or invalid email address";
            echo json_encode(array('status'=> '1','message' => $message ));
             
        }

        if(empty($password)){
        
           
            $message = "Enter your password";
            
            echo json_encode(array('status'=> '2','message' => $message ));
         
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

         foreach ($rows as $row) {
         if(empty($body)){   
             if($row['Username'] == $username && $row['Password'] == $pass_hash){
                    $usercorrect = true;
                    $user_found = $row;
                }
                
            }else{
                if($row['Username'] == $username && $row['Password'] == $password){
                    $usercorrect = true;
                    $user_found = $row;
                }
            }
        }
        
      
        
        if($usercorrect){
            $message = "You are successfully loggedIn";

        
           echo json_encode(array('status'=> '3','message' => $message, 
            'Username'=> $user_found['Username'], 'Correct' => $usercorrect,
            'Email' => $user_found['Email']));
            
        }else{
            $message = "Username or password are not matching";
            
            
            echo json_encode(array('Username' => "Username not found", 'message'=>$message, 'Correct' => $usercorrect));
            
           // var_dump($cursor);
        }
    }else{
        die("Mongo DB not connected!");
    }
//}

 ?>