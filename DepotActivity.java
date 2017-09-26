package com.example.rafal.gra;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
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

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DepotActivity extends AppCompatActivity{

    private ProgressDialog loadingGetAll, loadingShowType, loadingGetPlayerId;

    public static String[] imagesDepot, typesDepot, descriptionsDepot, quantityDepot, weightDepot,
            namesDepot, pricesDepot, imagesDepotFew, descriptionsDepotFew, typesDepotFew, quantityDepotFew,
            weightDepotFew, namesDepotFew, pricesDepotFew, idsDepot, idsDepotFew;

    String id_player, type, player_name, id_item;

    int imageID, quant, player_gold, imageNumber;

    boolean check = true, longClick = false;

    public static final String DATA_URL = "https://mundial2018.000webhostapp.com/getDepotByType.php?id=";
    public static final String URL_SAVE_SKILLS = "https://mundial2018.000webhostapp.com/saveGold.php";
    public static final String URL_DELETE_ITEM = "https://mundial2018.000webhostapp.com/deleteSoldItem.php?id_player=";
    public static final String URL_UPDATE_QUANTITY = "https://mundial2018.000webhostapp.com/updateQuantity.php";

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depot);

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        final GridView grid = (GridView) findViewById(R.id.depo);
        grid.setAdapter(new ImageAdapter(this));

        loadGame();

        getPlayerId();

        getAll(id_player);


        grid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, final int position, long arg3) {

                if(!ConfigDepot.images[0].equals("null") && ((ImageAdapter) grid.getAdapter()).checkImage(position)!=R.mipmap.empty) {
                    final Dialog dialog2 = new Dialog(DepotActivity.this);
                    dialog2.setTitle("Sell");
                    dialog2.setContentView(R.layout.popup_sell_from_depot);
                    dialog2.show();
                    dialog2.setCancelable(false);
                    dialog2.setCanceledOnTouchOutside(false);

                    longClick = true;

                    Button sell = (Button) dialog2.findViewById(R.id.sell);
                    Button back = (Button) dialog2.findViewById(R.id.back);

                    sell.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (quantityDepotFew[position].equals("1")) {

                                AlertDialog.Builder exit2 = new AlertDialog.Builder(DepotActivity.this);
                                exit2.setMessage("Are you sure you want to sell " + namesDepotFew[position].toLowerCase() +
                                        " for " + pricesDepotFew[position] + " gold?")
                                        .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                player_gold += Integer.parseInt(pricesDepotFew[position]);
                                                id_item = idsDepotFew[position];
                                                saveGold();
                                                deleteSoldItem(id_player, id_item);
                                                getAll(id_player);
                                                dialog.dismiss();
                                                dialog2.dismiss();
                                                longClick = false;
                                            }
                                        })
                                        .setPositiveButton("No", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                                dialog2.dismiss();
                                                longClick = false;
                                            }
                                        })
                                        .create();
                                exit2.show();
                            } else {
                                final Dialog dialog3 = new Dialog(DepotActivity.this);
                                dialog3.setTitle("Sell");
                                dialog3.setContentView(R.layout.popup_sell_from_depot_few);
                                dialog3.show();
                                dialog3.setCancelable(false);
                                dialog3.setCanceledOnTouchOutside(false);
                                dialog2.dismiss();

                                final TextView quantityText = (TextView) dialog3.findViewById(R.id.textViewQuantity);
                                quantityText.setText(quantityDepotFew[position]);
                                Button left = (Button) dialog3.findViewById(R.id.quantityLeft);
                                Button right = (Button) dialog3.findViewById(R.id.quantityRight);
                                Button sellFew = (Button) dialog3.findViewById(R.id.buttonSellFew);
                                Button backFew = (Button) dialog3.findViewById(R.id.buttonBackFew);

                                left.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        int quantitySell = Integer.parseInt(quantityText.getText().toString());

                                        if (quantitySell > 1 && quantitySell < Integer.parseInt(quantityDepotFew[position] + 1)) {
                                            quantityText.setText(Integer.toString(quantitySell - 1));
                                        }

                                    }
                                });

                                right.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        int quantitySell = Integer.parseInt(quantityText.getText().toString());

                                        if (quantitySell >= 1 && quantitySell < Integer.parseInt(quantityDepotFew[position])) {
                                            quantityText.setText(Integer.toString(quantitySell + 1));
                                        }

                                    }
                                });

                                left.setOnLongClickListener(new View.OnLongClickListener() {
                                    @Override
                                    public boolean onLongClick(View v) {

                                        quantityText.setText("1");
                                        return true;
                                    }
                                });

                                right.setOnLongClickListener(new View.OnLongClickListener() {
                                    @Override
                                    public boolean onLongClick(View v) {

                                        quantityText.setText(quantityDepotFew[position]);
                                        return true;
                                    }
                                });

                                sellFew.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        final String quantityToSell = quantityText.getText().toString();
                                        AlertDialog.Builder exit3 = new AlertDialog.Builder(DepotActivity.this);

                                        exit3.setMessage("Are you sure you want to sell " + quantityToSell + "x "
                                                + namesDepotFew[position].toLowerCase() +
                                                " for " + (Integer.parseInt(pricesDepotFew[position]) * Integer.parseInt(quantityToSell)) + " gold?")
                                                .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        player_gold += (Integer.parseInt(pricesDepotFew[position]) * Integer.parseInt(quantityToSell));
                                                        id_item = idsDepotFew[position];
                                                        saveGold();
                                                        if (quantityDepotFew[position].equals(quantityToSell)) {
                                                            deleteSoldItem(id_player, id_item);
                                                            getAll(id_player);
                                                            loadingGetAll.dismiss();
                                                        } else {
                                                            int newQuantity = (Integer.parseInt(quantityDepotFew[position]) -
                                                                    Integer.parseInt(quantityToSell));
                                                            updateQuantity(id_player, id_item, newQuantity);
                                                            getAll(id_player);
                                                            loadingGetAll.dismiss();
                                                        }
                                                        dialog.dismiss();
                                                        dialog2.dismiss();
                                                        dialog3.dismiss();
                                                        getAll(id_player);
                                                        longClick = false;
                                                    }
                                                })
                                                .setPositiveButton("No", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                        longClick = false;
                                                    }
                                                })
                                                .create();
                                        exit3.show();

                                    }
                                });

                                backFew.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog3.dismiss();
                                        longClick = false;
                                    }
                                });

                            }
                        }
                    });

                    back.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog2.dismiss();
                            longClick = false;
                        }
                    });
                }

                return false;
            }

        });

        //Set format for double numbers
        final NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
        nf.setMinimumFractionDigits(2);

        //Text to display after tapping item in depot
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (longClick == false && ConfigDepot.ids != null) {
                    if (imagesDepotFew[position] != null) {
                        if (Integer.parseInt(quantityDepotFew[position]) > 1 && !imagesDepotFew[position].equals("fish_img")
                        && (typesDepotFew[position].equals("other") || typesDepotFew[position].equals("creature_product"))){

                            if (imagesDepotFew[position] != null) {
                                Toast.makeText(getBaseContext(),
                                        "You see " + quantityDepotFew[position] + " " + namesDepotFew[position].toLowerCase() +
                                                "s. They weigh " + nf.format(Double.parseDouble(weightDepotFew[position]) * Double.parseDouble(quantityDepotFew[position])) + " oz.",
                                        Toast.LENGTH_LONG).show();

                            }
                        } else if (imagesDepotFew[position].equals("fish_img") && Integer.parseInt(quantityDepotFew[position]) > 1) {

                            if (imagesDepotFew[position] != null) {
                                Toast.makeText(getBaseContext(),
                                        "You see " + quantityDepotFew[position] + " fish. They weigh " +
                                                nf.format(Double.parseDouble(weightDepotFew[position]) * Double.parseDouble(quantityDepotFew[position])) + " oz.",
                                        Toast.LENGTH_LONG).show();

                            }
                        } else {

                            if (imagesDepotFew[position] != null) {
                                Toast.makeText(getBaseContext(), descriptionsDepotFew[position], Toast.LENGTH_LONG).show();
                            }

                        }
                    }

                }
            }
        });


    }

    //Display all items from character's depot
    public void getAll(final String id_player) {

            loadingGetAll = ProgressDialog.show(this, "Please wait...", "Loading...", false, false);

            String url = ConfigDepot.DATA_URL + id_player;

            StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
                @Override
                public void onResponse(String json) {
                    loadingGetAll.dismiss();
                    showJSON(json);

                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(DepotActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                        }
                    });

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);

    }

    public void showJSON(String json) {

        ConfigDepot pj = new ConfigDepot(json);
        pj.ConfigDepot();

        GridView grid = (GridView) findViewById(R.id.depo);

        if(ConfigDepot.images != null){
            imagesDepot = new String[ConfigDepot.images.length];
            typesDepot = new String[ConfigDepot.types.length];
            descriptionsDepot = new String[ConfigDepot.descriptions.length];
            quantityDepot = new String[ConfigDepot.quantity.length];
            weightDepot = new String[ConfigDepot.weight.length];
            namesDepot = new String[ConfigDepot.names.length];
            pricesDepot = new String[ConfigDepot.prices.length];
            idsDepot = new String[ConfigDepot.ids.length];

            for (int i = 0; i < 40; i++) {
                ((ImageAdapter) grid.getAdapter()).changeImage(i, R.mipmap.empty);
                ((ImageAdapter) grid.getAdapter()).changeText(i, "");
            }

            Arrays.fill(imagesDepot, null);
            Arrays.fill(typesDepot, null);
            Arrays.fill(weightDepot, null);
            Arrays.fill(namesDepot, null);
            Arrays.fill(pricesDepot, null);
            Arrays.fill(quantityDepot, null);
            Arrays.fill(idsDepot, null);

            //Variable for number of all items including repeated items
            quant = 0;

            //Fill arrays with data from database
            for (int i = 0; i < ConfigDepot.images.length; i++) {

                imagesDepot[i] = ConfigDepot.images[i];
                typesDepot[i] = ConfigDepot.types[i];
                descriptionsDepot[i] = ConfigDepot.descriptions[i];
                quantityDepot[i] = ConfigDepot.quantity[i];
                weightDepot[i] = ConfigDepot.weight[i];
                namesDepot[i] = ConfigDepot.names[i];
                pricesDepot[i] = ConfigDepot.prices[i];
                idsDepot[i] = ConfigDepot.prices[i];
                quant += Integer.parseInt(quantityDepot[i]);

            }

            //Create arrays for items including repeated items
            imagesDepotFew = new String[quant];
            descriptionsDepotFew = new String[quant];
            typesDepotFew = new String[quant];
            quantityDepotFew = new String[quant];
            weightDepotFew = new String[quant];
            namesDepotFew = new String[quant];
            pricesDepotFew = new String[quant];
            idsDepotFew = new String[quant];

            //Reset arrays
            Arrays.fill(imagesDepotFew, null);
            Arrays.fill(descriptionsDepotFew, null);
            Arrays.fill(typesDepotFew, null);
            Arrays.fill(quantityDepotFew, null);
            Arrays.fill(weightDepotFew, null);
            Arrays.fill(namesDepotFew, null);
            Arrays.fill(pricesDepotFew, null);
            Arrays.fill(idsDepotFew, null);

            int k = 0;

            //Fill arrays with items
            for (int i = 0; i < typesDepot.length; i++) {

                if (!typesDepot[i].equals("creature_product") && !typesDepot[i].equals("other")) {

                    if (Integer.parseInt(quantityDepot[i]) > 1) {

                        for (int j = 0; j < Integer.parseInt(quantityDepot[i]); j++) {

                            String image = imagesDepot[i];
                            imageID = getResources().getIdentifier(image, "mipmap", "com.example.rafal.gra");
                            ((ImageAdapter) grid.getAdapter()).changeImage(k, imageID);

                            imagesDepotFew[k] = ConfigDepot.images[i];
                            descriptionsDepotFew[k] = ConfigDepot.descriptions[i];
                            typesDepotFew[k] = ConfigDepot.types[i];
                            quantityDepotFew[k] = ConfigDepot.quantity[i];
                            namesDepotFew[k] = ConfigDepot.names[i];
                            weightDepotFew[k] = ConfigDepot.weight[i];
                            pricesDepotFew[k] = ConfigDepot.prices[i];
                            idsDepotFew[k] = ConfigDepot.ids[i];

                            k += 1;
                        }
                        imageNumber = k;
                    } else {

                        String image = imagesDepot[i];
                        imageID = getResources().getIdentifier(image, "mipmap", "com.example.rafal.gra");
                        ((ImageAdapter) grid.getAdapter()).changeImage(k, imageID);

                        imagesDepotFew[k] = ConfigDepot.images[i];
                        descriptionsDepotFew[k] = ConfigDepot.descriptions[i];
                        typesDepotFew[k] = ConfigDepot.types[i];
                        quantityDepotFew[k] = ConfigDepot.quantity[i];
                        namesDepotFew[k] = ConfigDepot.names[i];
                        weightDepotFew[k] = ConfigDepot.weight[i];
                        pricesDepotFew[k] = ConfigDepot.prices[i];
                        idsDepotFew[k] = ConfigDepot.ids[i];

                        k += 1;
                    }
                } else {

                    String image = imagesDepot[i];
                    String text = quantityDepot[i];
                    imageID = getResources().getIdentifier(image, "mipmap", "com.example.rafal.gra");
                    ((ImageAdapter) grid.getAdapter()).changeImage(k, imageID);
                    if (!text.equals("1")) {
                        ((ImageAdapter) grid.getAdapter()).changeText(k, text + "  ");
                    }

                    imagesDepotFew[k] = ConfigDepot.images[i];
                    descriptionsDepotFew[k] = ConfigDepot.descriptions[i];
                    typesDepotFew[k] = ConfigDepot.types[i];
                    quantityDepotFew[k] = ConfigDepot.quantity[i];
                    namesDepotFew[k] = ConfigDepot.names[i];
                    weightDepotFew[k] = ConfigDepot.weight[i];
                    pricesDepotFew[k] = ConfigDepot.prices[i];
                    idsDepotFew[k] = ConfigDepot.ids[i];

                    k += 1;
                }

            }
        }else {


            for (int i = 0; i < 40; i++) {
                ((ImageAdapter) grid.getAdapter()).changeImage(i, R.mipmap.empty);
                ((ImageAdapter) grid.getAdapter()).changeText(i, "");
            }

            if(imagesDepotFew!=null) {
                Arrays.fill(imagesDepotFew, null);
                Arrays.fill(descriptionsDepotFew, null);
                Arrays.fill(typesDepotFew, null);
                Arrays.fill(quantityDepotFew, null);
                Arrays.fill(weightDepotFew, null);
                Arrays.fill(namesDepotFew, null);
                Arrays.fill(pricesDepotFew, null);
                Arrays.fill(idsDepotFew, null);
            }
        }

    }

    public void getPlayerId() {

        loadingGetPlayerId = ProgressDialog.show(this, "Please wait...", "Loading...", false, false);

        String url = ConfigPlayerID.DATA_URL + player_name;

        StringRequest stringRequest2 = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loadingGetPlayerId.dismiss();
                showJSONgetPlayerId(response);

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DepotActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest2);


    }

    private void showJSONgetPlayerId(String json) {

        String  playerGold = "";
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(ConfigPlayerID.JSON_ARRAY);
            JSONObject collegeData = result.getJSONObject(0);
            playerGold = collegeData.getString(ConfigPlayerID.KEY_GOLD);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        player_gold = Integer.parseInt(playerGold);

    }

    public void saveGold(){

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
                parameters.put("gold", Integer.toString(player_gold));

                return parameters;
            }
        };
        requestQueue.add(request);

    }

    public void updateQuantity(final String id_player, final String id_item, final int quantity){

        StringRequest request = new StringRequest(Request.Method.POST, URL_UPDATE_QUANTITY,
                new Response.Listener<String>() {
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
                parameters.put("id_item", id_item);
                parameters.put("quantity", Integer.toString(quantity));

                return parameters;
            }
        };
        requestQueue.add(request);

    }

    public void deleteSoldItem(final String id_player, final String id_item){

        String url = URL_DELETE_ITEM + id_player + "&id_item=" + id_item;
        StringRequest request2 = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                System.out.println(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(request2);

    }

    //Display all weapon type items
    public void showWeapon(View view) {

        final GridView grid = (GridView) findViewById(R.id.depo);
        grid.setAdapter(new ImageAdapter(this));
        grid.scrollTo(0,0);

        if(ConfigDepot.images != null) {
            type = "weapon";
            showType(type);
        }
    }

    //Display all shield type items
    public void showShield(View view) {

        final GridView grid = (GridView) findViewById(R.id.depo);
        grid.setAdapter(new ImageAdapter(this));
        grid.scrollTo(0,0);

        if(ConfigDepot.images != null) {
            type = "shield";
            showType(type);
        }

    }

    //Display all armor type items
    public void showArmor(View view) {

        final GridView grid = (GridView) findViewById(R.id.depo);
        grid.setAdapter(new ImageAdapter(this));
        grid.scrollTo(0,0);

        if(ConfigDepot.images != null) {
            type = "armor";
            showType(type);
        }

    }

    //Display all jewelerry type items
    public void showJewellery(View view) {

        final GridView grid = (GridView) findViewById(R.id.depo);
        grid.setAdapter(new ImageAdapter(this));
        grid.scrollTo(0,0);

        if(ConfigDepot.images != null) {
            type = "jewellery";
            showType(type);
        }

    }

    //Display all craft type items
    public void showCraft(View view) {

        final GridView grid = (GridView) findViewById(R.id.depo);
        grid.setAdapter(new ImageAdapter(this));
        grid.scrollTo(0,0);

        if(ConfigDepot.images != null) {
            type = "creature_product";
            showType(type);
        }

    }

    //Display all other type items
    public void showOther(View view) {

        final GridView grid = (GridView) findViewById(R.id.depo);
        grid.setAdapter(new ImageAdapter(this));
        grid.scrollTo(0,0);

        if(ConfigDepot.images != null) {
            type = "other";
            showType(type);
        }

    }

    //Display all items
    public void showAll(View view) {

        final GridView grid = (GridView) findViewById(R.id.depo);
        grid.setAdapter(new ImageAdapter(this));
        grid.scrollTo(0,0);

        getAll(id_player);

    }

    //Display items by type
    public void showType(String type) {

        GridView grid = (GridView) findViewById(R.id.depo);

        for (int i = 0; i < imagesDepotFew.length; i++) {
            ((ImageAdapter) grid.getAdapter()).changeImage(i, R.mipmap.empty);
        }

        //Reset arrays
        Arrays.fill(imagesDepot, null);
        Arrays.fill(imagesDepotFew, null);
        Arrays.fill(descriptionsDepot, null);
        Arrays.fill(descriptionsDepotFew, null);
        Arrays.fill(typesDepot, null);
        Arrays.fill(typesDepotFew, null);
        Arrays.fill(weightDepotFew, null);
        Arrays.fill(namesDepotFew, null);
        Arrays.fill(quantityDepot, null);
        Arrays.fill(quantityDepotFew, null);
        Arrays.fill(pricesDepot, null);
        Arrays.fill(pricesDepotFew, null);

        loadingShowType = ProgressDialog.show(this, "Please wait...", "Loading...", false, false);

        String url = DATA_URL + id_player + "&type=" + type;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
                @Override
                public void onResponse(String json) {
                    loadingShowType.dismiss();
                    showJSON(json);
                    checkIfTypeExist();

                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(DepotActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                        }
                    });

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);

    }

    //Check if type exist, if not them display empty depot
    public void checkIfTypeExist(){

        if(ConfigDepot.images != null) {
            for (int i = 0; i < ConfigDepot.types.length; i++) {

                String t = ConfigDepot.types[i];
                if (t.equals(type)) {
                    check = false;
                    break;
                }
                check = true;
            }

            if (check == true) {

                GridView grid = (GridView) findViewById(R.id.depo);
                for (int i = 0; i < 40; i++) {
                    ((ImageAdapter) grid.getAdapter()).changeImage(i, R.mipmap.empty);
                    ((ImageAdapter) grid.getAdapter()).changeText(i, "");
                }
                Arrays.fill(imagesDepot, null);
                Arrays.fill(imagesDepotFew, null);
            }
            check = false;
        }
    }

    //Action after tapping Back Key
    @Override
    public void onBackPressed() {
        AlertDialog.Builder exit = new AlertDialog.Builder(this);
        exit.setMessage("Are you sure you want to exit the application?")
                .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        saveGold();
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

    //Go to MainActivity
    public void back(View view) {

        saveGold();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();

    }

    //Load game
    public void loadGame() {

        SharedPreferences loadE = getSharedPreferences("Save", MODE_PRIVATE);
        id_player = loadE.getString("id_player", "");
        player_name = loadE.getString("player_name", "");

    }

}
