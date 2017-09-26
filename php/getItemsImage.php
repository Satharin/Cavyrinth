<?php 

		require_once('dbConnect.php');
		
		$sql = "SELECT id_item, image FROM Items;
		
		$r = mysqli_query($con,$sql);
		
		$res = mysqli_fetch_array($r);
		
		$result = array();
		
		array_push($result,array(
			"id_item"=>$res['id_item'],
			"image"=>$res['image']
			)
		);
		
		echo json_encode(array("result"=>$result));
		
		mysqli_close($con);
		
	