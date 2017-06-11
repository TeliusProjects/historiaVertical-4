<?php

 require ('/home/snapfit/vendor/autoload.php');

    $entityBody = file_get_contents('php://input');
    $body       = json_decode($entityBody,true);
   
    if(empty($body)){
        $mail   = $_POST['mail'];
  
    }else{
        header('Content-type: application/json');
    
        foreach ($body as $value) 
        { 
            $mail   = $value['mail'];
        }
    }
    

    if(empty($mail)){

       $sent=false;
        echo json_encode(array('status'=> '1','sent' => $sent));
         
    }else{

        $con = new MongoDB\Client;
    // Select Database
        if($con){
            $db = $con->users;
            // Select Collection
            $collection = $db->user;
            $filter = array(['Email' => $mail
                        ]);
            $options = [

                'projection' => ['Email' => 1]
            ];
            $qry = new MongoDB\Driver\Query($filter, $options);
           
            $rows = $collection->find($qry);
           
             foreach ($rows as $row) {
              
                if($row['Email']== $mail){
                    $usercorrect = true;
                    $user_found = $row;
                   
                }
            }
        
            if($usercorrect){
                
                $Caracteres = 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMOPQRSTUVXWYZ0123456789';
                $NumerodeCaracteres = strlen($Caracteres);
                $NumerodeCaracteres--;
                $password=NULL;
       
                for ($i=0; $i < 12; $i++){

                    $number =rand(0,$NumerodeCaracteres);
                    $password .= substr($Caracteres,$number,1);
                }   

                $username = $user_found['Username'];
                $pass_hash =md5($password);
                $collection -> updateOne(['Username' => $user_found['Username']], ['$set' => ['Password' => $pass_hash]]);

                $destinatario = $mail; 
                $asunto = "Recovery password e-mail";
                $cuerpo = ' 
                <html> 
                <head> 
                   <title>Recovery password</title> 
                </head> 
                <body> 
                <h3>Hello, '.$username.': </h3> 
                <p> We have recieved your request of recovery password in your SnapFit account and we have generated a new one for you. 
                <b>Use this password to entry in your acount.</b>
                </p> 
                <p> New password: '.$password. '</p> 
                <p> We highly recommend to change the password immediately when you log in.</p>
                </body> 
                </html> 
                '; 

                //para el envío en formato HTML 
                $headers = "MIME-Version: 1.0\r\n"; 
                $headers .= "Content-type: text/html; charset=iso-8859-1\r\n"; 
                $headers .= "From: SnapFit Project <snapfitcep@gmail.com>\r\n"; 
                $headers .= "Reply-To: snapfit@no-reply.com\r\n";
                $headers .= "Return-path: snapfitcep@gmail.com\r\n"; 

                $bool =mail($mail,$asunto,$cuerpo,$headers);

                echo json_encode(array('status'=> '2','sent' => $bool));
            }
            else
            {
                $sent=false;
                echo json_encode(array('status' => '3' , 'sent' => $sent));
            }
        }else{
            die("Mongo DB not connected!");
        }
    }
?>
