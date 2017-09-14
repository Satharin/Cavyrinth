<?php 

	if($_SERVER['REQUEST_METHOD']=='GET'){
		
		$id  = $_GET['id'];
		$type = $_GET['type'];
                
		
		require_once('dbConnect.php');
		
		$sql = "SELECT image, type, description, quantity, Items.weight, Items.name, price, Items.id_item FROM Items
                LEFT JOIN Depot ON Items.id_item = Depot.id_item 
                LEFT JOIN Players ON Depot.id_player = Players.id_player
                WHERE Players.id_player = '".$id."' AND Items.type = '".$type."'";
		
		
		$r = mysqli_query($con,$sql);
                
		while($e=mysqli_fetch_assoc($r))
                $output[]=$e;

                if(is_null($output)){
                   $output="";}
 
                print(json_encode(array("result"=>$output)));
                
 
mysqli_close($con);
}