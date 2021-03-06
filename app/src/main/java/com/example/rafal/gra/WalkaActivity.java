package com.example.rafal.gra;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WalkaActivity extends AppCompatActivity {

    //Temporary monster
    Monsters  monster;

    public static String[] lootItems;
    public static String[] lootIds;

    String URL_SAVE_LOOT = "https://mundial2018.000webhostapp.com/saveLoot.php";
    String URL_SAVE_SAME_LOOT = "https://mundial2018.000webhostapp.com/saveSameLoot.php";
    String URL_CHECK_ITEM_EXIST = "https://mundial2018.000webhostapp.com/checkItemExist.php?id_player=";
    public static final String URL_SAVE_SKILLS = "https://mundial2018.000webhostapp.com/saveSkillsWalka.php";
    public static final String DATA_URL_LOOT = "https://mundial2018.000webhostapp.com/getLootByMonsterID.php?id=";

    private ProgressDialog loadingGetData, loadingGetPlayerId;

    int  killedGoblinWarrior, killedGoblinMage, killedFireDragon, killedWaterDragon, questStarted,
            check_quantity;

    int player_hp, player_mp, player_attack, player_defence, player_gold, player_skill_points,
            player_health_potions, player_mana_potions, player_critical, player_critical_chance,
            player_magic_level, player_level, player_experience, player_magic_experience;

    String id = "", id_player, player_name, check_id_player;

    //Get data from PolujActivity
    Integer expPlace = PolujActivity.dataPacketHunt.getInt("expPlace");

    StringBuilder fightWindow = new StringBuilder("");

    int currentExp = player_experience, currentMagicExp = player_magic_experience,
            currentMoney = player_gold, afterRun = 0, roundNumber = 1,
            playerHpTemp, playerManaTemp, checkLvlAdvance, checkMlvlAdvance, round = 0, firstAttackSpell = 0,
            monsterHpTemp, monsterAtk, monsterDef, monsterExp, monsterGoldMax;
    double loot, youHit, mobHit;
    boolean slaying;

    ArrayList<Integer> expTable = new ArrayList<Integer>();
    ArrayList<Integer> magicExpTable = new ArrayList<Integer>();

    public ProgressBar progressBarHp, progressBarMana, progressBarMonster;

    //Place to save temporary data
    public static Bundle dataPacketFight = new Bundle();

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        loadGame();
        getPlayerId();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walka);

        Button fight = (Button) findViewById(R.id.fight);
        Button spell = (Button) findViewById(R.id.spell);
        Button heal = (Button) findViewById(R.id.heal);
        Button potions = (Button) findViewById(R.id.potions);

        //Action after pressing button Potions
        potions.setOnClickListener(new View.OnClickListener() {

            //Creating popup with buttons
            public void onClick(View v) {

                final Dialog dialog = new Dialog(WalkaActivity.this);
                dialog.setTitle("Potions");
                dialog.setContentView(R.layout.popup_potions);
                dialog.show();

                Button hp = (Button) dialog.findViewById(R.id.hpotion);
                Button mp = (Button) dialog.findViewById(R.id.mpotion);
                hp.setText("HP: " + Integer.toString(player_health_potions));
                mp.setText("MP: " + Integer.toString(player_mana_potions));

                //Check amount of potions, health and mana potions of character and grey out buttons
                if (player_health_potions == 0) {
                    hp.setEnabled(false);
                    hp.setClickable(false);
                }

                if (player_mana_potions == 0) {
                    mp.setEnabled(false);
                    mp.setClickable(false);
                }

                if (playerHpTemp + 25 >= player_hp) {
                    playerHpTemp = player_hp;
                    hp.setEnabled(false);
                    hp.setClickable(false);
                }

                if (playerManaTemp + 25 >= player_mp) {
                    playerManaTemp = player_mp;
                    mp.setEnabled(false);
                    mp.setClickable(false);
                }

                //Action after pressing button HP
                hp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (playerHpTemp + 25 >= player_hp) {
                            playerHpTemp = player_hp;
                            Button hpot = (Button) dialog.findViewById(R.id.hpotion);
                            hpot.setEnabled(false);
                            hpot.setClickable(false);
                        } else {
                            playerHpTemp += 25;
                        }
                        player_health_potions -= 1;
                        Button hpot = (Button) dialog.findViewById(R.id.hpotion);
                        hpot.setText("HP: " + Integer.toString(player_health_potions));
                        progressBarHp.setProgress(playerHpTemp);
                        TextView hp = (TextView) findViewById(R.id.textViewHp);
                        hp.setText(playerHpTemp + "/" + player_hp);
                        if (player_health_potions == 0) {
                            hpot.setEnabled(false);
                            hpot.setClickable(false);
                        }
                        saveGame();
                    }
                });

                //Action after pressing button HP
                mp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (playerManaTemp + 25 >= player_mp) {
                            playerManaTemp = player_mp;
                            Button mp = (Button) dialog.findViewById(R.id.mpotion);
                            mp.setEnabled(false);
                            mp.setClickable(false);
                        } else {
                            playerManaTemp += 25;
                        }
                        player_mana_potions -= 1;
                        Button mp = (Button) dialog.findViewById(R.id.mpotion);
                        mp.setText("MP: " + Integer.toString(player_mana_potions));
                        progressBarMana.setProgress(playerManaTemp);
                        TextView mana = (TextView) findViewById(R.id.textViewManaP);
                        mana.setText(playerManaTemp + "/" + player_mp);
                        if (player_mana_potions == 0) {
                            mp.setEnabled(false);
                            mp.setClickable(false);
                        }
                        saveGame();
                    }
                });
            }

        });

        //Grey out buttons when there is no fight
        fight.setClickable(false);
        fight.setEnabled(false);
        spell.setEnabled(false);
        spell.setClickable(false);
        heal.setEnabled(false);
        heal.setClickable(false);
        potions.setEnabled(false);
        potions.setClickable(false);

        monster = new Monsters("", 0, 0, 0, 0, 0);

        fillExperienceTable();

        fillMagicExperienceTable();

    }

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

    //Drawing a monster
    public void monster() {

        if (expPlace == 0) {

            if (Math.floor(Math.random() * 10) > 5) {
                id = "2000";
            } else {
                id = "2001";

            }
        } else {

            if (Math.floor(Math.random() * 10) > 5) {
                id = "2002";
            } else {
                id = "2003";
            }

        }

        //Get statistics and loot from drew monster
        getData();
        getLoot();

        //Save current character's hp and mana to future calculation
        playerHpTemp = player_hp;
        monsterHpTemp = monster.hp;
        playerManaTemp = player_mp;

    }

    //Start a fight
    public void startFight(View view) {

        //Refreshing monster's hp bar
        TextView monsterHealthPoints = (TextView) findViewById(R.id.textViewMonster);

        TextView fight = (TextView) findViewById(R.id.fightWindow);
        fight.setMovementMethod(new ScrollingMovementMethod());

        slaying = true;
        firstAttackSpell = 0;

        if (playerHpTemp > 0 && monsterHpTemp > 0 && slaying == true) {

            //Drawing a chance for a critical chance
            double checkCrit = Math.floor(Math.random() * 100);

            double crit = Math.floor((player_attack * 2 - player_attack / 2 + 1) + player_attack / 2 - monster.def) +
                    (Math.floor((player_attack * 2 - player_attack / 2 + 1) + player_attack / 2 - monster.def) * (player_critical / 100));
            youHit = (Math.floor(Math.random() * (player_attack * 10 - player_attack / 2 + 1)) + player_attack / 2 - monster.def);
            mobHit = Math.floor((Math.random() * 2) * (monster.atk * 2 - (player_defence / 2)));

            if (mobHit < 0) {
                mobHit = 0;
            }

            if (round == 0) {
                fightWindow.append("Round " + roundNumber + "\n");
            }

            if (checkCrit < player_critical_chance) {
                if (monsterHpTemp - (int) crit < 0) {
                    fightWindow.append("A " + monster.name + " loses " + monsterHpTemp + " health points due to your critical attack.\n");
                    monsterHpTemp = 0;
                } else {
                    monsterHpTemp -= (int) crit;
                    fightWindow.append("A " + monster.name + " loses " + (int) crit + " health points due to your critical attack.\n");
                }
            } else {
                if ((int) youHit < 0) {
                    youHit = 0;
                    fightWindow.append("A " + monster.name + " loses " + (int) youHit + " health points due to your attack.\n");
                    monsterHpTemp -= (int) youHit;
                } else {
                    if (monsterHpTemp - (int) youHit < 0) {
                        fightWindow.append("A " + monster.name + " loses " + monsterHpTemp + " health points due to your attack.\n");
                        monsterHpTemp = 0;
                    } else {
                        monsterHpTemp -= (int) youHit;
                        fightWindow.append("A " + monster.name + " loses " + (int) youHit + " health points due to your attack.\n");
                    }
                }

            }

            //Update monster hp bar
            if (monsterHpTemp < 0) {
                progressBarMonster.setProgress(0);
                monsterHealthPoints.setText(0 + "/" + monster.hp);
            } else {
                progressBarMonster.setProgress(monsterHpTemp);
                monsterHealthPoints.setText(monsterHpTemp + "/" + monster.hp);
            }

            //Update character's bars
            if (monsterHpTemp > 0) {
                fightWindow.append("You lose " + (int) mobHit + " health points due to an attack by a " + monster.name + ".\n");
                playerHpTemp -= (int) mobHit;
                if (playerHpTemp < 0) {
                    progressBarHp.setProgress(0);
                    TextView hp = (TextView) findViewById(R.id.textViewHp);
                    hp.setText(0 + "/" + player_hp);
                } else {
                    progressBarHp.setProgress(playerHpTemp);
                    TextView hp = (TextView) findViewById(R.id.textViewHp);
                    hp.setText(playerHpTemp + "/" + player_hp);
                }

            }
            roundNumber += 1;
        }

        duringFight();

    }

    //Get monster's statistics
    private void getData() {

        loadingGetData = ProgressDialog.show(this, "Please wait...", "Loading...", false, false);

        String url = ConfigMonster.DATA_URL + id;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loadingGetData.dismiss();
                showJSONmonster(response);

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(WalkaActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void showJSONmonster(String json) {

        String name = "";
        String hp = "";
        String attack = "";
        String defence = "";
        String experience = "";
        String gold_max = "";
        String image = "";

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(ConfigMonster.JSON_ARRAY);//name of class
            JSONObject collegeData = result.getJSONObject(0);
            name = collegeData.getString(ConfigMonster.KEY_NAME);
            hp = collegeData.getString(ConfigMonster.KEY_HP);
            attack = collegeData.getString(ConfigMonster.KEY_ATTACK);
            defence = collegeData.getString(ConfigMonster.KEY_DEFENCE);
            experience = collegeData.getString(ConfigMonster.KEY_EXPERIENCE);
            gold_max = collegeData.getString(ConfigMonster.KEY_GOLD_MAX);
            image = collegeData.getString(ConfigMonster.KEY_IMAGE);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        monsterHpTemp = Integer.parseInt(hp);
        monsterAtk = Integer.parseInt(attack);
        monsterDef = Integer.parseInt(defence);
        monsterExp = Integer.parseInt(experience);
        monsterGoldMax = Integer.parseInt(gold_max);

        monster.name = name;
        monster.hp = monsterHpTemp;
        monster.atk = monsterAtk;
        monster.def = monsterDef;
        monster.exp = monsterExp;
        monster.goldMax = monsterGoldMax;

    }

    private void getLoot() {

        StringRequest stringRequest = new StringRequest(DATA_URL_LOOT + id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String json) {
                        showJSONloot(json);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(WalkaActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    public void showJSONloot(String json) {

        GetLoot pj = new GetLoot(json);
        pj.GetLoot();
        lootItems = new String[GetLoot.items.length];
        lootIds = new String[GetLoot.ids.length];

        for (int i = 0; i < GetLoot.items.length; i++) {

            int rarityChance = Integer.parseInt(GetLoot.chances[i]);
            int randomNumber = randomWithRange(0, 300); //loot chance
            if (randomNumber <= rarityChance) {
                lootItems[i] = GetLoot.items[i];
                lootIds[i] = GetLoot.ids[i];
            } else {
                lootItems[i] = "";
                lootIds[i] = "";
            }

        }

    }

    public void saveGame() {

        SharedPreferences saveGame = getSharedPreferences("Save", MODE_PRIVATE);
        SharedPreferences.Editor save = saveGame.edit();
        save.putInt("checkLvlAdvance", checkLvlAdvance);
        save.putInt("checkMlvlAdvance", checkMlvlAdvance);
        save.putInt("killedGoblinWarrior", killedGoblinWarrior);
        save.putInt("killedGoblinMage", killedGoblinMage);
        save.putInt("killedFireDragon", killedFireDragon);
        save.putInt("killedWaterDragon", killedWaterDragon);
        save.putInt("questStarted", questStarted);
        save.apply();

    }

    public void loadGame() {

        SharedPreferences loadE = getSharedPreferences("Save", MODE_PRIVATE);

        checkLvlAdvance = loadE.getInt("checkLvlAdvance", 1);
        checkMlvlAdvance = loadE.getInt("checkMlvlAdvance", 0);
        killedGoblinWarrior = loadE.getInt("killedGoblinWarrior", 0);
        killedGoblinMage = loadE.getInt("killedGoblinMage", 0);
        killedFireDragon = loadE.getInt("killedFireDragon", 0);
        killedWaterDragon = loadE.getInt("killedWaterDragon", 0);
        questStarted = loadE.getInt("questStarted", 0);
        id_player = loadE.getString("id_player", "");
        player_name = loadE.getString("player_name", "");

    }

    //Go to MainActivity
    public void escape(View view) {

        saveGame();
        saveSkills();

        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        afterRun = 1;
        WalkaActivity.dataPacketFight.putInt("afterRun", afterRun);
        finish();

    }

    //Action after using spell
    public void spell(View view) {

        slaying = true;

        if (playerManaTemp >= 50) {
            if (player_magic_level == 0) {
                youHit = Math.floor(((Math.random() * 2) + 1) + 7);
            } else {
                youHit = Math.floor(((Math.random() * 2) + 1) * (player_magic_level * 7));
            }

            if (firstAttackSpell == 0) {
                fightWindow.append("Round " + roundNumber + "\n");
                if (monsterHpTemp - (int) youHit < 0) {
                    fightWindow.append("A " + monster.name + " loses " + monsterHpTemp + " health points due to your magic attack.\n");
                    monsterHpTemp = 0;
                } else {
                    fightWindow.append("A " + monster.name + " loses " + (int) youHit + " health points due to your magic attack.\n");
                    monsterHpTemp -= (int) youHit;
                }
            } else {
                if (monsterHpTemp - (int) youHit < 0) {
                    fightWindow.append("A " + monster.name + " loses " + monsterHpTemp + " health points due to your magic attack.\n");
                    monsterHpTemp = 0;
                } else {
                    fightWindow.append("A " + monster.name + " loses " + (int) youHit + " health points due to your magic attack.\n");
                    monsterHpTemp -= (int) youHit;
                }
            }

            player_magic_experience += 15;

            //Update monster's bar
            progressBarMonster.setProgress(monsterHpTemp);
            TextView monsterHealthPoints = (TextView) findViewById(R.id.textViewMonster);
            monsterHealthPoints.setText(monsterHpTemp + "/" + monster.hp);

            //Display data on TextView
            TextView walka = (TextView) findViewById(R.id.fightWindow);
            walka.setText(fightWindow);

            //Subtraction mana points after using spell
            playerManaTemp -= 50;

            //Update mana points bar
            progressBarMana.setProgress(playerManaTemp);
            TextView mana = (TextView) findViewById(R.id.textViewManaP);
            mana.setText(playerManaTemp + "/" + player_mp);

            duringFight();

            round = 1;
            firstAttackSpell = 1;

            //Check for magic level advance
            for (int i = 100; i >= 0; i--) {

                if (player_magic_experience >= magicExpTable.get(i) && player_magic_experience > 1599) {
                    player_magic_level = i;
                    if (player_magic_level > checkMlvlAdvance) {

                        AlertDialog.Builder awans = new AlertDialog.Builder(this);
                        awans.setMessage("You advanced to magic level " + i + "!")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .create();
                        awans.show();

                        checkMlvlAdvance += 1;
                        saveGame();
                    }
                    break;
                }
            }

        } else {

            AlertDialog.Builder lessMana = new AlertDialog.Builder(this);
            lessMana.setMessage("You have not enough mana!")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create();
            lessMana.show();
        }
    }

    //Action after using heal
    public void heal(View view) {

        slaying = true;
        double heal;

        if (playerManaTemp >= 100) {
            if (player_magic_level == 0) {
                heal = (Math.floor(Math.random() * (15 - 5 + 1)) + 5);
            } else {
                heal = (Math.floor(Math.random() * (((player_magic_level / 0.05) + 40) -
                        player_magic_level / 0.05 + 1)) + player_magic_level / 0.05);
            }

            if (firstAttackSpell == 0) {
                fightWindow.append("Round " + roundNumber + "\n");
                if (playerHpTemp == player_hp) {
                    fightWindow.append("You have full health points. \n");
                } else if ((playerHpTemp + (int) heal) > player_hp) {
                    fightWindow.append("You healed for " + (player_hp - playerHpTemp) + " health points.\n");
                } else {
                    fightWindow.append("You healed for " + (int) heal + " health points.\n");
                }
            } else {
                if (playerHpTemp == player_hp) {
                    fightWindow.append("You have full health points. \n");
                } else if ((playerHpTemp + (int) heal) > player_hp) {
                    fightWindow.append("You healed for " + (player_hp - playerHpTemp) + " health points.\n");
                } else {
                    fightWindow.append("You healed for " + (int) heal + " health points.\n");
                }
            }
            TextView fight = (TextView) findViewById(R.id.fightWindow);
            if ((playerHpTemp + (int) heal) > player_hp) {
                playerHpTemp = player_hp;
            } else {
                playerHpTemp += (int) heal;
            }

            //Magic experience for using heal
            player_magic_experience += 35;

            //Display data on TextView
            fight.setText(fightWindow);

            //Subtraction mana points for using heal
            playerManaTemp -= 100;

            //Update character's health and mana bars
            TextView mana = (TextView) findViewById(R.id.textViewManaP);
            TextView hp = (TextView) findViewById(R.id.textViewHp);
            hp.setText(playerHpTemp + "/" + player_hp);
            mana.setText(playerManaTemp + "/" + player_mp);
            progressBarHp.setProgress(playerHpTemp);
            progressBarMana.setProgress(playerManaTemp);

            round = 1;
            firstAttackSpell = 1;

            //Check for magic level advance
            for (int i = 100; i >= 0; i--) {
                if (player_magic_experience >= magicExpTable.get(i) && player_magic_experience > 59) {
                    player_magic_level = i;
                    if (player_magic_level > checkMlvlAdvance) {

                        AlertDialog.Builder awans = new AlertDialog.Builder(this);
                        awans.setMessage("You advanced to magic level " + i + "!")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .create();
                        awans.show();

                        checkMlvlAdvance += 1;
                        saveGame();
                    }
                    break;
                }
            }

        } else {
            AlertDialog.Builder lessMana = new AlertDialog.Builder(this);
            lessMana.setMessage("You have not enough mana!")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create();
            lessMana.show();
        }

    }

    //Setting health and mana bars
    public void resetBars() {

        progressBarHp = (ProgressBar) findViewById(R.id.progressBarHp);
        progressBarMana = (ProgressBar) findViewById(R.id.progressBarMana);
        progressBarMonster = (ProgressBar) findViewById(R.id.progressBarMonster);

        progressBarHp.setProgress(playerHpTemp);
        progressBarHp.setMax(player_hp);
        progressBarHp.setProgress(player_hp);

        progressBarMana.setProgress(playerManaTemp);
        progressBarMana.setMax(player_mp);
        progressBarMana.setProgress(player_mp);

        TextView hp = (TextView) findViewById(R.id.textViewHp);
        TextView mana = (TextView) findViewById(R.id.textViewManaP);

        hp.setText(player_hp + "/" + player_hp);
        mana.setText(player_mp + "/" + player_mp);

    }

    //Draw a random number from min to max value
    int randomWithRange(int min, int max) {
        int range = (max - min) + 1;
        return (int) (Math.random() * range) + min;
    }

    //Exploring
    public void explore(View view) throws InterruptedException {

        resetBars();

        TextView monsterHealthPoints = (TextView) findViewById(R.id.textViewMonster);

        progressBarMonster.setMax(monster.hp);
        progressBarMonster.setProgress(monsterHpTemp);
        monsterHealthPoints.setText(monster.hp + "/" + monster.hp);

        loot = randomWithRange(0, monster.goldMax);

        firstAttackSpell = 0;

        AlertDialog.Builder atak = new AlertDialog.Builder(this);
        atak.setMessage(monster.name + " has attacked you!\n")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        atak.show();

        TextView monsterName = (TextView) findViewById(R.id.monsterName);
        TextView fightTextView = (TextView) findViewById(R.id.fightWindow);

        fightTextView.setMovementMethod(new ScrollingMovementMethod());
        monsterName.clearComposingText();
        fightTextView.clearComposingText();
        fightTextView.scrollTo(0, 0);

        StringBuilder v = new StringBuilder("");
        v.append(monster.name);
        monsterName.setText(v);

        StringBuilder m = new StringBuilder("");
        m.append("");
        fightTextView.setText(m);
        fightWindow.delete(0, fightWindow.length());

        roundNumber = 1;

        Button explore = (Button) findViewById(R.id.explore);
        Button fight = (Button) findViewById(R.id.fight);
        Button spell = (Button) findViewById(R.id.spell);
        Button heal = (Button) findViewById(R.id.heal);
        Button potions = (Button) findViewById(R.id.potions);

        //Grey out and activate buttons after starting a fight
        explore.setClickable(false);
        explore.setEnabled(false);
        fight.setClickable(true);
        fight.setEnabled(true);
        spell.setEnabled(true);
        spell.setClickable(true);
        heal.setEnabled(true);
        heal.setClickable(true);
        potions.setEnabled(true);
        potions.setClickable(true);

    }

    //Check for player's death and add loot
    public void duringFight() {

        TextView fightTextView = (TextView) findViewById(R.id.fightWindow);
        fightTextView.setMovementMethod(new ScrollingMovementMethod());

        Button spell = (Button) findViewById(R.id.spell);
        Button fight = (Button) findViewById(R.id.fight);
        Button heal = (Button) findViewById(R.id.heal);
        Button explore = (Button) findViewById(R.id.explore);
        Button potions = (Button) findViewById(R.id.potions);

        if (slaying == true) {

            //If player die
            if (playerHpTemp <= 0) {
                if (player_experience > 0) {
                    double loseExp = (Math.floor(player_experience * 0.2));
                    double loseMexp = (Math.floor(player_magic_experience * 0.23));
                    fightWindow.append("You are dead. \nYou lost " + (int) loseExp + " experience.");
                    player_experience -= (int) loseExp;
                    if (player_magic_experience >= (int) loseMexp) {
                        player_magic_experience -= (int) loseMexp;
                    }

                    AlertDialog.Builder dead = new AlertDialog.Builder(this);
                    dead.setMessage("You are dead!")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    saveGame();
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                    int afterRun = 1;
                                    WalkaActivity.dataPacketFight.putInt("afterRun", afterRun);
                                    finish();
                                }
                            })
                            .create();
                    dead.show();

                } else {
                    fightWindow.append("You are dead. \nYou lost 0 experience.");
                }

                slaying = false;

                fightTextView.setText(fightWindow);

                //If monster die
            } else if (monsterHpTemp <= 0) {
                List<String> list = new ArrayList<String>(Arrays.asList(lootItems));
                list.removeAll(Collections.singleton(""));
                List<String> ids = new ArrayList<String>(Arrays.asList(lootIds));
                ids.removeAll(Collections.singleton(""));
                if (list.isEmpty()) {
                    fightWindow.append("You have defeated " + monster.name + "!" + "\n" + "You have received:" +
                            "\n" + monster.exp + " experience" + "\n" + "Loot: " + (int) loot + " gold " +
                            list.toString().replace("[", "").replace("]", "")+ "\n");
                } else {
                    fightWindow.append("You have defeated " + monster.name + "!" + "\n" + "You have received:" +
                            "\n" + monster.exp + " experience" + "\n" + "Loot: " + (int) loot + " gold, " +
                            list.toString().replace("[", "").replace("]", "")+ "\n");

                    for (int i = 0; i < list.size(); i++) {

                        int id_item = Integer.parseInt(ids.get(i));
                        checkIfItemExist(id_item);


                    }
                }

                //Adding experience and gold
                player_experience += monster.exp;
                player_gold += (int) loot;
                slaying = false;

                monster();

                explore.setClickable(true);
                explore.setEnabled(true);
                fight.setClickable(false);
                fight.setEnabled(false);
                spell.setClickable(false);
                spell.setEnabled(false);
                heal.setEnabled(false);
                heal.setClickable(false);
                potions.setEnabled(false);
                potions.setClickable(false);

                //Update number of killed monsters for quests
                switch (questStarted) {
                    case 1:
                        switch (monster.name) {
                            case "Goblin Warrior":
                                if (killedGoblinWarrior < 25) {
                                    killedGoblinWarrior += 1;
                                    fightWindow.append("You have killed " + Integer.toString(killedGoblinWarrior) + "/25 " + monster.name + "s.");
                                    if (killedGoblinWarrior == 25) {
                                        completedQuest();
                                        player_experience += 250;
                                        player_gold += 200;
                                    }
                                }
                                break;
                        }
                        break;
                    case 2:
                        switch (monster.name) {
                            case "Goblin Mage":
                                if (killedGoblinMage < 25) {
                                    killedGoblinMage += 1;
                                    fightWindow.append("You have killed " + Integer.toString(killedGoblinMage) + "/25 " + monster.name + "s.");
                                    if (killedGoblinMage == 25) {
                                        completedQuest();
                                        player_experience += 200;
                                        player_gold += 100;
                                    }
                                }
                                break;
                        }
                        break;
                    case 3:
                        switch (monster.name) {
                            case "Fire Dragon":
                                if (killedFireDragon < 25) {
                                    killedFireDragon += 1;
                                    fightWindow.append("You have killed " + Integer.toString(killedFireDragon) + "/25 " + monster.name + "s.");
                                    if (killedFireDragon == 25) {
                                        completedQuest();
                                        player_experience += 500;
                                        player_gold += 400;
                                    }
                                }
                                break;
                        }
                        break;
                    case 4:
                        switch (monster.name) {
                            case "Water Dragon":
                                if (killedWaterDragon < 25) {
                                    killedWaterDragon += 1;
                                    fightWindow.append("You have killed " + Integer.toString(killedWaterDragon) + "/25 " + monster.name + "s.");
                                    if (killedWaterDragon == 25) {
                                        completedQuest();
                                        player_experience += 500;
                                        player_gold += 400;
                                    }
                                }
                                break;
                        }
                        break;
                }
            }

            fightTextView.setText(fightWindow);
            round = 0;

        }

        //Add experience and gold to player's statistics
        currentExp += player_experience;
        currentMoney += player_gold;
        currentMagicExp += player_magic_experience;

        saveGame();

        //Check for level advance and update statistics
        for (int i = 100; i >= 0; i--) {
            if (player_experience >= expTable.get(i) && player_experience > 99) {
                player_level = i;
                if (player_level > checkLvlAdvance) {

                    AlertDialog.Builder advance = new AlertDialog.Builder(this);
                    advance.setMessage("You advanced to level " + i + "!")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .create();
                    advance.show();

                    //Adding attack and defence every 5 levels
                    if (player_level % 5 == 0) {
                        player_attack += 1;
                        player_defence += 1;
                    }

                    player_hp += 15;
                    player_mp += 5;
                    //Check if skill point has to be added
                    // (if same level is reached once again - skill point will not be added)
                    if (player_skill_points < player_level - 1) {
                        player_skill_points += 1;
                    }
                    checkLvlAdvance += 1;
                    saveGame();
                }
                break;
            }
        }

        //Check for level downgrade
        if (player_experience < expTable.get(player_level)) {

            AlertDialog.Builder downgrade = new AlertDialog.Builder(this);
            downgrade.setMessage("You were downgraded from level " + player_level + " to level " + (player_level - 1) + "!")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create();
            downgrade.show();

            player_level -= 1;
            player_attack -= 0;
            player_defence -= 0;
            player_hp -= 15;
            player_mp -= 5;
        }

        //Check for magic level downgrade
        if (player_magic_experience < magicExpTable.get(player_magic_level)) {
            AlertDialog.Builder nieawansm = new AlertDialog.Builder(this);
            nieawansm.setMessage("You were downgraded from magic level " + player_magic_level + " to level " +
                    (player_magic_level - 1) + "!")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create();
            nieawansm.show();
            player_magic_level -= 1;
        }

        saveGame();
        saveSkills();


    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder exit = new AlertDialog.Builder(this);
        exit.setMessage("Are you sure you want to exit the application?")
                .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        saveSkills();
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

    public void completedQuest() {

        questStarted = 0;
        killedGoblinWarrior = 0;
        killedGoblinMage = 0;
        killedFireDragon = 0;
        killedWaterDragon = 0;
        saveGame();

        AlertDialog.Builder start = new AlertDialog.Builder(this);
        start.setMessage("Congratulations! You have completed your " + monster.name + "s quest!")
                .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        questStarted = 0;
                        killedGoblinWarrior = 0;
                        killedGoblinMage = 0;
                        killedFireDragon = 0;
                        killedWaterDragon = 0;
                        saveGame();

                    }
                })
                .create();
        start.show();

    }

    public void saveLoot(final String id_player, final int id_item, final int quantity){

        StringRequest request = new StringRequest(Request.Method.POST, URL_SAVE_LOOT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                System.out.println(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<>();
                parameters.put("id_player", id_player);
                parameters.put("id_item", Integer.toString(id_item));
                parameters.put("quantity", Integer.toString(quantity));

                return parameters;
            }
        };

        requestQueue.add(request);

    }

    public void saveSameLoot(final String id_player, final int id_item, final int quantity){

        StringRequest request = new StringRequest(Request.Method.POST, URL_SAVE_SAME_LOOT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                System.out.println(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<>();
                parameters.put("id_player", id_player);
                parameters.put("id_item", Integer.toString(id_item));
                parameters.put("quantity", Integer.toString(quantity));

                return parameters;
            }
        };
        requestQueue.add(request);

    }

    public void getPlayerId() {

        loadingGetPlayerId = ProgressDialog.show(this, "Please wait...", "Loading...", false, false);

        String url = ConfigPlayerID.DATA_URL + player_name;

        StringRequest stringRequest2 = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loadingGetPlayerId.dismiss();
                showJSONgetPlayerId(response);

                //Get level and magic level to later check advances
                checkLvlAdvance = player_level;
                checkMlvlAdvance = player_magic_level;

                //Set health and mana bars
                resetBars();
                progressBarMonster.setProgress(100);
                progressBarMonster.setMax(100);
                progressBarMonster.setProgress(100);

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(WalkaActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest2);


    }

    private void showJSONgetPlayerId(String json) {

        String  playerHpTemp = "", playerMp = "", playerGold = "", playerAttack = "",
                playerDefence = "", playerCritical = "", playerCriticalChance = "", playerSkillPoints = "",
                playerHealthPotions = "", playerManaTempPotions = "", playerLevel = "",
                playerMagicLevel = "", playerExperience = "", playerMagicExperience = "";
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(ConfigPlayerID.JSON_ARRAY);//name of class
            JSONObject collegeData = result.getJSONObject(0);
            playerHpTemp = collegeData.getString(ConfigPlayerID.KEY_HP);
            playerMp = collegeData.getString(ConfigPlayerID.KEY_MP);
            playerLevel = collegeData.getString(ConfigPlayerID.KEY_LEVEL);
            playerMagicLevel = collegeData.getString(ConfigPlayerID.KEY_MAGIC_LEVEL);
            playerExperience = collegeData.getString(ConfigPlayerID.KEY_EXPERIENCE);
            playerGold = collegeData.getString(ConfigPlayerID.KEY_GOLD);
            playerAttack = collegeData.getString(ConfigPlayerID.KEY_ATTACK);
            playerDefence = collegeData.getString(ConfigPlayerID.KEY_DEFENCE);
            playerCritical = collegeData.getString(ConfigPlayerID.KEY_CRITICAL);
            playerCriticalChance = collegeData.getString(ConfigPlayerID.KEY_CRITICAL_CHANCE);
            playerSkillPoints = collegeData.getString(ConfigPlayerID.KEY_SKILL_POINTS);
            playerHealthPotions = collegeData.getString(ConfigPlayerID.KEY_HEALTH_POTIONS);
            playerManaTempPotions = collegeData.getString(ConfigPlayerID.KEY_MANA_POTIONS);
            playerMagicExperience = collegeData.getString(ConfigPlayerID.KEY_MAGIC_EXPERIENCE);



        } catch (JSONException e) {
            e.printStackTrace();
        }


        player_hp = Integer.parseInt(playerHpTemp);
        player_mp = Integer.parseInt(playerMp);
        player_level = Integer.parseInt(playerLevel);
        player_magic_level = Integer.parseInt(playerMagicLevel);
        player_gold = Integer.parseInt(playerGold);
        player_experience = Integer.parseInt(playerExperience);
        player_attack = Integer.parseInt(playerAttack);
        player_defence = Integer.parseInt(playerDefence);
        player_critical = Integer.parseInt(playerCritical);
        player_critical_chance = Integer.parseInt(playerCriticalChance);
        player_skill_points = Integer.parseInt(playerSkillPoints);
        player_health_potions = Integer.parseInt(playerHealthPotions);
        player_mana_potions = Integer.parseInt(playerManaTempPotions);
        player_magic_experience = Integer.parseInt(playerMagicExperience);

        monster();
    }

    public void saveSkills(){

        StringRequest request = new StringRequest(Request.Method.POST, URL_SAVE_SKILLS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                System.out.println(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<>();
                parameters.put("id_player", id_player);
                parameters.put("hp", Integer.toString(player_hp));
                parameters.put("mp", Integer.toString(player_mp));
                parameters.put("level", Integer.toString(player_level));
                parameters.put("magic_level", Integer.toString(player_magic_level));
                parameters.put("attack", Integer.toString(player_attack));
                parameters.put("defence", Integer.toString(player_defence));
                parameters.put("gold", Integer.toString(player_gold));
                parameters.put("skill_points", Integer.toString(player_skill_points));
                parameters.put("health_potions", Integer.toString(player_health_potions));
                parameters.put("mana_potions", Integer.toString(player_mana_potions));
                parameters.put("experience", Integer.toString(player_experience));
                parameters.put("magic_experience", Integer.toString(player_magic_experience));

                return parameters;
            }
        };
        requestQueue.add(request);

    }

    public void checkIfItemExist(final int id_item){

        String url2 = URL_CHECK_ITEM_EXIST + id_player + "&id_item=" + id_item;

        StringRequest stringRequest2 = new StringRequest(url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String json) {

                showJSONcheck(json);
                if(check_id_player.equals("notExist")){
                    int quantity = 1;
                    saveLoot(id_player, id_item, quantity);
                }else{
                    int quantity = check_quantity + 1;
                    saveSameLoot(id_player, id_item, quantity);
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(WalkaActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue2 = Volley.newRequestQueue(this);
        requestQueue2.add(stringRequest2);
    }

    private void showJSONcheck(String json) {

        String  playerId = "";
        Integer quantity2 = 0;

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(ConfigIfExist.JSON_ARRAY);//name of class
            JSONObject collegeData = result.getJSONObject(0);
            playerId = collegeData.getString(ConfigIfExist.KEY_ID_PLAYER);
            quantity2 = collegeData.getInt(ConfigIfExist.KEY_QUANTITY);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        check_id_player = playerId;
        check_quantity =quantity2;

    }

}




