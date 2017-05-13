<?php 

require '/home/snapfit/vendor/autoload.php';
//phpinfo();

//$manager = new MongoDB\Driver\Manager("mongodb://localhost:27017");
//var_dump($manager);
 	

 	$connection = new MongoDB\Client;

 	$db = $connection->users; 
 	$collection = $db->user;

 	$id = $_GET['id'];
    //$password = $_POST['Password'];

    //$encrypted_pass = md5($password);

    //var_dump($encrypted_pass);

 	$mongoId = new MongoDB\BSON\ObjectId($id);
 	
 	$condition = array('_id' => $mongoId);

 	//$qry    = new MongoDB\Driver\Query($filter);


 	if($collection->count($condition) == 1){
 		var_dump("estas dentro del if");
		$result = $collection->removeOne($condition);
		var_dump("despues de eliminar");
	}

	print_r($result);
	/*header ("Content-Type:application/json");



	include "functions.php";

	if(!empty($_GET['name'])){

		$name = $_GET['name'];
		$price = get_price($name);

		if(empty($price)){
			deliver_response(200, "book not found", NULL);
		}else{
			deliver_response(200, "Bookd found: $name",$price);
		}
	}else{
		eliver_response(400, "Invalid Request", NULL);
	}

function deliver_response($status, $status_message,$data){

		header("HTTP://1.1 $status $status_message");

		$response ['status' ] 		=$status;
		$response['status message'] = $status_message;
		$response['data'] 			= $data;

		$json_response = json_encode($response);

		echo $json_response;*/
//}
?>

