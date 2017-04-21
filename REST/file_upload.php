<?php
   require '/home/snapfit/vendor/autoload.php';
   include 'recognition_functions.php';
    	// Where the file is going to be placed

	   	$client = new MongoDB\Client;

		$db = $client->users;
		$coll = $db->MyList;

	    $target_path = "/var/www/REST/ListUsers/";
	    $url_path    = "http://192.168.1.43/REST/ListUsers/";
	    $recognition = new Recognition();
	   
	 
		    /* Add the original filename to our target path.
		    Result is "uploads/filename.extension" */
	    $image_path = $target_path . basename($_FILES['uploadedfile']['name']);
	 	$image = basename($_FILES['uploadedfile']['name']);
	 	
	    if(move_uploaded_file($_FILES['uploadedfile']['tmp_name'], $image_path)) { 
		   chmod($image_path, 0777);

		   $color     = $recognition->image_properties($image_path);
		   $logo_name = $recognition->logo_detection($image_path);
		   $labels    = $recognition->label_detection($image_path);

		   $document = array(
                        'URL_image'=>$url_path.$image
                        ); 
                    
           $insertOneResult=$coll->insertOne($document);

		   echo json_encode(array("Status"=> "200", "Message" => "The file ". basename($_FILES['uploadedfile']['name']).
		    " has been uploaded", "Color" => $color, "Logo"=>$logo_name,
		     "First label"=>$labels['First label'], 
		     "Second label"=>$labels['Second label']));
		   
	    } else{
	      echo json_encode(array("Message" => "There was an error uploading the file, please try again!", 
	    		"File" => $image, "Target path" => $target_path));		  
	    }


?>