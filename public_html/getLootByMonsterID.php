<?php 

	if($_SERVER['REQUEST_METHOD']=='GET'){
		
		$id  = $_GET['id'];
		
		require_once('dbConnect.php');
		
		$sql = "SELECT Items.name, Rarities.name as rarity, Rarity_chances.value as rarity_chances, Items.id_item as id_item FROM Items
		LEFT JOIN Loot ON Items.id_item = Loot.id_item
		LEFT JOIN Monsters ON Monsters.id_monster = Loot.id_monster
		LEFT JOIN Rarity_chances ON Monsters.id_monster = Rarity_chances.id_monster
		LEFT JOIN Rarities ON Rarities.id_rarity = Rarity_chances.id_rarity
		WHERE Monsters.id_monster = '".$id."' AND Items.rarity = Rarities.id_rarity";
		
		
		
		$r = mysqli_query($con,$sql);
                
		while($e=mysqli_fetch_assoc($r))
                $output[]=$e;
 
                print(json_encode(array("result"=>$output)));
 
mysqli_close($con);
}