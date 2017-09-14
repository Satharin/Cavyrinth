<?php 

if($_SERVER["REQUEST_METHOD"]=="POST"){

       require_once('dbConnect.php');

       saveShop();
}

function saveShop()
{

       global $con;

       $id_player = $_POST['id_player'];
       $gold = $_POST['gold'];
       $health_potions = $_POST['health_potions'];
       $mana_potions = $_POST['mana_potions'];
      
 
       $sql = "UPDATE Players SET gold = '".$gold."', health_potions = '".$health_potions."', mana_potions = '".$mana_potions."' 
       WHERE id_player = '".$id_player."'";
  
       mysqli_query($con, $sql) or die (mysqli_error($con));
       mysqli_close($con);
  
}