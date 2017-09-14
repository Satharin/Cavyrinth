<?php 

	if($_SERVER['REQUEST_METHOD']=='GET'){
		
                $id = $_GET['id'];
		
		
		require_once('dbConnect.php');
		
		$sql = "SELECT type FROM Items WHERE id_item = '".$id."'";
	
		
		$r = mysqli_query($con,$sql);
		
		$res = mysqli_fetch_row($r);
		$typeResult = $res[0];
		
		
		
		
		
		
	}