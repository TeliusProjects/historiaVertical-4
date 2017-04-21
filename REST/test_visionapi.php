<?php
require ('/home/snapfit/vendor/autoload.php');

$con = new MongoDB\Client;

$api_key = "AIzaSyDSwSc9gabpc9H0LdU2nCuni3TCp6ADk0M";
$cvurl = "https://vision.googleapis.com/v1/images:annotate?key=" . $api_key;
$type = "LOGO_DETECTION";

//Did they upload a file...

if($_FILES['photo']['name'])
{
	//$url_image = $_GET['url'] . "/" . $_FILES['photo']['name'];
	//var_dump($url_image);
	
	//if no errors...
	if(!$_FILES['photo']['error'])
	{
		$valid_file = true;
		//can't be larger than ~4 MB
		if($_FILES['photo']['size'] > (4024000)) 
		{
			$valid_file = false;
			die('Your file\'s size is too large.');
		}

		//if the file has passed the test
		if($valid_file)
		{

			//convert it to base64
			$fname = $_FILES['photo']['tmp_name'];
			$data = file_get_contents($fname);
			$base64 = base64_encode($data);
			//Create this JSON
			$r_json ='{
			  	"requests": [
					{
					  "image": {
					    "content":"' . $base64. '"
					  },
					  "features": [
					      {
					      	"type": "' .$type. '",
							"maxResults": 200
					      }
					  ]
					}
				]
			}';

			$curl = curl_init();
			curl_setopt($curl, CURLOPT_URL, $cvurl);
			curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);
			curl_setopt($curl, CURLOPT_HTTPHEADER,
				array("Content-type: application/json"));
			curl_setopt($curl, CURLOPT_POST, true);
			curl_setopt($curl, CURLOPT_POSTFIELDS, $r_json);
			$json_response = curl_exec($curl);
			$status = curl_getinfo($curl, CURLINFO_HTTP_CODE);
			curl_close($curl);

			if ( $status != 200 ) {
			    die("Error: $cvurl failed status $status" );
			}


			echo "<pre>";
			echo $json_response;
			echo "</pre>";
			$json_data = json_decode($json_response);


			$logoName = $json_data->responses[0]->logoAnnotations[0]->description;

			print_r($json_data);
			print_r($logoName);


			return $array_label;

		}
	}
	//if there is an error...
	else
	{
		//set that to be the returned message
		echo "Error";
		 die('Drror:  '.$_FILES['photo']['error']);
	}
}
?>