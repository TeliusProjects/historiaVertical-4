<?php

 //require_once 'DB_Connect.php';
 require ('/home/snapfit/vendor/autoload.php');
    //define('WP_CACHE', true);

if(isset($_POST['bttn_register'])){

    

    $user=$_POST['username'];
    $email=$_POST['Email'];
    $confirmEmail=$_POST['Email2'];
    $password=$_POST['password'];
    $password2=$_POST['password2'];

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
                
                 
                 //if($email_correct){
                   if($row['Email'] != $email && $row['Username'] != $user) 
                   {
                   $document = array(
                        'Username'=>$user,
                        'Email'=>$email,
                        'Password'=>md5($password),
                        'ProfileImage' => null
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
                     var_dump($row);
                     echo "Email is already existed.Please register with another Email id!.";
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
}
?>
