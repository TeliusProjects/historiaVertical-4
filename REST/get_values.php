<?php

	class GetValues{

		function __construct(){
		
		}

		function __destruc(){


		}



		public function getColorName($json_data){

			$red  = $json_data->responses[0]->imagePropertiesAnnotation->dominantColors->colors[0]->color->red;
			$green  = $json_data->responses[0]->imagePropertiesAnnotation->dominantColors->colors[0]->color->green;
			$blue  = $json_data->responses[0]->imagePropertiesAnnotation->dominantColors->colors[0]->color->blue;
							
			$colorapi = "http://thecolorapi.com/id?hex=0047AB&rgb=$red,$green,$blue&hsl=215,100%,34%&cmyk=100,58,0,33";

			$file = file_get_contents($colorapi);
							
			$objson = json_decode($file);

		 	$name = $objson->name->value;

			return $name;
		}

		public function getLogoName($json_data){
			$logoName = $json_data->responses[0]->logoAnnotations[0]->description;

			return $logoName;
		}

		public function getLabels($json_data){

			$array_label= array("First label"=>$json_data->responses[0]->labelAnnotations[0]->description,
							  "Second label"=>$json_data->responses[0]->labelAnnotations[1]->description);


			return $array_label;

		}



	}




?>