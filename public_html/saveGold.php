<?php 

if($_SERVER["REQUEST_METHOD"]=="POST"){

       require_once('dbConnect.php');

       saveGold();
}

function saveGold()
{

       global $con;

       $id_player = $_POST['id_player'];
       $gold = $_POST['gold'];
       
 
       $sql = "UPDATE Players SET gold = '".$gold."' 
       WHERE id_player = '".$id_player."'";
  
       mysqli_query($con, $sql) or die (mysqli_error($con));
       mysqli_close($con);
  
}