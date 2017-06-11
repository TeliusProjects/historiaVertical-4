<?php

 //require_once 'DB_Connect.php';
require ('/home/snapfit/vendor/autoload.php');
//header('Content-type: application/json');
    //define('WP_CACHE', true);


	$entityBody = file_get_contents('php://input');
    $body = json_decode($entityBody,true);

//if($_SERVER['REQUEST_METHOD'] == 'POST')

if(empty($body)){
		    $user           = $_POST['username'];
        $password       = $_POST['password'];
        $password2      = $_POST['password2'];
        $email          = $_POST['Email'];
        $confirmEmail   = $_POST['Email2'];
       
}else{
    header('Content-type: application/json');
    
    foreach ($body as $value)
	{
        $user           = $value['username'];
        $password       = $value['password'];
        $password2      = $value['password2'];
        $email          = $value['Email'];
        $confirmEmail   = $value['Email2'];
        
    }
}
 
    $error = array();

        if(empty($user)){
            $error[] = "Username is empty or invalid";
        }
        if(empty($email) or !filter_var($email,FILTER_SANITIZE_EMAIL)){
          $error[] = "Email is empty or invalid";
        }
        if(empty($confirmEmail)){
            $error[] = "Confirm Email is empty or invalid";
        }
        if($email != $confirmEmail){
           $error[] = "Email and Confirm Email are not matching";
        }
        if(empty($password)){
          $error[] = "Please enter password";
        }
        if(empty($password2)){
          $error[] = "Please enter Confirm password";
        }
        if($password != $password2){
           $error[] = "Password and Confirm password are not matching";
        }
        

        if(count($error) == 0){
            //database configuration
           // $host = 'localhost';
            
           // $database_name = 'users';
           // $database_user_name = '';  
           // $database_password = '';  

             $client = new MongoDB\Client();

             if($client){
                    
                 //connecting to database
                 $database=$client->users;

                 //connect to specific collection
                 $collection=$database->user;

                 $filter =array(['Email' => $email,
                                 'Username' => $user
                                ]);

                 $query = new MongoDB\Driver\Query($filter);
            
                 //checking for existing user
                 $rows = $collection->find($query);

                 foreach ($rows as $row) {
                        # code...
                       //if($row['email'] == $email){
                         //   $email_correct = true;
                           // $email_row = $row;

                       }
                
                 if(empty($body)){
                 //if($email_correct){
                   if($row['Email'] != $email && $row['Username'] != $user){
                        $document = array(
                        'Username'=>$user,
                        'Email'=>$email,
                        'Password'=>md5($password),
                        'ProfileImage' => "null"
                        ); 
                    
                     $insertOneResult=$collection->insertOne($document);

                     if($insertOneResult){
                        echo 'Hi there ' .$user . ' You are successfully registered.' ;
                         
                     }else{
                        echo "The user wasn't registered";
                     }
                  //$bulk->insert($saveUser);

                  //$connection->execduteBulkWrite('users.user', $bulk);
                 }else{
                    //var_dump($resultado);
                     echo "Email is already existed.Please register with another Email id!.";
                 }
             }else{
                if($row['Email'] != $email && $row['Username'] != $user){
                        $document = array(
                        'Username'=>$user,
                        'Email'=>$email,
                        'Password'=>$password,
                        'ProfileImage' => "null",
                        'EncodedProfileImage' => "null",
                        'ProfilePath' => "null"
                        ); 
                    
                     $insertOneResult=$collection->insertOne($document);

                     if($insertOneResult){
                        echo 'Usuario registrado correctamente.' ;
                       
                         
                     }else{
                        echo "El usuario no se ha podido registrar.";
                     }
                  //$bulk->insert($saveUser);

                  //$connection->execduteBulkWrite('users.user', $bulk);
                 }else{
                    //var_dump($resultado);
                     echo "El correo que intenta introducir ya ha sido registrado anteriormente.";
                 }
             }

             }else{

                  die("Database are not connected");
             }

        }else{
            //Displaying the error
            foreach($error as $err){
                echo $err.'</br>';
                }
            }

?>
