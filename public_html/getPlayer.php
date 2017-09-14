<?php 

	if($_SERVER['REQUEST_METHOD']=='GET'){
		
		$name  = $_GET['name'];
		$password  = $_GET['password'];
		
		require_once('dbConnect.php');
		
		$sql = "SELECT name, password FROM Players WHERE name='".$name."' AND password='".$password."'";
		
		$r = mysqli_query($con,$sql);
		
		$res = mysqli_fetch_array($r);
		
		$result = array();
		
		array_push($result,array(
			"name"=>$res['name'],
			"password"=>$res['password']
			)
		);
		
		echo json_encode(array("result"=>$result));
		
		mysqli_close($con);
		
	}