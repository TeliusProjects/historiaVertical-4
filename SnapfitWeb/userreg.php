<?php

require ('/home/snapfit/vendor/autoload.php');

	$entityBody = file_get_contents('php://input');
    $body = json_decode($entityBody,true);

	if(empty($body)){
		$user           = $_POST['username'];
        $password       = $_POST['password'];
        $email          = $_POST['Email'];
        $confirmEmail   = $_POST['Email2'];
        $password2      = $_POST['password2'];
	}
	else{
    	header('Content-type: application/json');

    	foreach ($body as $value)
		{
        	$user           = $value['username'];
        	$password       = $value['password'];
        	$email          = $value['Email'];
        	$confirmEmail   = $value['Email2'];
        	$password2      = $value['password2'];
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
            $client = new MongoDB\Client();

            if($client){    
                //connecting to database
                $database=$client->users;

                //connect to specific collection
                $collection=$database->user;

                $filter =array(['Email' => $email,'Username' => $user]);

                $query = new MongoDB\Driver\Query($filter);
            
                //checking for existing user
                $rows = $collection->find($query);

                /*foreach ($rows as $row) {}*/
                
                if($row['Email'] != $email && $row['Username'] != $user){
                    $document = array['Username'=>$user,'Email'=>$email,'Password'=>md5($password)]; 
                    
                    $insertOneResult=$collection->insertOne($document);

                    if($insertOneResult){
                        header('Location: profile.php');    
                    }
                    else{
                        $message = "<div class='error'>Error: Can not do possibility register because this user is being already exists.</div>";
                    }
                }
                else{
                    //var_dump($resultado);
                    $message = "<div class='error'>Email is already existed.Please register with another Email id!.</div>";
                }

            }
            else{
				die("Database are not connected");
            }
        }
        else{
            //Displaying the error
            foreach($error as $err){
                echo $err.'</br>';
            }
        }
?>