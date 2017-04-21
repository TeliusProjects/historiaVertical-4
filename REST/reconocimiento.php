<?php

require ('/home/snapfit/Descargas/google-api/src/Google/autoload.php');

use Google\Cloud\Vision\VisionClient;

$projectId = 'vision-api 163515';
$path = "/var/www/REST/ralph_lauren.jpeg";
$vision = new VisionClient([

	'projectId' => $projectId,

	]);
$image = $vision->image(file_get_contents($path), ['IMAGE_PROPERTIES']);
$result = $vision->annotate($image);

print("Properties: ");
foreach ($result->imageProperties()->colors() as $color) {
	# code...

	$rgb = $color['color'];
	printf("red: %s\n", $rgb['red']);
	printf("green: %s\n", $rgb['green']);
	printf("blue: %s\n", $rgb['blue']);
}




?>