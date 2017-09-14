<?php 

if($_SERVER["REQUEST_METHOD"]=="POST"){

       require_once('dbConnect.php');

       saveQuantity();
}

function saveQuantity()
{

       global $con;

       $id_player = $_POST['id_player'];
       $id_item = $_POST['id_item'];
       $quantity = $_POST['quantity'];
      
 
       $sql = "UPDATE Depot SET quantity = '".$quantity."'
       WHERE id_player = '".$id_player."' AND id_item = '".$id_item."'";
  
       mysqli_query($con, $sql) or die (mysqli_error($con));
       mysqli_close($con);
  
}