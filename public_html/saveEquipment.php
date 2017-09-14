<?php 

if($_SERVER["REQUEST_METHOD"]=="POST"){

require_once('dbConnect.php');
 
saveEquipment();
}

function saveEquipment()
{
	global $con;

	$id_player = $_POST["id_player"];	
	$eq_helmet = $_POST["eq_helmet"];
	$eq_armor = $_POST["eq_armor"];
	$eq_legs = $_POST["eq_legs"];
	$eq_boots = $_POST["eq_boots"];
	$eq_weapon = $_POST["eq_weapon"];
	$eq_shield = $_POST["eq_shield"];
	$eq_ring_left = $_POST["eq_ring_left"];
	$eq_ring_right = $_POST["eq_ring_right"];
	$eq_amulet = $_POST["eq_amulet"];
	
	$sql = "UPDATE Players SET eq_helmet = '".$eq_helmet."', eq_armor = '".$eq_armor."', eq_legs = '".$eq_legs."', eq_boots = '".$eq_boots."', eq_weapon = '".$eq_weapon."', eq_shield = '".$eq_shield."', eq_ring_left = '".$eq_ring_left."', eq_ring_right'".$eq_ring_right."', eq_amulet = '".$eq_amulet."' 
			WHERE id_player = '".$id_player."'";
			
	
	mysqli_query($con, $sql) or die (mysqli_error($con));
	mysqli_close($con);
	
}