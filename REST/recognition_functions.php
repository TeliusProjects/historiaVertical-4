<?php

	require ('/home/snapfit/vendor/autoload.php');
	include 'get_values.php';
	set_time_limit(0);

	

	class Recognition{

		private $path;
		
		private $cvurl;
		

		function __construct(){
		
		}

		function __destruc(){


		}
		public function image_properties($image_path){
			$values = new GetValues();	
			$api_key = "AIzaSyBn3jouEw4SZX6X30hWgfXU30F4NbGdLdA";
			$cvurl = "https://vision.googleapis.com/v1/images:annotate?key=" . $api_key;
			$type = "IMAGE_PROPERTIES";
		
			$path = $image_path;
			//Did they upload a file...
			if(file_exists($path)){
				
					$valid_file = true;
					//can't be larger than ~4 MB
					if(getimagesizefromstring(($path) > (802400000000000000))) 
					{
						
						$valid_file = false;
						die('Your file\'s size is too large.');
					}

					//if the file has passed the test
					if($valid_file)
					{
						//convert it to base64
						$fname = $path;
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
						//curl_setopt($curl, CURLOPT_USERAGENT, 'Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; .NET CLR 1.0.3705; .NET CLR 1.1.4322)');
						curl_setopt($curl, CURLOPT_HTTPHEADER,
							array("Content-type: application/json",
								  ));
						curl_setopt($curl, CURLOPT_POST, true);
						curl_setopt($curl, CURLOPT_POSTFIELDS, $r_json);
						curl_setopt($curl, CURLOPT_SSL_VERIFYPEER,false);
						curl_setopt($curl, CURLOPT_SSL_VERIFYHOST,false);
						curl_setopt($curl, CURLOPT_CONNECTTIMEOUT, 0);
						curl_setopt($curl, CURLOPT_VERBOSE, true);
						$verbose = fopen('php://temp', 'w+');
						curl_setopt($curl, CURLOPT_STDERR, $verbose);

						$json_response = curl_exec($curl);

						
						$error = curl_error($curl);
						$status = curl_getinfo($curl, CURLINFO_HTTP_CODE);

						curl_close($curl);

						if ($status != 200) {

						    die("Error: $cvurl failed status $status");

						}

						/*echo "<pre>";
						echo $json_response;
						echo "</pre>";*/
						$json_data = json_decode($json_response);

						//print_r($json_data);
					

						$name = $values->getColorName($json_data);

						//print_r($objson);
						
					}

					
				}else{
					echo "The file  " . $path . "  doesn't exist";
				}
			return $name;
			}

	 	public function logo_detection($image_path){
	 		$values = new GetValues();
	 		$api_key = "AIzaSyBn3jouEw4SZX6X30hWgfXU30F4NbGdLdA";
	 		$cvurl = "https://vision.googleapis.com/v1/images:annotate?key=" . $api_key;
			$type = "LOGO_DETECTION";
			$path = $image_path;
			//Did they upload a file...
			if(file_exists($path)){
				
					$valid_file = true;
					//can't be larger than ~4 MB
					if(getimagesizefromstring(($path) > (802400000000000000))) 
					{
						
						$valid_file = false;
						die('Your file\'s size is too large.');
					}

					//if the file has passed the test
					if($valid_file)
					{
						//convert it to base64
						$fname = $path;
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
						//curl_setopt($curl, CURLOPT_USERAGENT, 'Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; .NET CLR 1.0.3705; .NET CLR 1.1.4322)');
						curl_setopt($curl, CURLOPT_HTTPHEADER,
							array("Content-type: application/json",
								  ));
						curl_setopt($curl, CURLOPT_POST, true);
						curl_setopt($curl, CURLOPT_POSTFIELDS, $r_json);
						curl_setopt($curl, CURLOPT_SSL_VERIFYPEER,false);
						curl_setopt($curl, CURLOPT_SSL_VERIFYHOST,false);
						curl_setopt($curl, CURLOPT_CONNECTTIMEOUT, 0);
						curl_setopt($curl, CURLOPT_VERBOSE, true);
						$verbose = fopen('php://temp', 'w+');
						curl_setopt($curl, CURLOPT_STDERR, $verbose);

						$json_response = curl_exec($curl);

						
						$error = curl_error($curl);
						$status = curl_getinfo($curl, CURLINFO_HTTP_CODE);

						curl_close($curl);

						if ($status != 200) {

						    die("Error: $cvurl failed status $status");

						}

						$json_data = json_decode($json_response);
					

						$logoName = $values->getLogoName($json_data);
						
					}

					
				}else{
					echo "The file  " . $path . "  doesn't exist";
				}
			return $logoName;
			}

		public function label_detection($image_path){
				$values = new GetValues();
				$api_key = "AIzaSyBn3jouEw4SZX6X30hWgfXU30F4NbGdLdA";
				$cvurl = "https://vision.googleapis.com/v1/images:annotate?key=" . $api_key;
				$type = "LABEL_DETECTION";
		
				$path = $image_path;
				//Did they upload a file...
				if(file_exists($path)){
					
						$valid_file = true;
						//can't be larger than ~4 MB
						if(getimagesizefromstring(($path) > (802400000000000000))) 
						{
							
							$valid_file = false;
							die('Your file\'s size is too large.');
						}

						//if the file has passed the test
						if($valid_file)
						{
							//convert it to base64
							$fname = $path;
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
							//curl_setopt($curl, CURLOPT_USERAGENT, 'Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; .NET CLR 1.0.3705; .NET CLR 1.1.4322)');
							curl_setopt($curl, CURLOPT_HTTPHEADER,
								array("Content-type: application/json",
									  ));
							curl_setopt($curl, CURLOPT_POST, true);
							curl_setopt($curl, CURLOPT_POSTFIELDS, $r_json);
							curl_setopt($curl, CURLOPT_SSL_VERIFYPEER,false);
							curl_setopt($curl, CURLOPT_SSL_VERIFYHOST,false);
							curl_setopt($curl, CURLOPT_CONNECTTIMEOUT, 0);
							curl_setopt($curl, CURLOPT_VERBOSE, true);
							$verbose = fopen('php://temp', 'w+');
							curl_setopt($curl, CURLOPT_STDERR, $verbose);

							$json_response = curl_exec($curl);

							
							$error = curl_error($curl);
							$status = curl_getinfo($curl, CURLINFO_HTTP_CODE);

							curl_close($curl);

							if ($status != 200) {

							    die("Error: $cvurl failed status $status");

							}

					
							$json_data = json_decode($json_response);

							$array_label = $values->getLabels($json_data);
						}	
					}else{
						echo "The file  " . $path . "  doesn't exist";
					}
				return $array_label;
				}		
	 	}

?>