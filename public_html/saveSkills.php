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
       $critical = $_POST['critical'];
       $critical_chance = $_POST['critical_chance'];
       $gold = $_POST['gold'];
       $skill_points = $_POST['skill_points'];
       $used_hp_sp = $_POST['used_hp_sp'];
       $used_mp_sp = $_POST['used_mp_sp'];
       $used_attack_sp = $_POST['used_attack_sp'];
       $used_defence_sp = $_POST['used_defence_sp'];
       $used_critical_sp = $_POST['used_critical_sp'];
       $used_critical_chance_sp = $_POST['used_critical_chance_sp'];
 
       $sql = "UPDATE Players SET hp = '".$hp."', mp = '".$mp."', attack = '".$attack."', 
       defence = '".$defence."', critical = '".$critical."', critical_chance = '".$critical_chance."', 
       gold = '".$gold."', skill_points = '".$skill_points."',used_hp_sp = '".$used_hp_sp."', used_mp_sp = '".$used_mp_sp."', 
       used_attack_sp = '".$used_attack_sp."',used_defence_sp = '".$used_defence_sp."', used_critical_sp = '".$used_critical_sp."',
       used_critical_chance_sp = '".$used_critical_chance_sp."' 
       WHERE id_player = '".$id_player."'";
  
       mysqli_query($con, $sql) or die (mysqli_error($con));
       mysqli_close($con);
  
}