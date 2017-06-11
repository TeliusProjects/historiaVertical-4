<?php 
require ('/home/snapfit/vendor/autoload.php');

	$entityBody = file_get_contents('php://input');
    $body = json_decode($entityBody,true);
    
    if(empty($body)){
        $username = $_POST['username'];
        $password = $_POST['Password'];
  
    }
    else{
        header('Content-type: application/json');
    
        foreach ($body as $value){ 
            $username   = $value['username'];
            $password   = $value['Password'];
        }
    }
    
    // if(empty($username)){
    //     $message = "Empty or invalid email address";
    //     echo json_encode(array('status'=> '1','message' => $message ));
    // }

    // if(empty($password)){
    //     $message = "Enter your password";

    //     echo json_encode(array('status'=> '2','message' => $message ));
    // }
    // else{
    	$pass_hash = md5($password);
    // }

    $con = new MongoDB\Client;
    // Select Database
    if($con){
        $db = $con->users;
        // Select Collection
        $collection = $db->user;
        $filter = array(['Username' => $username, 'Password' => $pass_hash]);
        $options = ['projection' => ['Username' => 1], ['Password' => 1]];
        $qry = new MongoDB\Driver\Query($filter, $options);
       
        $rows = $collection->find($qry);
        
        foreach ($rows as $row){
            if($row['Username']== $username && $row['Password'] == $pass_hash){
                $usercorrect = true;
                $user_found = $row;
            }
        }
        
        if($usercorrect){
        	header('Location: profile.php');
        }
        else{
        	$message = "<div class='error'>Error: Can not do possibility conect because using a wrong username or password.</div>";
        	// printf($message);
        }
    }
    else{
    	die("Mongo DB not connected!");
    }

    return printf($message);
?>