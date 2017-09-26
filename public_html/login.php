<?php 

	require_once('dbConnect.php'); 
	
	$name=$_POST['name'];
	$password=$_POST['password']; 
        $response = array();
      
	 
	
	if (!empty($_POST)) { 
		if (empty($_POST['name']) || empty($_POST['password'])) { 
		// Create some data that will be the JSON response 
		$response["success"] = 0; 
		$response["message"] = "One or both of the fields are empty2 ."; 
		//die is used to kill the page, will not let the code below to be executed. It will also 
		//display the parameter, that is the json data which our android application will parse to be 
		//shown to the users die(json_encode($response)); 
		} 
	$query = "SELECT * FROM Players WHERE name='".$name."' AND password='".$password."'"; 
	$sql1=mysqli_query($query); 
	$row = mysqli_fetch_array($sql1); 
        
	
	if (!empty($row)) { 
		$response["success"] = 1; 
		$response["message"] = "You have been sucessfully login"; 
		die(json_encode($response)); 
	} else{ 
		$response["success"] = 0;
		$response["message"] = "invalid username or password "; 
		die(json_encode($response)); } 
	} else{ 
		$response["success"] = 0; 
		$response["message"] = " One or both of the fields are empty3 "; 
		die(json_encode(array("result"=>$response))); } 

      
		
	mysqli_close(); 
?>
