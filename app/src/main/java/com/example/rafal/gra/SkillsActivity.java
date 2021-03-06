package com.example.rafal.gra;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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

import java.util.HashMap;
import java.util.Map;

public class SkillsActivity extends AppCompatActivity {

    int player_hp, player_mp, player_gold, player_attack, player_defence, player_critical,
            player_critical_chance, player_skill_points, used_hp_sp, used_mp_sp, used_attack_sp,
            used_defence_sp, used_critical_sp, used_critical_chance_sp;

    String id_player, player_name;

    private ProgressDialog loadingGetPlayerId;

    public static final String URL = "https://mundial2018.000webhostapp.com/saveSkills.php";

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skills);

            requestQueue = Volley.newRequestQueue(getApplicationContext());

        //Display skills
        loadGame();
        getPlayerId();

    }

    //Reset skill points
    public void resetSp(View view) {

        if (player_gold >= 1000) {

            AlertDialog.Builder reset = new AlertDialog.Builder(this);
            reset.setMessage("Are you sure you want to reset your Skill Points for 1000 gold?")
                    .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            player_gold -= 1000;
                            player_skill_points += (used_hp_sp+used_mp_sp+used_attack_sp+used_defence_sp+
                                    used_critical_sp+used_critical_chance_sp);
                            resetHp();
                            resetMana();
                            resetAtk();
                            resetDef();
                            resetCrit();
                            resetCritChance();
                            refresh();
                        }
                    })
                    .setPositiveButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create();
            reset.show();

        } else {

            AlertDialog.Builder noMoney = new AlertDialog.Builder(this);
            noMoney.setMessage("You need 1000 gold to reset Skill Points.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create();
            noMoney.show();

        }

    }

    public void back(View view) {

        saveSkills();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();

    }

    public void loadGame() {

        SharedPreferences loadE = getSharedPreferences("Save", MODE_PRIVATE);

        id_player = loadE.getString("id_player", "");
        player_name = loadE.getString("player_name", "");

    }

    //Refresh skills after reseting or adding
    public void refresh() {

        Button Hp = (Button) findViewById(R.id.addHp);
        Button Mana = (Button) findViewById(R.id.addMana);
        Button Atk = (Button) findViewById(R.id.addAtk);
        Button Def = (Button) findViewById(R.id.addDef);
        Button Crit = (Button) findViewById(R.id.addCrit);
        Button critChance = (Button) findViewById(R.id.addCritChance);

        if (player_skill_points == 0) {

            Hp.setClickable(false);
            Hp.setEnabled(false);
            Mana.setClickable(false);
            Mana.setEnabled(false);
            Atk.setClickable(false);
            Atk.setEnabled(false);
            Def.setClickable(false);
            Def.setEnabled(false);
            Crit.setClickable(false);
            Crit.setEnabled(false);
            critChance.setClickable(false);
            critChance.setEnabled(false);

        } else {

            Hp.setClickable(true);
            Hp.setEnabled(true);
            Mana.setClickable(true);
            Mana.setEnabled(true);
            Atk.setClickable(true);
            Atk.setEnabled(true);
            Def.setClickable(true);
            Def.setEnabled(true);
            Crit.setClickable(true);
            Crit.setEnabled(true);
            critChance.setClickable(true);
            critChance.setEnabled(true);
        }

        showSkills();

    }

    public void addHp(View view) {

        player_hp += 10;
        player_skill_points -= 1;
        used_hp_sp += 1;

        TextView addHp = (TextView) findViewById(R.id.textViewHp);
        StringBuilder hp = new StringBuilder("");
        hp.append("Health Points" + "\n");
        hp.append("Current: " + Integer.toString(player_hp) + "\n");
        hp.append("Next: " + Integer.toString((player_hp + 10)) + "\n");
        hp.append("Used SP: " + Integer.toString(used_hp_sp));
        addHp.setText(hp);
        refresh();

    }

    public void addMana(View view) {

        player_mp += 10;
        player_skill_points -= 1;
        used_mp_sp += 1;

        TextView addMana = (TextView) findViewById(R.id.textViewManaPoints);
        StringBuilder mana = new StringBuilder("");
        mana.append("Mana Points \n");
        mana.append("Current: " + Integer.toString(player_mp) + "\n");
        mana.append("Next: " + Integer.toString((player_mp + 10)) + "\n");
        mana.append("Used SP: " + Integer.toString(used_mp_sp));
        addMana.setText(mana);
        refresh();

    }

    public void addAtk(View view) {

        player_attack += 1;
        player_skill_points -= 1;
        used_attack_sp += 1;

        TextView addAtk = (TextView) findViewById(R.id.textViewAtk);
        StringBuilder atk = new StringBuilder("");
        atk.append("Attack \n");
        atk.append("Current: " + Integer.toString(player_attack) + "\n");
        atk.append("Next: " + Integer.toString((player_attack + 1)) + "\n");
        atk.append("Used SP: " + Integer.toString(used_attack_sp));
        addAtk.setText(atk);
        refresh();

    }

    public void addDef(View view) {

        player_defence += 1;
        player_skill_points -= 1;
        used_defence_sp += 1;

        TextView addDef = (TextView) findViewById(R.id.textViewDef);
        StringBuilder def = new StringBuilder("");
        def.append("Defence \n");
        def.append("Current: " + Integer.toString(player_defence) + "\n");
        def.append("Next: " + Integer.toString((player_defence + 1)) + "\n");
        def.append("Used SP: " + Integer.toString(used_defence_sp));
        addDef.setText(def);
        refresh();

    }

    public void addCrit(View view) {

        player_critical += 1;
        player_skill_points -= 1;
        used_critical_sp += 1;

        TextView addCrit = (TextView) findViewById(R.id.textViewCrit);
        StringBuilder crit = new StringBuilder("");
        crit.append("Crit. hit % \n");
        crit.append("Current: " + Integer.toString(player_critical) + "\n");
        crit.append("Next: " + Integer.toString((player_critical + 1)) + "\n");
        crit.append("Used SP: " + Integer.toString(used_critical_sp));
        addCrit.setText(crit);
        refresh();

    }

    public void addCritChance(View view) {

        player_critical_chance += 1;
        player_skill_points -= 1;
        used_critical_chance_sp += 1;

        TextView addCritChance = (TextView) findViewById(R.id.textViewCritChance);
        StringBuilder critChance = new StringBuilder("");
        critChance.append("Crit. chance % \n");
        critChance.append("Current: " + Integer.toString(player_critical_chance) + "\n");
        critChance.append("Next: " + Integer.toString((player_critical_chance + 1)) + "\n");
        critChance.append("Used SP: " + Integer.toString(used_critical_chance_sp));
        addCritChance.setText(critChance);
        refresh();

    }

    public void resetHp() {

        player_hp -= used_hp_sp * 10;
        used_hp_sp = 0;

        TextView addHp = (TextView) findViewById(R.id.textViewHp);
        StringBuilder hp = new StringBuilder("");
        hp.append("Health Points \n");
        hp.append("Current: " + Integer.toString(player_hp) + "\n");
        hp.append("Next: " + Integer.toString((player_hp + 10)) + "\n");
        hp.append("Used SP: " + Integer.toString(used_hp_sp));
        addHp.setText(hp);
        refresh();

    }

    public void resetMana() {

        player_mp -= used_mp_sp * 10;
        used_mp_sp = 0;

        TextView addMana = (TextView) findViewById(R.id.textViewManaPoints);
        StringBuilder mana = new StringBuilder("");
        mana.append("Mana Points \n");
        mana.append("Current: " + Integer.toString(player_mp) + "\n");
        mana.append("Next: " + Integer.toString((player_mp + 10)) + "\n");
        mana.append("Used SP: " + Integer.toString(used_mp_sp));
        addMana.setText(mana);
        refresh();

    }

    public void resetAtk() {

        player_attack -= used_attack_sp;
        used_attack_sp = 0;

        TextView addAtk = (TextView) findViewById(R.id.textViewAtk);
        StringBuilder atk = new StringBuilder("");
        atk.append("Attack \n");
        atk.append("Current: " + Integer.toString(player_attack) + "\n");
        atk.append("Next: " + Integer.toString((player_attack + 1)) + "\n");
        atk.append("Used SP: " + Integer.toString(used_attack_sp));
        addAtk.setText(atk);
        refresh();

    }

    public void resetDef() {

        player_defence -= used_defence_sp;
        used_defence_sp = 0;

        TextView addDef = (TextView) findViewById(R.id.textViewDef);
        StringBuilder def = new StringBuilder("");
        def.append("Defence \n");
        def.append("Current: " + Integer.toString(player_defence) + "\n");
        def.append("Next: " + Integer.toString((player_defence + 1)) + "\n");
        def.append("Used SP: " + Integer.toString(used_defence_sp));
        addDef.setText(def);
        refresh();

    }

    public void resetCrit() {

        player_critical -= used_critical_sp;
        used_critical_sp = 0;

        TextView addCrit = (TextView) findViewById(R.id.textViewCrit);
        StringBuilder crit = new StringBuilder("");
        crit.append("Crit. hit % \n");
        crit.append("Current: " + Integer.toString(player_critical) + "\n");
        crit.append("Next: " + Integer.toString((player_critical + 1)) + "\n");
        crit.append("Used SP: " + Integer.toString(used_critical_sp));
        addCrit.setText(crit);
        refresh();

    }

    public void resetCritChance() {

        player_critical_chance -= used_critical_chance_sp;
        used_critical_chance_sp = 0;

        TextView addCritChance = (TextView) findViewById(R.id.textViewCritChance);
        StringBuilder critChance = new StringBuilder("");
        critChance.append("Crit. chance % \n");
        critChance.append("Current: " + Integer.toString(player_critical_chance) + "\n");
        critChance.append("Next: " + Integer.toString((player_critical_chance + 1)) + "\n");
        critChance.append("Used SP: " + Integer.toString(used_critical_chance_sp));
        addCritChance.setText(critChance);
        refresh();

    }

    public void showSkills() {

        TextView textViewSP = (TextView) findViewById(R.id.textViewSP);
        textViewSP.setText("Skill points: " + Integer.toString(player_skill_points));

        TextView addHp = (TextView) findViewById(R.id.textViewHp);
        StringBuilder hp = new StringBuilder("");
        hp.append("Current: " + Integer.toString(player_hp) + "\n");
        hp.append("Next: " + Integer.toString((player_hp + 10)) + "\n");
        hp.append("Used SP: " + Integer.toString(used_hp_sp));
        addHp.setText(hp);

        TextView addMana = (TextView) findViewById(R.id.textViewManaPoints);
        StringBuilder mana = new StringBuilder("");
        mana.append("Current: " + Integer.toString(player_mp) + "\n");
        mana.append("Next: " + Integer.toString((player_mp + 10)) + "\n");
        mana.append("Used SP: " + Integer.toString(used_mp_sp));
        addMana.setText(mana);

        TextView addAtk = (TextView) findViewById(R.id.textViewAtk);
        StringBuilder atk = new StringBuilder("");
        atk.append("Current: " + Integer.toString(player_attack) + "\n");
        atk.append("Next: " + Integer.toString((player_attack + 1)) + "\n");
        atk.append("Used SP: " + Integer.toString(used_attack_sp));
        addAtk.setText(atk);

        TextView addDef = (TextView) findViewById(R.id.textViewDef);
        StringBuilder def = new StringBuilder("");
        def.append("Current: " + Integer.toString(player_defence) + "\n");
        def.append("Next: " + Integer.toString((player_defence + 1)) + "\n");
        def.append("Used SP: " + Integer.toString(used_defence_sp));
        addDef.setText(def);

        TextView addCrit = (TextView) findViewById(R.id.textViewCrit);
        StringBuilder crit = new StringBuilder("");
        crit.append("Current: " + Integer.toString(player_critical) + "\n");
        crit.append("Next: " + Integer.toString((player_critical + 1)) + "\n");
        crit.append("Used SP: " + Integer.toString(used_critical_sp));
        addCrit.setText(crit);

        TextView addCritChance = (TextView) findViewById(R.id.textViewCritChance);
        StringBuilder critChance = new StringBuilder("");
        critChance.append("Current: " + Integer.toString(player_critical_chance) + "\n");
        critChance.append("Next: " + Integer.toString((player_critical_chance + 1)) + "\n");
        critChance.append("Used SP: " + Integer.toString(used_critical_chance_sp));
        addCritChance.setText(critChance);

    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder exit = new AlertDialog.Builder(this);
        exit.setMessage("Are you sure you want to exit the application?")
                .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        saveSkills();
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

    public void getPlayerId() {

        loadingGetPlayerId = ProgressDialog.show(this, "Please wait...", "Loading...", false, false);

        String url = ConfigPlayerID.DATA_URL+player_name;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loadingGetPlayerId.dismiss();
                showJSONgetPlayerId(response);

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SkillsActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void showJSONgetPlayerId(String json) {

        String  playerHp = "", playerMp = "", playerGold = "", playerAttack = "",
                playerDefence = "", playerCritical = "", playerCriticalChance = "", playerSkillPoints = "",
                playerUsedHp = "", playerUsedMp = "", playerUsedAttack = "", playerUsedDefence = "",
                playerUsedCritical = "", playerUsedCriticalChance = "";

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(ConfigPlayerID.JSON_ARRAY);//name of class
            JSONObject collegeData = result.getJSONObject(0);
            playerHp = collegeData.getString(ConfigPlayerID.KEY_HP);
            playerMp = collegeData.getString(ConfigPlayerID.KEY_MP);
            playerGold = collegeData.getString(ConfigPlayerID.KEY_GOLD);
            playerAttack = collegeData.getString(ConfigPlayerID.KEY_ATTACK);
            playerDefence = collegeData.getString(ConfigPlayerID.KEY_DEFENCE);
            playerCritical = collegeData.getString(ConfigPlayerID.KEY_CRITICAL);
            playerCriticalChance = collegeData.getString(ConfigPlayerID.KEY_CRITICAL_CHANCE);
            playerSkillPoints = collegeData.getString(ConfigPlayerID.KEY_SKILL_POINTS);
            playerUsedHp = collegeData.getString(ConfigPlayerID.KEY_USED_HP_SP);
            playerUsedMp = collegeData.getString(ConfigPlayerID.KEY_USED_MP_SP);
            playerUsedAttack = collegeData.getString(ConfigPlayerID.KEY_USED_ATTACK_SP);
            playerUsedDefence = collegeData.getString(ConfigPlayerID.KEY_USED_DEFENCE_SP);
            playerUsedCritical = collegeData.getString(ConfigPlayerID.KEY_USED_CRITICAL_SP);
            playerUsedCriticalChance = collegeData.getString(ConfigPlayerID.KEY_USED_CRITICAL_CHANCE_SP);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        player_hp = Integer.parseInt(playerHp);
        player_mp = Integer.parseInt(playerMp);
        player_gold = Integer.parseInt(playerGold);
        player_attack = Integer.parseInt(playerAttack);
        player_defence = Integer.parseInt(playerDefence);
        player_critical = Integer.parseInt(playerCritical);
        player_critical_chance = Integer.parseInt(playerCriticalChance);
        player_skill_points = Integer.parseInt(playerSkillPoints);
        used_hp_sp = Integer.parseInt(playerUsedHp);
        used_mp_sp = Integer.parseInt(playerUsedMp);
        used_attack_sp = Integer.parseInt(playerUsedAttack);
        used_defence_sp = Integer.parseInt(playerUsedDefence);
        used_critical_sp = Integer.parseInt(playerUsedCritical);
        used_critical_chance_sp = Integer.parseInt(playerUsedCriticalChance);

        showSkills();
        refresh();

    }

    public void saveSkills(){

        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
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
                parameters.put("attack", Integer.toString(player_attack));
                parameters.put("defence", Integer.toString(player_defence));
                parameters.put("critical", Integer.toString(player_critical));
                parameters.put("critical_chance", Integer.toString(player_critical_chance));
                parameters.put("gold", Integer.toString(player_gold));
                parameters.put("skill_points", Integer.toString(player_skill_points));
                parameters.put("used_hp_sp", Integer.toString(used_hp_sp));
                parameters.put("used_mp_sp", Integer.toString(used_mp_sp));
                parameters.put("used_attack_sp", Integer.toString(used_attack_sp));
                parameters.put("used_defence_sp", Integer.toString(used_defence_sp));
                parameters.put("used_critical_sp", Integer.toString(used_critical_sp));
                parameters.put("used_critical_chance_sp", Integer.toString(used_critical_chance_sp));

                return parameters;
            }
        };

        requestQueue.add(request);

    }

}
