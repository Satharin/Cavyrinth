<?php 

if($_SERVER["REQUEST_METHOD"]=="POST"){

require_once('dbConnect.php');
 
createDepot();
}

function createDepot()
{
	global $con;

	$id_player = $_POST["id_player"];	
	$id_item = $_POST["id_item"];
	$quantity = $_POST["quantity"];
	
	$sql = "INSERT INTO Depot (id_player,id_item,quantity) VALUES ('".$id_player."','".$id_item."','".$quantity."')";
	
	mysqli_query($con, $sql) or die (mysqli_error($con));
	mysqli_close($con);
	
}



