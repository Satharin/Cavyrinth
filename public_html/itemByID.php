<?php 

	if($_SERVER['REQUEST_METHOD']=='GET'){
		
		$id  = $_GET['id'];
                
		
		require_once('dbConnect.php');
		require_once('itemTypeCheck.php');
		

		
		

		IF ($typeResult == 'shield') {
		$sql = "SELECT Items.name, type, Item_stat.value AS defence, price, weight, IFNULL(Stats.name, 'none') AS immunity, IFNULL(Item_immunities.bonus, '0') AS 'bonus%',Rarities.name AS rarity FROM Items 
		LEFT JOIN Rarities ON Items.rarity = Rarities.id_rarity
		LEFT JOIN Item_stat ON Items.id_item = Item_stat.id_item
		LEFT JOIN Item_immunities ON Items.id_item = Item_immunities.id_item
		LEFT JOIN Stats ON Stats.id_stat = Item_immunities.id_stat
		WHERE type = 'shield' AND Items.id_item='".$id."'";

		}ELSEIF ($typeResult == 'weapon') {
		$sql = "SELECT Items.name as name, type, Subtypes.name AS subtype, CASE WHEN Item_stat.id_stat = 4000 THEN Item_stat.value END
AS attack, MAX(CASE WHEN Item_stat.id_stat = 4001 THEN Item_stat.value END) AS defence, price, weight, IFNULL(Stats.name, 'none') AS elemental_attack, IFNULL(Item_immunities.bonus, '0') AS 'bonus%',Rarities.name AS rarity FROM Items 
                LEFT JOIN Rarities ON Items.rarity = Rarities.id_rarity
                LEFT JOIN Item_stat ON Items.id_item = Item_stat.id_item
                LEFT JOIN Item_immunities ON Items.id_item = Item_immunities.id_item
                LEFT JOIN Stats ON Stats.id_stat = Item_immunities.id_stat
                LEFT JOIN Subtypes ON Subtypes.id_item = Items.id_item
		WHERE type = 'weapon' AND Items.id_item='".$id."'";
		}ELSE{
		

                $sql = "SELECT Items.name AS name, type, price, weight, Rarities.name AS rarity FROM Items 
		LEFT JOIN Rarities ON Items.rarity = Rarities.id_rarity
		LEFT JOIN Item_stat ON Items.id_item = Item_stat.id_item
		WHERE Items.id_item='".$id."'
		GROUP BY Items.name";}
		
		$e = mysqli_query($con,$sql);
		
		$res = mysqli_fetch_array($e);
		
		
		$result = array();
		
		
		
		IF ($typeResult == 'shield') {
		array_push($result,array(
			"name"=>$res['name'],
			"type"=>$res['type'],
			"defence"=>$res['defence'],
			"price"=>$res['price'],
			"weight"=>$res['weight'],
			"immunity"=>$res['immunity'],
			"bonus%"=>$res['bonus%'],
			"rarity"=>$res['rarity']
			)
		);
		} ELSEIF ($typeResult == 'weapon') {
		array_push($result,array(
			"name"=>$res['name'],
			"type"=>$res['type'],
                        "subtype"=>$res['subtype'],
			"attack"=>$res['attack'],
			"defence"=>$res['defence'],
			"price"=>$res['price'],
			"weight"=>$res['weight'],
			"elemental_attack"=>$res['elemental_attack'],
			"bonus%"=>$res['bonus%'],
			"rarity"=>$res['rarity']
			)
		);
                }ELSE{    
                array_push($result,array(
			"name"=>$res['name'],
			"type"=>$res['type'],
			"price"=>$res['price'],
			"weight"=>$res['weight'],
			"rarity"=>$res['rarity']));

		}
		echo json_encode(array("result"=>$result));
		
		mysqli_close($con);
		
	}