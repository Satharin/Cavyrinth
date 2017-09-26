<?php 

require_once('dbConnect.php');

$name = $_POST['name'];
$email = $_POST['email'];
$password = $_POST['password'];
 
$sql = "INSERT INTO Players (name, hp, mp, level, magic_level, experience, gold, capacity, email, password, attack, defence, critical, critical_chance, eq_helmet, eq_armor, eq_legs, eq_boots, eq_weapon, eq_shield, eq_ring_left, eq_ring_right, eq_amulet, skill_points, used_hp_sp, used_mp_sp, used_attack_sp, used_defence_sp, used_critical_sp, used_critical_chance_sp, health_potions, mana_potions, magic_experience) 
        VALUES ('$name', '200', '150', '1', '0', '0', '0', '450', '$email','$password', '5', '5', '5', '10', '', '', '', '', '1005', '1000', '', '', '', '0', '0', '0', '0', '0', '0', '0', '5', '5', '0')";
  if(mysqli_query($con,$sql)){
    echo 'success';
  }
  else{
    echo 'failure';
  }
  mysqli_close($con);
?>