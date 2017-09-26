<?php 

if($_SERVER["REQUEST_METHOD"]=="GET"){
	
       $id_player = $_GET['id_player'];
       $id_item = $_GET['id_item'];
	
	require_once('dbConnect.php');
	
	$sql = "DELETE FROM Depot WHERE id_player = $id_player AND id_item = $id_item;";

	 if(mysqli_query($con,$sql)){
 echo 'Employee Deleted Successfully';
 }else{
 echo 'Could Not Delete Employee Try Again';
 }
	 
	mysqli_close($con);
}