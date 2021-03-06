<?php
   require '/home/snapfit/vendor/autoload.php';
   include 'recognition_functions.php';
    	// Where the file is going to be placed

	   	$client = new MongoDB\Client;
	   	$recognition = new Recognition();
		$db = $client->users;
		$coll = $db->MyList;
		$coll_users = $db->user;

		//ruta donde se guardarán las imágenes que han ido subiendo los usuarios.
	    $target_path = "/var/www/REST/ListUsers/";
	    //ruta en la que se guardarán las imágenes de perfil de los usuarios
	    $target_path_imageUser = "/var/www/REST/ImageUsers/";

	    //Estas son las url de descarga, para poder cargar las ímágenes en android con la librería Picasso.
	    $url_path    = "http://192.168.1.47/REST/ListUsers/";

	    $url_imageUserPath = "http://192.168.1.47/REST/ImageUsers/"; 

	   
		if (isset($_POST['image'])) {
			    $imagen = $_POST['image'];
			    $username = $_POST['username'];
			    $name_photo = $_POST['imageName'];
			    $profileImage = $_POST['imageProfile'];
			    $image_Profilename = $_POST['imageProfileName'];
			    $pathAndroid       = $_POST['androidPath'];

			    $imageprofile_name = $image_Profilename;
			    $image_name = $name_photo;
			   
			    $target_path = $target_path . $image_name;
			    $target_path_imageUser = $target_path_imageUser . $imageprofile_name;


			    if (file_put_contents($target_path, base64_decode($imagen)) != false){
			    	chmod($target_path, 0777);
			    	file_put_contents($target_path_imageUser, base64_decode($profileImage));
			    	chmod($target_path_imageUser, 0777);
			    	
			    	 $color     = $recognition->image_properties($target_path);
				   	 $logo_name = $recognition->logo_detection($target_path);
				     $labels    = $recognition->label_detection($target_path);

				     $document = array(
		                        'URL_image'=>$url_path.$image_name,
		                        'Username' =>$username,
		                        'URL_profile_image' => $url_imageUserPath.$imageprofile_name
		                        ); 
	                    
	           		$insertOneResult=$coll->insertOne($document);

	           		$filter = array('Username'=>$username);
				    $options= [ 'projection' => ['Username'=> 1]];
				  
				   	$qry = new MongoDB\Driver\Query($filter,$options);

				   	$rows = $coll_users->find($qry);
	           		 foreach($rows as $row){
					            if($row['Username'] == $username)
					            {
					                $criteria = ['Username' => $row['Username']];
					                $newData = ['$set' => ['ProfileImage' => $url_imageUserPath.$imageprofile_name,
					                					   'EncodedProfileImage' => $profileImage,
					                					   'ProfilePath' => $pathAndroid]]; 
					            }
					        }
					$coll_users->updateOne($criteria,$newData, array('upsert' => true));
					
				   echo json_encode(array("Status" => "200", "Message" => "The file ". $name_photo.
				    " has been uploaded", "Color" => $color, "Logo"=>$logo_name,
				     "First label"=>$labels['First label'], 
				     "Second label"=>$labels['Second label']));

			    }else{
			      echo json_encode(array("Message" => "There was an error uploading the file, please try again!", 
			    		"File" => $image, "Target path" => $target_path)); 
			}
		}
