<?php

require '/home/snapfit/vendor/autoload.php';

class DB_functions{
	
	
	private $connection;

	function __construct(){
		require_once 'DB_Connect.php';

		$db = new DB_Connect();

		$this->connection = $db->user;
	}

	function __destruc(){



	}
	

	

}






