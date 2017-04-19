<?php 
require '/home/snapfit/vendor/autoload.php';
//phpinfo();

//$manager = new MongoDB\Driver\Manager("mongodb://localhost:27017");
//var_dump($manager);

$client = new MongoDB\Client;

$testdb = $client->companydb;

$collection = $testdb->testing;
$document = array(['name' => 'Pepe']);
$insertOneResult = $collection->insertOne($document);


var_dump("Inserted %d documents", $insertOneResult.getInsertCount());

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
		deliver_response(400, "Invalid Request", NULL);
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