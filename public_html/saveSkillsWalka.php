<?php 

if($_SERVER["REQUEST_METHOD"]=="POST"){

       require_once('dbConnect.php');

       saveSkills();
}

function saveSkills()
{

       global $con;

       $id_player = $_POST['id_player'];
       $hp = $_POST['hp'];
       $mp = $_POST['mp'];
       $attack = $_POST['attack'];
       $defence = $_POST['defence'];
       $gold = $_POST['gold'];
       $skill_points = $_POST['skill_points'];
       $health_potions = $_POST['health_potions'];
       $mana_potions = $_POST['mana_potions'];
       $experience = $_POST['experience'];
       $magic_experience = $_POST['magic_experience'];
       $level = $_POST['level'];
       $magic_level = $_POST['magic_level'];
       
 
       $sql = "UPDATE Players SET hp = '".$hp."', mp = '".$mp."', level = '".$level."', magic_level = '".$magic_level."', experience = '".$experience."', attack = '".$attack."', 
       defence = '".$defence."', gold = '".$gold."', skill_points = '".$skill_points."',
       health_potions = '".$health_potions."', mana_potions = '".$mana_potions."', magic_experience = '".$magic_experience."'	   
       WHERE id_player = '".$id_player."'";
  
       mysqli_query($con, $sql) or die (mysqli_error($con));
       mysqli_close($con);
  
}