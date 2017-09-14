<?php 

	if($_SERVER['REQUEST_METHOD']=='GET'){
		
		$id_item  = $_GET['id_item'];
		
		require_once('dbConnect.php');
		
		$sql = "SELECT Items.name, Item_stat.value AS defence, IFNULL(Stats.name, 'none') AS immunity, IFNULL(Item_immunities.bonus, '0') AS 'bonus%', weight FROM Items 
		LEFT JOIN Item_stat ON Items.id_item = Item_stat.id_item
		LEFT JOIN Item_immunities ON Items.id_item = Item_immunities.id_item
		LEFT JOIN Stats ON Stats.id_stat = Item_immunities.id_stat
		WHERE Items.id_item = '".$id_item."'";
		
		$r = mysqli_query($con,$sql);
		
		$res = mysqli_fetch_array($r);
		
		$result = array();
		
		array_push($result,array(
			"name"=>$res['name'],
            "defence"=>$res['defence'],
			"immunity"=>$res['immunity'],
			"bonus"=>$res['bonus%'],
			"weight"=>$res['weight']
			)
		);
		
		echo json_encode(array("result"=>$result));
		
		mysqli_close($con);
		
	}