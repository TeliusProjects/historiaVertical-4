<?php
	require ('/home/snapfit/vendor/autoload.php');
	
 	//header('Content-type:image/jpg');
	$client = new MongoDB\Client;

	$db = $client->users;
	$coll = $db->MyList;
	//checking for existing user
     $rows = $coll->find();
	 

	foreach ($rows as $row) {
	  # code...
     	$urls[] = array('url' => $row['URL_image']);   	
     }

     $jsonString = json_encode($urls);

     echo $jsonString;
?>

	

	


