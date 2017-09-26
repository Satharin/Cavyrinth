<?php 

		require_once('dbConnect.php');
		
		$sql = "SELECT id_item, image, description FROM Items";
		
		$res = mysqli_query($con,$sql);
		
		$result = array();

                while($row = mysqli_fetch_array($res)){		
		array_push($result,array(
			'id_item'=>$row['id_item'],
			'image'=>$row['image'],
                        'description'=>$row['description']
			)
		);}
		
		echo json_encode(array("result"=>$result));
		
		mysqli_close($con);
		
	