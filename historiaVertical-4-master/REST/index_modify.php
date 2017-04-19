<?php
require ('/home/snapfit/vendor/autoload.php');

 $entityBody = file_get_contents('php://input');
 $body = json_decode($entityBody,true);

 if(empty($body)){
 	$username = $_POST['nombre'];
 	$email = $_POST['email'];
 	$password = $_POST['contra'];
 }else{
 	header('Content-type: application/json');

 	foreach ($body as $key => $value) {
 		$username = $value['nombre'];
 		$email = $value['email'];
 		$password = $value['contra'];
 	}
 }

$con = new MongoDB\Client;

 if($con){
 	$db = $con->users;
 	$collection = $db->user;
 	$filter = array(['nombre' => $username,
                    'contra' => $pass_hash
                    ]);
    $options = [

        'projection' => ['nombre' => 1], ['contra' => 1]
    ];
    $qry = new MongoDB\Driver\Query($filter, $options);
       
    $rows = $collection->find($qry);
       
    foreach ($rows as $row) {
          
        if($row['nombre']== $username && $row['contra'] == $pass_hash){
            $usercorrect = true;
            $user_found = $row;
        }
    }
    if($usercorrect){
        
        
        //echo json_encode(array('status'=> '3','message' => $message, 'Username' => $user_found['Username'] ));
?>

<html>
	
	<form action="modify.php" name="formMod" method="pos" enctype="multipart/form-data">
		<table>
			<tr>
				<th>Editar perfil</th>
			</tr>
			<tr>
				<td>Contraseña: </td>
				<td><input type="text" name="contra" id="" value=<?php echo $contra?>/></td>
			</tr>
			<tr>
				<td>Repetir contraseña:</td>
				<td><input type="text" name="repContra" id="" value=""/></td>
			</tr>


		</table>
		<input type="submit" name="cambiar" value="Cambiar">
	</form>

</html>