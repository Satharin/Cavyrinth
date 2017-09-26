<?php 

	if($_SERVER['REQUEST_METHOD']=='GET'){
		
		$id_item  = $_GET['id_item'];
		
		require_once('dbConnect.php');
		
		$sql = "SELECT Items.name, IFNULL(Item_stat.value, '0') AS armor, IFNULL(Attributes.name, 'none') AS attribute, IFNULL(Attributes.value, '0') AS value, IFNULL(Stats.name, 'none') AS immunity, IFNULL(Item_immunities.bonus, '0') AS 'bonus%', weight FROM Items 
		LEFT JOIN Item_stat ON Items.id_item = Item_stat.id_item
		LEFT JOIN Item_immunities ON Items.id_item = Item_immunities.id_item
		LEFT JOIN Stats ON Stats.id_stat = Item_immunities.id_stat
		LEFT JOIN Attributes ON Attributes.id_item = Items.id_item
		WHERE Items.id_item = '".$id_item."'";
		
		$r = mysqli_query($con,$sql);
		
		$res = mysqli_fetch_array($r);
		
		$result = array();
		
		array_push($result,array(
			"name"=>$res['name'],
            "armor"=>$res['armor'],
			"attribute"=>$res['attribute'],
			"value"=>$res['value'],
			"immunity"=>$res['immunity'],
			"bonus"=>$res['bonus%'],
			"weight"=>$res['weight']
			)
		);
		
		echo json_encode(array("result"=>$result));
		
		mysqli_close($con);
		
	}