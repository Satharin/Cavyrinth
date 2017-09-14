<?php 

	if($_SERVER['REQUEST_METHOD']=='GET'){
		
		$id  = $_GET['id'];
		
		require_once('dbConnect.php');
		
		$sql = "SELECT Monsters.name, hp, CASE WHEN Monster_immunities.id_stat = 4000 THEN Monster_immunities.bonus END AS attack, MAX(CASE WHEN Monster_immunities.id_stat = 4001 THEN Monster_immunities.bonus END) AS  defence, experience, gold_max, image FROM Monsters
		LEFT JOIN Monster_immunities ON Monsters.id_monster = Monster_immunities.id_monster
		LEFT JOIN Stats ON Stats.id_stat = Monster_immunities.id_stat
		WHERE Monsters.id_monster='".$id."'
		GROUP BY name";
		
		$r = mysqli_query($con,$sql);
		
		$res = mysqli_fetch_array($r);
		
		$result = array();
		
		array_push($result,array(
			"name"=>$res['name'],
			"hp"=>$res['hp'],
			"attack"=>$res['attack'],
			"defence"=>$res['defence'],
			"experience"=>$res['experience'],
			"gold_max"=>$res['gold_max'],
			"image"=>$res['image']
			)
		);
		
		echo json_encode(array("result"=>$result));
		
		mysqli_close($con);
		
	}