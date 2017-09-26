<?php 

	if($_SERVER['REQUEST_METHOD']=='GET'){
		
	
		
		require_once('dbConnect.php');
		
		$sql = "SELECT Items.name, Rarities.name as rarity, Rarity_chances.value as rarity_chances FROM Items
		LEFT JOIN Loot ON Items.id_item = Loot.id_item
		LEFT JOIN Monsters ON Monsters.id_monster = Loot.id_monster
		LEFT JOIN Rarity_chances ON Monsters.id_monster = Rarity_chances.id_monster
		LEFT JOIN Rarities ON Rarities.id_rarity = Rarity_chances.id_rarity
		WHERE Monsters.id_monster = '2002' AND Items.rarity = Rarities.id_rarity";
		
		$res = mysqli_query($con,$sql);
		
		$result = array();
		
		while($row = mysqli_fetch_array($res)){
                array_push($result,
                        array(
			'name'=>$row['name'],
			'rarity'=>$row['rarity'],
			'rarity_chances'=>$row['rarity_chances']
			)
		);
}	
		
		
		echo json_encode(array("result"=>$result));
		
		mysqli_close($con);
		
	}