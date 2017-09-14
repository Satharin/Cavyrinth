<?php 

	if($_SERVER['REQUEST_METHOD']=='GET'){
		
		$name  = $_GET['name'];
		
		require_once('dbConnect.php');
		
		$sql = "SELECT id_player, name, hp, mp, level, magic_level, experience, gold, capacity, attack, defence, critical, critical_chance, eq_helmet, eq_armor, eq_legs, eq_boots, eq_weapon, eq_shield, eq_ring_left, eq_ring_right,             eq_amulet, skill_points, used_hp_sp, used_mp_sp, used_attack_sp, used_defence_sp, used_critical_sp, used_critical_chance_sp, health_potions, mana_potions, magic_experience FROM Players WHERE name='".$name."'";
		
		$r = mysqli_query($con,$sql);
		
		$res = mysqli_fetch_array($r);
		
		$result = array();
		
		array_push($result,array(
			"id_player"=>$res['id_player'],
                        "name"=>$res['name'],
                        "hp"=>$res['hp'],
                        "mp"=>$res['mp'],
                        "level"=>$res['level'],
                        "magic_level"=>$res['magic_level'],
                        "experience"=>$res['experience'],
                        "gold"=>$res['gold'],
                        "capacity"=>$res['capacity'],
                        "attack"=>$res['attack'],
                        "defence"=>$res['defence'],
                        "critical"=>$res['critical'],
                        "critical_chance"=>$res['critical_chance'],
                        "eq_helmet"=>$res['eq_helmet'],
                        "eq_armor"=>$res['eq_armor'],
                        "eq_legs"=>$res['eq_legs'],
                        "eq_boots"=>$res['eq_boots'],
                        "eq_weapon"=>$res['eq_weapon'],
                        "eq_shield"=>$res['eq_shield'],
                        "eq_ring_left"=>$res['eq_ring_left'],
                        "eq_ring_right"=>$res['eq_ring_right'],
                        "eq_amulet"=>$res['eq_amulet'],
                        "skill_points"=>$res['skill_points'],
                        "used_hp_sp"=>$res['used_hp_sp'],
                        "used_mp_sp"=>$res['used_mp_sp'],
                        "used_attack_sp"=>$res['used_attack_sp'],
                        "used_defence_sp"=>$res['used_defence_sp'],
                        "used_critical_sp"=>$res['used_critical_sp'],
                        "used_critical_chance_sp"=>$res['used_critical_chance_sp'],
                        "health_potions"=>$res['health_potions'],
                        "mana_potions"=>$res['mana_potions'],
                        "magic_experience"=>$res['magic_experience']
			)
		);
		
		echo json_encode(array("result"=>$result));
		
		mysqli_close($con);
		
	}