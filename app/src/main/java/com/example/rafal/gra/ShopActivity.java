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

public class ShopActivity extends AppCompatActivity {

    int player_gold, player_health_potions, player_mana_potions, toPay, quantityHP, quantityMP;

    private ProgressDialog loadingGetPlayerId;

    RequestQueue requestQueue;

    String id_player, player_name;

    public static final String URL = "https://mundial2018.000webhostapp.com/saveShop.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        loadGame();
        getPlayerId();

        check();

    }

    public void loadGame() {

        SharedPreferences loadE = getSharedPreferences("Save", MODE_PRIVATE);

        id_player = loadE.getString("id_player", "");
        player_name = loadE.getString("player_name", "");

    }

    public void back(View view) {

        saveShop();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();

    }

    public void leftHealthPotion(View view) {

        TextView textViewQuantityHP = (TextView) findViewById(R.id.textViewQuantityHP);
        quantityHP = Integer.parseInt(textViewQuantityHP.getText().toString());

        if (quantityHP > 0 && quantityHP < 101) {

            textViewQuantityHP.setText(Integer.toString(quantityHP - 1));

        }

        check();

    }

    public void rightHealthPotion(View view) {

        TextView textViewQuantityHP = (TextView) findViewById(R.id.textViewQuantityHP);
        quantityHP = Integer.parseInt(textViewQuantityHP.getText().toString());

        if (quantityHP >= 0 && quantityHP < 100) {
            textViewQuantityHP.setText(Integer.toString(quantityHP + 1));
        }

        check();

    }

    public void leftManaPotion(View view) {

        TextView textViewQuantityMP = (TextView) findViewById(R.id.textViewQuantityMP);
        quantityMP = Integer.parseInt(textViewQuantityMP.getText().toString());

        if (quantityMP > 0 && quantityMP < 101) {
            textViewQuantityMP.setText(Integer.toString(quantityMP - 1));
        }

        check();

    }

    public void rightManaPotion(View view) {

        TextView textViewQuantityMP = (TextView) findViewById(R.id.textViewQuantityMP);
        quantityMP = Integer.parseInt(textViewQuantityMP.getText().toString());

        if (quantityMP >= 0 && quantityMP < 100) {
            textViewQuantityMP.setText(Integer.toString(quantityMP + 1));
        }

        check();

    }

    public void check() {

        Button leftHP = (Button) findViewById(R.id.buttonLeftHP);
        Button rightHP = (Button) findViewById(R.id.buttonRightHP);
        Button leftMP = (Button) findViewById(R.id.buttonLeftMP);
        Button rightMP = (Button) findViewById(R.id.buttonRightMP);
        Button buy = (Button) findViewById(R.id.buttonBuy);

        TextView textViewQuantityHP = (TextView) findViewById(R.id.textViewQuantityHP);
        quantityHP = Integer.parseInt(textViewQuantityHP.getText().toString());

        TextView textViewQuantityMP = (TextView) findViewById(R.id.textViewQuantityMP);
        quantityMP = Integer.parseInt(textViewQuantityMP.getText().toString());

        if (quantityHP > 0 || quantityMP > 0) {
            buy.setClickable(true);
            buy.setEnabled(true);
        } else {
            buy.setClickable(false);
            buy.setEnabled(false);
        }

        if (quantityHP == 0) {
            leftHP.setClickable(false);
            leftHP.setEnabled(false);
        } else {
            leftHP.setClickable(true);
            leftHP.setEnabled(true);
        }

        if (quantityHP == 99) {
            rightHP.setClickable(false);
            rightHP.setEnabled(false);
        } else {
            rightHP.setClickable(true);
            rightHP.setEnabled(true);
        }

        if (quantityMP == 0) {
            leftMP.setClickable(false);
            leftMP.setEnabled(false);
        } else {
            leftMP.setClickable(true);
            leftMP.setEnabled(true);
        }

        if (quantityMP == 99) {
            rightMP.setClickable(false);
            rightMP.setEnabled(false);
        } else {
            rightMP.setClickable(true);
            rightMP.setEnabled(true);
        }

        toPay();

    }

    public void refresh() {

        TextView textViewGold = (TextView) findViewById(R.id.textViewGold);
        TextView textViewQuantityHP = (TextView) findViewById(R.id.textViewQuantityHP);
        TextView textViewQuantityMP = (TextView) findViewById(R.id.textViewQuantityMP);

        textViewGold.setText("Gold: " + Integer.toString(player_gold));
        textViewQuantityHP.setText("0");
        textViewQuantityMP.setText("0");

    }

    public void toPay() {

        TextView textViewHP = (TextView) findViewById(R.id.textViewHP);
        int hpPrice = Integer.parseInt(textViewHP.getText().toString());

        TextView textViewMP = (TextView) findViewById(R.id.textViewMP);
        int mpPrice = Integer.parseInt(textViewMP.getText().toString());

        TextView textViewQuantityHP = (TextView) findViewById(R.id.textViewQuantityHP);
        quantityHP = Integer.parseInt(textViewQuantityHP.getText().toString());

        TextView textViewQuantityMP = (TextView) findViewById(R.id.textViewQuantityMP);
        quantityMP = Integer.parseInt(textViewQuantityMP.getText().toString());

        TextView textViewFinalPrice = (TextView) findViewById(R.id.textViewFinalPrice);
        toPay = (hpPrice * quantityHP) + (mpPrice * quantityMP);
        textViewFinalPrice.setText("To pay: " + Integer.toString(toPay));

    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder exit = new AlertDialog.Builder(this);
        exit.setMessage("Are you sure you want to exit the application?")
                .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        saveShop();
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

    public void buy(View view) {

        AlertDialog.Builder reset = new AlertDialog.Builder(this);
        AlertDialog.Builder enough = new AlertDialog.Builder(this);

        if (player_gold < toPay){

            enough.setMessage("You do not have enough money.")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create();
            enough.show();

        }else if (quantityHP > 0 && quantityMP == 0) {
            reset.setMessage("Are you sure you want to buy " + quantityHP + " health potion(s) for " + toPay + " gold?")

                    .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            player_gold -= toPay;
                            player_health_potions += quantityHP;
                            //zapis();
                            refresh();
                            check();
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
        } else if (quantityMP > 0 && quantityHP == 0) {

            reset.setMessage("Are you sure you want to buy " + quantityMP + " mana potion(s) for " + toPay + " gold?")

                    .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            player_gold -= toPay;
                            player_mana_potions += quantityMP;
                            refresh();
                            check();
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

        } else if (quantityHP > 0 && quantityMP > 0) {

            reset.setMessage("Are you sure you want to buy " + quantityHP + " health potion(s) and " + quantityMP +
                    " mana potion(s) for " + toPay + " gold?")

                    .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            player_gold -= toPay;
                            player_health_potions += quantityHP;
                            player_mana_potions += quantityMP;
                            refresh();
                            check();
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


        }
    }

    public void getPlayerId() {

        loadingGetPlayerId = ProgressDialog.show(this, "Please wait...", "Loading...", false, false);

        String url = ConfigPlayerID.DATA_URL+player_name; //URL taken from Config.java and added number after it

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loadingGetPlayerId.dismiss();
                showJSONgetPlayerId(response);
                TextView textViewGold = (TextView) findViewById(R.id.textViewGold);
                textViewGold.setText("Gold: " + Integer.toString(player_gold));

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ShopActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }

    private void showJSONgetPlayerId(String json) {

        String  playerGold = "", playerHealthPotions = "", playerManaPotions = "";
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(ConfigPlayerID.JSON_ARRAY);//name of class
            JSONObject collegeData = result.getJSONObject(0);
            playerGold = collegeData.getString(ConfigPlayerID.KEY_GOLD);
            playerHealthPotions = collegeData.getString(ConfigPlayerID.KEY_HEALTH_POTIONS);
            playerManaPotions = collegeData.getString(ConfigPlayerID.KEY_MANA_POTIONS);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        player_gold = Integer.parseInt(playerGold);
        player_health_potions = Integer.parseInt(playerHealthPotions);
        player_mana_potions = Integer.parseInt(playerManaPotions);


    }

    public void saveShop(){

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
                parameters.put("gold", Integer.toString(player_gold));
                parameters.put("health_potions", Integer.toString(player_health_potions));
                parameters.put("mana_potions", Integer.toString(player_mana_potions));

                return parameters;
            }
        };

        requestQueue.add(request);

    }

}
