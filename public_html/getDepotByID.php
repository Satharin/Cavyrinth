<?php 

	if($_SERVER['REQUEST_METHOD']=='GET'){
		
		$id  = $_GET['id'];
                
		
		require_once('dbConnect.php');
		
		$sql = "SELECT image, type, description, quantity, weight, Items.name AS name, price, Items.id_item FROM Items
                LEFT JOIN Depot ON Items.id_item = Depot.id_item 
                LEFT JOIN Players ON Depot.id_player = Players.id_player
                WHERE Players.id_player = '".$id."'";
		
		
		$r = mysqli_query($con,$sql);
                
		while($e=mysqli_fetch_assoc($r))
                $output[]=$e;
 
                print(json_encode(array("result"=>$output)));
 
mysqli_close($con);
}