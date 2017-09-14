<?php 

	if($_SERVER['REQUEST_METHOD']=='GET'){
		
		$name  = $_GET['name'];
		
		require_once('dbConnect.php');
		
		$sql = "SELECT Items.name AS name, type, Subtypes.name AS subtype, price, weight, Rarities.name AS rarity FROM Items 
		LEFT JOIN Rarities ON Items.rarity = Rarities.id_rarity
		LEFT JOIN Item_stat ON Items.id_item = Item_stat.id_item
		LEFT JOIN Subtypes ON Subtypes.id_item = Items.id_item
		WHERE Items.id_item='".$name."'
		GROUP BY Items.name";
		
		$r = mysqli_query($con,$sql);
		
		$res = mysqli_fetch_array($r);
		
		$result = array();
		
		array_push($result,array(
			"name"=>$res['name'],
			"type"=>$res['type'],
                        "subtype"=>$res['subtype'],
			"price"=>$res['price'],
			"weight"=>$res['weight'],
			"rarity"=>$res['rarity']
			)
		);
		
		echo json_encode(array("result"=>$result));
		
		mysqli_close($con);
		
	}