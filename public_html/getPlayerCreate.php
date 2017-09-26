<?php 

	if($_SERVER['REQUEST_METHOD']=='GET'){
		
		$name  = $_GET['name'];
                $email  = $_GET['email'];
                
		
		require_once('dbConnect.php');
		
		$sql = "SELECT name, email FROM Players WHERE name='".$name."' OR email='".$email."'";
		
		$r = mysqli_query($con,$sql);
		
		$res = mysqli_fetch_array($r);
		
		$result = array();
		
		array_push($result,array(
			"name"=>$res['name'],
			"email"=>$res['email']
			)
		);
		
	
		
		echo json_encode(array("result"=>$result));
		
		mysqli_close($con);
		
	}