<?php 

	if($_SERVER['REQUEST_METHOD']=='GET'){
		
		$id_player  = $_GET['id_player'];
		$id_item  = $_GET['id_item'];
                
		
		require_once('dbConnect.php');
		
		$sql = "SELECT * FROM Depot
                WHERE id_player = '".$id_player."' AND id_item = '".$id_item."'";
		
		
		$r = mysqli_query($con,$sql);
                
		while($e=mysqli_fetch_assoc($r))
                $output[]=$e;

                    $result = array();

                if(is_null($output)){
                        array_push($result,array(
			"id_player"=>"notExist",
			"id_item"=>"notExist",
                        "quantity"=>"0"
			)
                     );
                     print(json_encode(array("result"=>$result)));
                }else{
                print(json_encode(array("result"=>$output)));
                }
mysqli_close($con);
}