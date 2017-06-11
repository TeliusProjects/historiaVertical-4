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

    $tabla = "<table id='tabla-img' class='img'>";
    	$tabla .= "<tbody>";
    		foreach ($urls as $url) {
    			# code...
    			$tabla .= "<tr>";
					$tabla .= "<td><img id='img' src='".$url."' /></td>";
					//<!-- The Modal -->
					$tabla .= "<div id='myModal' class='modal'>"

						//<!-- The Close Button -->
						$tabla .= "<span class='close' onclick='document.getElementById('myModal').style.display='none''>&times;</span>"

						//<!-- Modal Content (The Image) -->
						$tabla .= "<img class='modal-content' id='img01' />"

						//<!-- Modal Caption (Image Text) -->
						$tabla .= "<div id='caption'></div>"
					$tabla .= "</div>"
					
					$tabla .= "<td><img id='img' src='".$url."' /></td>";
					//<!-- The Modal -->
					$tabla .= "<div id='myModal' class='modal'>"

						//<!-- The Close Button -->
						$tabla .= "<span class='close' onclick='document.getElementById('myModal').style.display='none''>&times;</span>"

						//<!-- Modal Content (The Image) -->
						$tabla .= "<img class='modal-content' id='img01' />"

						//<!-- Modal Caption (Image Text) -->
						$tabla .= "<div id='caption'></div>"
					$tabla .= "</div>"

					$tabla .= "<td><img id='img' src='".$url."' /></td>";
					//<!-- The Modal -->
					$tabla .= "<div id='myModal' class='modal'>"

						//<!-- The Close Button -->
						$tabla .= "<span class='close' onclick='document.getElementById('myModal').style.display='none''>&times;</span>"

						//<!-- Modal Content (The Image) -->
						$tabla .= "<img class='modal-content' id='img01' />"

						//<!-- Modal Caption (Image Text) -->
						$tabla .= "<div id='caption'></div>"
					$tabla .= "</div>"
				$tabla .= "</tr>";
			}
		$tabla .= "</tbody>";
	$tabla .= "</table>";
	
	printf($tabla);
?>

	

	


