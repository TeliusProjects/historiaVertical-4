<?php

require '/home/snapfit/vendor/autoload.php';

class DB_functions{
	
	
	private $connection;

	function __construct(){
		require_once 'DB_Connect.php';

		$db = new DB_Connect();

		$this ->connection = $db->connect();
	}

	function __destruc(){



	}
	public function storeUser($username, $password,$email){

		$uuid = uniqid("", true);
		$hash = $this->hashSSHA($password);
		$encrypted_password = $hash['encrypted'];
		$salt = $hash["salt"];

	}
	

	

}






