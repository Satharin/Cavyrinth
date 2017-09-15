package com.example.rafal.gra;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static java.sql.Types.NULL;

public class MainActivity extends AppCompatActivity {

    //Items from equipment
    ItemWeapon weapon;
    ItemShield shield;
    ItemArmors helmet, armor, legs, boots, amulet, ring_left, ring_right;

    //Experience tables with variables to check level change
    ArrayList<Integer> expTable = new ArrayList<Integer>();
    ArrayList<Integer> magicExpTable = new ArrayList<Integer>();
    int checkLvlAdvance, checkMlvlAdvance;

    //Variables needed for most of configs to get players statistics and equipment
    String player_name, id_player;

    //Character statistics and equipment
    int player_hp, player_mp, player_level, player_magic_level, player_experience, player_gold,
            player_capacity, player_attack, player_defence, player_critical, player_critical_chance,
            player_skill_points, player_magic_experience;

    int player_eq_helmet, player_eq_armor, player_eq_legs, player_eq_boots, player_eq_weapon,
            player_eq_shield, player_eq_ring_left, player_eq_ring_right, player_eq_amulet;

    //Loadings for getting data from database
    private ProgressDialog loadingGetPlayerID, loadingGetPlayerIdStats, loadingGetItemWeaponStats,
            loadingGetItemShieldStats;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(haveNetworkConnection()) {

            //Load viariables
            loadGame();

            //Objects for character's equipment
            weapon = new ItemWeapon(0, "", 0, 0, "", 0, 0);
            shield = new ItemShield(0, "", 0, "", 0, 0);
            helmet = new ItemArmors(0, "", 0, "", 0, "", 0, 0);
            armor = new ItemArmors(0, "", 0, "", 0, "", 0, 0);
            legs = new ItemArmors(0, "", 0, "", 0, "", 0, 0);
            boots = new ItemArmors(0, "", 0, "", 0, "", 0, 0);
            amulet = new ItemArmors(0, "", 0, "", 0, "", 0, 0);
            ring_left = new ItemArmors(0, "", 0, "", 0, "", 0, 0);
            ring_right = new ItemArmors(0, "", 0, "", 0, "", 0, 0);

            //get id_player based on player_name
            getPlayerId();

            //Save variables
            saveGame();

            //Fill table of experience
            fillExperienceTable();

            //Fill table of magic experience
            fillMagicExperienceTable();

        }else {
            Toast.makeText(MainActivity.this,"No network connection.", Toast.LENGTH_LONG).show();
        }

    }

    //Check if player really wants to leave the game
    public void exitGame(View view) {

        if(haveNetworkConnection() == false){

            AlertDialog.Builder exit = new AlertDialog.Builder(this);
            exit.setMessage("Are you sure you want to exit the application? Progress won't be saved without network connection.")
                    .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            System.exit(0);
                        }
                    })
                    .setPositiveButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create();
            exit.show();

        }else {

            AlertDialog.Builder exit = new AlertDialog.Builder(this);

            exit.setMessage("Are you sure you want to exit the application?")
                    .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            System.exit(0);
                        }
                    })
                    .setPositiveButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create();
            exit.show();

        }

    }

    //Save game
    public void saveGame() {

        SharedPreferences saveGame = getSharedPreferences("Save", MODE_PRIVATE);
        SharedPreferences.Editor save = saveGame.edit();
        save.putInt("checkLvlAdvance", checkLvlAdvance);
        save.putInt("checkMlvlAdvance", checkMlvlAdvance);
        save.putString("id_player", id_player);
        save.putString("player_name", player_name);
        save.apply();

    }

    //Load game
    public void loadGame() {

        SharedPreferences loadGame = getSharedPreferences("Save", MODE_PRIVATE);
        checkLvlAdvance = loadGame.getInt("checkLvlAdvance", 1);
        checkMlvlAdvance = loadGame.getInt("checkMlvlAdvance", 0);
        player_name = loadGame.getString("player_name", "");

    }

    //Display character's statistics
    public void statistics(View view) {

        if(haveNetworkConnection()) {
            getPlayerIdStats();
        }else {
            Toast.makeText(MainActivity.this,"No network connection.", Toast.LENGTH_LONG).show();
        }

    }

    //Go to EquipmentActivity
    public void equipment(View view) {

        if(haveNetworkConnection()) {

            //Clear textView from MainActivity
            TextView mainActivity = (TextView) findViewById(R.id.mainTextView);
            mainActivity.clearComposingText();
            mainActivity.scrollTo(0, 0);

            startActivity(new Intent(getApplicationContext(), EquipmentActivity.class));
            finish();

        }else {
            Toast.makeText(MainActivity.this,"No network connection.", Toast.LENGTH_LONG).show();
        }

    }

    //Go to PolujActivity
    public void hunt(View view) {

        if(haveNetworkConnection()) {

            //Clear textView from MainActivity
            TextView mainActivity = (TextView) findViewById(R.id.mainTextView);
            mainActivity.clearComposingText();
            mainActivity.scrollTo(0, 0);

            startActivity(new Intent(getApplicationContext(), PolujActivity.class));
            finish();

        }else {
            Toast.makeText(MainActivity.this,"No network connection.", Toast.LENGTH_LONG).show();
        }

    }

    //Go to SkillsActivity
    public void skills(View view) {

        if(haveNetworkConnection()) {

            //Clear textView from MainActivity
            TextView mainActivity = (TextView) findViewById(R.id.mainTextView);
            mainActivity.clearComposingText();
            mainActivity.scrollTo(0, 0);

            startActivity(new Intent(getApplicationContext(), SkillsActivity.class));
            finish();

        }else {
            Toast.makeText(MainActivity.this,"No network connection.", Toast.LENGTH_LONG).show();
        }

    }

    //Experience table
    public void fillExperienceTable() {

        for (int i = 0; i < 101; i++){
            if(i == 0 || i == 1) {
                expTable.add(i-1);
            }
            else{
                expTable.add((50*(i*i)-(150*i)+200)+expTable.get(i-1));
            }
        }

    }

    //Display experience table on textView
    public void showExperienceTable(View view) {

        TextView mainActivity = (TextView) findViewById(R.id.mainTextView);
        mainActivity.setMovementMethod(new ScrollingMovementMethod());
        StringBuilder experience = new StringBuilder("");

        for (int i = 1; i < expTable.size(); i++) {
            experience.append("Level " + i + ": " + expTable.get(i).toString() + "\n");
            mainActivity.setText(experience);
        }

    }

    //Magic experience table
    public void fillMagicExperienceTable() {

        for (int i = 0; i < 101; i++) {
            if(i == 0) {
                magicExpTable.add(i-1);
            }
            else{
                magicExpTable.add((int)(1600*((Math.pow(1.15, i))-1)/(1.15-1)));
            }
        }

    }

    //Odświeżenie statystyk
    /*public void odswiez(){

        for(int i = 100;i >= 0;i--){
            if(player_experience >= expTable.get(i) && player_experience > 59) {
                player_level = i;

                //Sprawdzenie czy był awans poziomu
                if (player_level > checkLvlAdvance){
                    if (player_level%5 == 0) {
                        player_attack += 1;
                        player_defence += 1;
                    }
                    player_hp += 15;
                    player_mp += 10;
                    if (player_skill_points < player_level - 1) {
                        player_skill_points += 1;
                    }
                    checkLvlAdvance += 1;
                }
                break;
            }
        }

        //Sprawdzenie mlvlu
        for(int i = 100;i >= 0;i--){

            if(player_magic_experience >= magicExpTable.get(i) && player_magic_experience > 59) {
                player_magic_level = i;

                //Sprawdzenie czy był awans poziomu
                if (player_magic_level > checkMlvlAdvance){
                    checkMlvlAdvance += 1;
                }
                break;
            }
        }

        //Sprawdzenie czy spadł poziom
        if(player_experience < expTable.get(player_level - 1)){
            player_level -= 1;
            player_attack -= 0;
            player_defence -= 0;
            player_hp -= 15;
        }

        //Sprawdzenie czy spadł mlvl
        if (player_magic_level > 0) {

            if (player_magic_experience < magicExpTable.get(player_magic_level - 1)) {
                AlertDialog.Builder nieawans = new AlertDialog.Builder(this);
                nieawans.setMessage("You were downgraded from magic level " + player_magic_level + " to level " + (player_magic_level - 1) + "!")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create();
                nieawans.show();
                player_magic_level -= 1;
            }
        }

    }*/

    //Go to ShopActivity
    public void shop(View view) {

        if(haveNetworkConnection()) {

            //Clear textView from MainActivity
            TextView mainActivity = (TextView) findViewById(R.id.mainTextView);
            mainActivity.clearComposingText();
            mainActivity.scrollTo(0, 0);

            startActivity(new Intent(getApplicationContext(), ShopActivity.class));
            finish();

        }else {
            Toast.makeText(MainActivity.this,"No network connection.", Toast.LENGTH_LONG).show();
        }

    }

    //Action after tapping Back Key
    @Override
    public void onBackPressed() {

        if(haveNetworkConnection() != true){

            AlertDialog.Builder exit = new AlertDialog.Builder(this);
            exit.setMessage("Are you sure you want to exit the application? Progress won't be saved without network connection.")
                    .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            System.exit(0);
                        }
                    })
                    .setPositiveButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create();
            exit.show();

        }else {

            AlertDialog.Builder exit = new AlertDialog.Builder(this);
            exit.setMessage("Are you sure you want to exit the application?")
                    .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            saveGame();
                            finish();
                            System.exit(0);
                        }
                    })
                    .setPositiveButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create();
            exit.show();

        }
    }

    //Go to QuestActivity
    public void quests(View view) {

        if(haveNetworkConnection()) {

            //Clear textView from MainActivity
            TextView mainActivity = (TextView) findViewById(R.id.mainTextView);
            mainActivity.clearComposingText();
            mainActivity.scrollTo(0, 0);

            startActivity(new Intent(getApplicationContext(), QuestsActivity.class));
            finish();

        }else {
            Toast.makeText(MainActivity.this,"No network connection.", Toast.LENGTH_LONG).show();
        }

    }

    //Go to DepotActivity
    public void depot(View view) {

        if(haveNetworkConnection()) {

            //Clear textView from MainActivity
            TextView mainActivity = (TextView) findViewById(R.id.mainTextView);
            mainActivity.clearComposingText();
            mainActivity.scrollTo(0, 0);

            startActivity(new Intent(getApplicationContext(), DepotActivity.class));
            finish();

        }else {
            Toast.makeText(MainActivity.this,"No network connection.", Toast.LENGTH_LONG).show();
        }

    }

    //Go to LoginActivity
    public void logout(View view){

        //Clear textView from MainActivity
        TextView mainActivity = (TextView) findViewById(R.id.mainTextView);
        mainActivity.clearComposingText();
        mainActivity.scrollTo(0,0);

        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();

    }

    public void getPlayerId() {

        loadingGetPlayerID = ProgressDialog.show(this, "Please wait...", "Loading...", false, false);

        String url = ConfigPlayerID.DATA_URL+player_name;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loadingGetPlayerID.dismiss();
                showJSONgetPlayerStats(response);

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    public void getPlayerIdStats() {

        loadingGetPlayerIdStats = ProgressDialog.show(this, "Please wait...", "Loading...", false, false);

        String url = ConfigPlayerID.DATA_URL+player_name;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                loadingGetPlayerIdStats.dismiss();
                showJSONgetPlayerStats(response);

                //Clear textView from MainActivity
                TextView mainActivity = (TextView) findViewById(R.id.mainTextView);
                mainActivity.setMovementMethod(new ScrollingMovementMethod());
                mainActivity.clearComposingText();
                mainActivity.scrollTo(0, 0);


                //Display character's statistics
                StringBuilder statistics = new StringBuilder("");
                statistics.append("Name: " + player_name + "\n");
                statistics.append("Health points: " + Integer.toString(player_hp) + "\n");
                statistics.append("Mana points: " + Integer.toString(player_mp) + "\n");
                statistics.append("Level: " + Integer.toString(player_level) + "\n");
                statistics.append("Magic level: " + Integer.toString(player_magic_level) + "\n");
                statistics.append("Attack: " + Integer.toString(player_attack) + "+" + Integer.toString(weapon.attack) + "\n");
                statistics.append("Defence: " + Integer.toString(player_defence) + "+" + Integer.toString(shield.defence) + "\n");
                statistics.append("Armor: " + Integer.toString(helmet.armor+armor.armor+legs.armor+boots.armor+amulet.armor+
                        ring_left.armor+ring_right.armor) + "\n");
                statistics.append("Experience: " + Integer.toString(player_experience) + "\n");
                statistics.append("Gold: " + Integer.toString(player_gold));
                mainActivity.setText(statistics);

            }
        },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();

                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void showJSONgetPlayerStats(String json) {

        String  playerId = "", playerHp = "", playerMp = "", playerLevel = "", playerMagicLevel = "", playerExperience = "",
                playerGold = "", playerCapacity = "", playerAttack = "", playerDefence = "", playerCritical = "",
                playerCriticalChance = "", playerSkillPoints = "", playerMagicExperience = "", playerEqHelmet = "",
                playerEqArmor = "", playerEqLegs = "", playerEqBoots = "", playerEqWeapon = "", playerEqShield = "",
                playerEqAmulet = "", playerEqRingLeft = "", playerEqRingRight = "";

        //Get data from database
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(ConfigPlayerID.JSON_ARRAY);//name of class
            JSONObject collegeData = result.getJSONObject(0);
            playerId = collegeData.getString(ConfigPlayerID.KEY_ID);
            playerHp = collegeData.getString(ConfigPlayerID.KEY_HP);
            playerMp = collegeData.getString(ConfigPlayerID.KEY_MP);
            playerLevel = collegeData.getString(ConfigPlayerID.KEY_LEVEL);
            playerMagicLevel = collegeData.getString(ConfigPlayerID.KEY_MAGIC_LEVEL);
            playerExperience = collegeData.getString(ConfigPlayerID.KEY_EXPERIENCE);
            playerGold = collegeData.getString(ConfigPlayerID.KEY_GOLD);
            playerCapacity = collegeData.getString(ConfigPlayerID.KEY_CAPACITY);
            playerAttack = collegeData.getString(ConfigPlayerID.KEY_ATTACK);
            playerDefence = collegeData.getString(ConfigPlayerID.KEY_DEFENCE);
            playerCritical = collegeData.getString(ConfigPlayerID.KEY_CRITICAL);
            playerCriticalChance = collegeData.getString(ConfigPlayerID.KEY_CRITICAL_CHANCE);
            playerSkillPoints = collegeData.getString(ConfigPlayerID.KEY_SKILL_POINTS);
            playerMagicExperience = collegeData.getString(ConfigPlayerID.KEY_MAGIC_EXPERIENCE);
            playerEqHelmet = collegeData.getString(ConfigPlayerID.KEY_EQ_HELMET);
            playerEqArmor = collegeData.getString(ConfigPlayerID.KEY_EQ_ARMOR);
            playerEqLegs = collegeData.getString(ConfigPlayerID.KEY_EQ_LEGS);
            playerEqBoots = collegeData.getString(ConfigPlayerID.KEY_EQ_BOOTS);
            playerEqWeapon = collegeData.getString(ConfigPlayerID.KEY_EQ_WEAPON);
            playerEqShield = collegeData.getString(ConfigPlayerID.KEY_EQ_SHIELD);
            playerEqAmulet = collegeData.getString(ConfigPlayerID.KEY_EQ_AMULET);
            playerEqRingLeft = collegeData.getString(ConfigPlayerID.KEY_EQ_RING_LEFT);
            playerEqRingRight = collegeData.getString(ConfigPlayerID.KEY_EQ_RING_RIGHT);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        id_player = playerId;
        player_hp = Integer.parseInt(playerHp);
        player_mp = Integer.parseInt(playerMp);
        player_level = Integer.parseInt(playerLevel);
        player_magic_level = Integer.parseInt(playerMagicLevel);
        player_experience = Integer.parseInt(playerExperience);
        player_gold = Integer.parseInt(playerGold);
        player_capacity = Integer.parseInt(playerCapacity);
        player_attack = Integer.parseInt(playerAttack);
        player_defence = Integer.parseInt(playerDefence);
        player_critical = Integer.parseInt(playerCritical);
        player_critical_chance = Integer.parseInt(playerCriticalChance);
        player_skill_points = Integer.parseInt(playerSkillPoints);
        player_magic_experience = Integer.parseInt(playerMagicExperience);
        player_eq_helmet = Integer.parseInt(playerEqHelmet);
        player_eq_armor = Integer.parseInt(playerEqArmor);
        player_eq_legs = Integer.parseInt(playerEqLegs);
        player_eq_boots = Integer.parseInt(playerEqBoots);
        player_eq_weapon = Integer.parseInt(playerEqWeapon);
        player_eq_shield = Integer.parseInt(playerEqShield);
        player_eq_amulet = Integer.parseInt(playerEqAmulet);
        player_eq_ring_left = Integer.parseInt(playerEqRingLeft);
        player_eq_ring_right = Integer.parseInt(playerEqRingRight);

        saveGame();

        //Set items id to objects
        weapon.id = player_eq_weapon;
        shield.id = player_eq_shield;
        helmet.id = player_eq_helmet;
        armor.id = player_eq_armor;
        legs.id = player_eq_legs;
        boots.id = player_eq_boots;
        shield.id = player_eq_shield;
        amulet.id = player_eq_amulet;
        ring_left.id = player_eq_ring_left;
        ring_right.id = player_eq_ring_right;

        //Fill item objects
        getItemWeaponStats(weapon.id);
        getItemShieldStats(shield.id);
        getItemArmorStats(helmet.id);
        getItemArmorStats(armor.id);
        getItemArmorStats(legs.id);
        getItemArmorStats(boots.id);
        getItemArmorStats(amulet.id);
        getItemArmorStats(ring_left.id);
        getItemArmorStats(ring_right.id);

    }

    public void getItemWeaponStats(int get_id){

        loadingGetItemWeaponStats = ProgressDialog.show(this, "Please wait...", "Loading...", false, false);

        String url = ConfigGetItemWeaponStats.DATA_URL_GET_WEAPON+get_id; //URL taken from Config.java and added number after it

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loadingGetItemWeaponStats.dismiss();
                showJSONGetItemWeaponStats(response);

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void showJSONGetItemWeaponStats(String json){

        String  weaponName = "", weaponAttack = "", weaponDefence = "", weaponElementalAttack = "",
                weaponBonus = "", weaponWeight = "";

        //Get data from database
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(ConfigGetItemWeaponStats.JSON_ARRAY);//name of class
            JSONObject collegeData = result.getJSONObject(0);
            weaponName = collegeData.getString(ConfigGetItemWeaponStats.KEY_NAME);
            weaponAttack = collegeData.getString(ConfigGetItemWeaponStats.KEY_ATTACK);
            weaponDefence = collegeData.getString(ConfigGetItemWeaponStats.KEY_DEFENCE);
            weaponElementalAttack = collegeData.getString(ConfigGetItemWeaponStats.KEY_ELEMENTAL_ATTACK);
            weaponBonus = collegeData.getString(ConfigGetItemWeaponStats.KEY_BONUS);
            weaponWeight = collegeData.getString(ConfigGetItemWeaponStats.KEY_WEIGHT);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Fill statistics for character's weapon
        if(player_eq_weapon != 0){
        weapon.name = weaponName;
        weapon.attack = Integer.parseInt(weaponAttack);
        weapon.defence = Integer.parseInt(weaponDefence);
        weapon.elemental_attack = weaponElementalAttack;
        weapon.bonus = Integer.parseInt(weaponBonus);
        weapon.weight = Double.parseDouble(weaponWeight);}

    }

    public void getItemShieldStats(int get_id){

        loadingGetItemShieldStats = ProgressDialog.show(this, "Please wait...", "Loading...", false, false);

        String url = ConfigGetItemShieldStats.DATA_URL_GET_SHIELD+get_id; //URL taken from Config.java and added number after it

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loadingGetItemShieldStats.dismiss();
                showJSONGetItemShieldStats(response);

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void showJSONGetItemShieldStats(String json){

        String  shieldName = "", shieldDefence = "", shieldImmunity = "",
                shieldBonus = "", shieldWeight = "";

        //Get data from database
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(ConfigGetItemShieldStats.JSON_ARRAY);//name of class
            JSONObject collegeData = result.getJSONObject(0);
            shieldName = collegeData.getString(ConfigGetItemShieldStats.KEY_NAME);
            shieldDefence = collegeData.getString(ConfigGetItemShieldStats.KEY_DEFENCE);
            shieldImmunity = collegeData.getString(ConfigGetItemShieldStats.KEY_IMMUNITY);
            shieldBonus = collegeData.getString(ConfigGetItemShieldStats.KEY_BONUS);
            shieldWeight = collegeData.getString(ConfigGetItemShieldStats.KEY_WEIGHT);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Fill statistics for character's shield
        if(player_eq_shield != 0){
        shield.name = shieldName;
        shield.defence = Integer.parseInt(shieldDefence);
        shield.immunity = shieldImmunity;
        shield.bonus = Integer.parseInt(shieldBonus);
        shield.weight = Double.parseDouble(shieldWeight);}

    }

    public void getItemArmorStats(final int get_id){

        String url = ConfigGetItemArmorStats.DATA_URL_GET_ARMOR+get_id; //URL taken from Config.java and added number after it

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                    showJSONGetItemArmorStats(response, get_id);

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void showJSONGetItemArmorStats(String json, int get_id){

        String  armorName = "", armorArmor = "", armorAttribute = "",
                armorValue = "", armorImmunity = "", armorBonus = "", armorWeight = "";

        //Get data from database
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(ConfigGetItemArmorStats.JSON_ARRAY);//name of class
            JSONObject collegeData = result.getJSONObject(0);
            armorName = collegeData.getString(ConfigGetItemArmorStats.KEY_NAME);
            armorArmor = collegeData.getString(ConfigGetItemArmorStats.KEY_ARMOR);
            armorAttribute = collegeData.getString(ConfigGetItemArmorStats.KEY_ATTRIBUTE);
            armorValue = collegeData.getString(ConfigGetItemArmorStats.KEY_VALUE);
            armorImmunity = collegeData.getString(ConfigGetItemArmorStats.KEY_IMMUNITY);
            armorBonus = collegeData.getString(ConfigGetItemArmorStats.KEY_BONUS);
            armorWeight = collegeData.getString(ConfigGetItemArmorStats.KEY_WEIGHT);


        } catch (JSONException e) {
            e.printStackTrace();
        }



        //Fill statistics for character's armor type items
        if(player_eq_helmet != 0){

            if(get_id == player_eq_helmet){
                helmet.name = armorName;
                helmet.armor = Integer.parseInt(armorArmor);
                helmet.attribute = armorAttribute;
                helmet.value = Integer.parseInt(armorValue);
                helmet.immunity = armorImmunity;
                helmet.bonus = Integer.parseInt(armorBonus);
                helmet.weight = Double.parseDouble(armorWeight);
            }
        }

        if(player_eq_armor != 0){

            if(get_id == player_eq_armor) {
                armor.name = armorName;
                armor.armor = Integer.parseInt(armorArmor);
                armor.attribute = armorAttribute;
                armor.value = Integer.parseInt(armorValue);
                armor.immunity = armorImmunity;
                armor.bonus = Integer.parseInt(armorBonus);
                armor.weight = Double.parseDouble(armorWeight);
            }
        }

        if(player_eq_legs != 0){

            if(get_id == player_eq_legs){
                legs.name = armorName;
                legs.armor = Integer.parseInt(armorArmor);
                legs.attribute = armorAttribute;
                legs.value = Integer.parseInt(armorValue);
                legs.immunity = armorImmunity;
                legs.bonus = Integer.parseInt(armorBonus);
                legs.weight = Double.parseDouble(armorWeight);
            }
        }

        if(player_eq_boots != 0){

            if(get_id == player_eq_boots){
                boots.name = armorName;
                boots.armor = Integer.parseInt(armorArmor);
                boots.attribute = armorAttribute;
                boots.value = Integer.parseInt(armorValue);
                boots.immunity = armorImmunity;
                boots.bonus = Integer.parseInt(armorBonus);
                boots.weight = Double.parseDouble(armorWeight);
            }
        }

        if(player_eq_amulet != 0){

            if(get_id == player_eq_amulet){
                amulet.name = armorName;
                amulet.armor = Integer.parseInt(armorArmor);
                amulet.attribute = armorAttribute;
                amulet.value = Integer.parseInt(armorValue);
                amulet.immunity = armorImmunity;
                amulet.bonus = Integer.parseInt(armorBonus);
                amulet.weight = Double.parseDouble(armorWeight);
            }
        }

        if(player_eq_ring_left != 0){

            if(get_id == player_eq_ring_left) {
                ring_left.name = armorName;
                ring_left.armor = Integer.parseInt(armorArmor);
                ring_left.attribute = armorAttribute;
                ring_left.value = Integer.parseInt(armorValue);
                ring_left.immunity = armorImmunity;
                ring_left.bonus = Integer.parseInt(armorBonus);
                ring_left.weight = Double.parseDouble(armorWeight);
            }
        }

        if(player_eq_ring_right != 0){

            if(get_id == player_eq_ring_right){
                ring_right.name = armorName;
                ring_right.armor = Integer.parseInt(armorArmor);
                ring_right.attribute = armorAttribute;
                ring_right.value = Integer.parseInt(armorValue);
                ring_right.immunity = armorImmunity;
                ring_right.bonus = Integer.parseInt(armorBonus);
                ring_right.weight = Double.parseDouble(armorWeight);
            }
        }


    }

    private boolean haveNetworkConnection() {

        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;

    }

}



