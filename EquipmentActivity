package com.example.rafal.gra;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
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
import android.os.Handler;

import static java.sql.Types.NULL;

public class EquipmentActivity extends AppCompatActivity {

    public static String[] imagesDepot, typesDepot, descriptionsDepot, weightDepot, namesDepot,
                            idsDepot, quantityDepot, imagesDepotFew, typesDepotFew, descriptionsDepotFew,
                            weightDepotFew, namesDepotFew, idsDepotFew, quantityDepotFew, subtypes,
                            subtypesFew;

    private ProgressDialog loadingGetPlayerId, loadingGetImages, loadingGetAll;

    public static String[] imagesEquipment, descriptionsEquipment;
    public static int[] idsEquipment;

    int player_health_potions, player_mana_potions, player_eq_helmet, player_eq_armor,
            player_eq_legs, player_eq_boots, player_eq_weapon, player_eq_shield,
            player_eq_ring_left, player_eq_ring_right, player_eq_amulet, quant, imageID;

    int helmet_temp = 0, armor_temp = 0, legs_temp = 0, boots_temp = 0, weapon_temp = 0,
        shield_temp = 0, amulet_temp = 0, ring_left_temp = 0, ring_right_temp = 0;

    String id_player, player_name, descriptionHelmet, descriptionArmor, descriptionLegs,
            descriptionBoots, descriptionWeapon, descriptionShield, descriptionAmulet,
            descriptionRingLeft,descriptionRingRight;

    boolean longClick = false, wait = false;

    String URL_UPDATE_ITEM = "https://mundial2018.000webhostapp.com/saveSameLoot.php";
    String URL_INSERT_ITEM = "https://mundial2018.000webhostapp.com/saveLoot.php";
    public static final String URL_DELETE_ITEM = "https://mundial2018.000webhostapp.com/deleteSoldItem.php?id_player=";
    public static final String URL = "https://mundial2018.000webhostapp.com/saveEquipment.php";

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment);

        ImageView helmet_img = (ImageView) findViewById(R.id.imageViewHelmet);
        ImageView armor_img = (ImageView) findViewById(R.id.imageViewArmor);
        ImageView legs_img = (ImageView) findViewById(R.id.imageViewLegs);
        ImageView boots_img = (ImageView) findViewById(R.id.imageViewBoots);
        ImageView weapon_img = (ImageView) findViewById(R.id.imageViewWeapon);
        ImageView shield_img = (ImageView) findViewById(R.id.imageViewShield);
        ImageView amulet_img = (ImageView) findViewById(R.id.imageViewAmulet);
        ImageView ring_left_img = (ImageView) findViewById(R.id.imageViewRingLeft);
        ImageView ring_right_img = (ImageView) findViewById(R.id.imageViewRingRight);

        loadGame();

        getPlayerId();

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        helmet_img.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {

                if(helmet_temp != 0) {

                    final Dialog dialog4 = new Dialog(EquipmentActivity.this);
                    dialog4.setTitle("Uneq");
                    dialog4.setContentView(R.layout.popup_unequip);
                    dialog4.show();
                    dialog4.setCancelable(false);
                    dialog4.setCanceledOnTouchOutside(false);

                    longClick = true;

                    Button unequip = (Button) dialog4.findViewById(R.id.unequip);
                    Button back = (Button) dialog4.findViewById(R.id.back);

                    unequip.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            boolean ty = true;
                            int temporary = player_eq_helmet;
                            player_eq_helmet = 0;
                            if(idsDepot != null){
                            for (int i = 0; i < idsDepot.length; i++) {
                                if (temporary == Integer.parseInt(idsDepot[i])) {
                                    updateItemDepot(id_player, temporary, (Integer.parseInt(quantityDepot[i]) + 1));
                                    ty = false;
                                    break;
                                }
                            }
                            }
                            if (ty == true) {
                                insertItemDepot(id_player, temporary, 1);
                            }

                            saveEquipment();
                            dialog4.dismiss();
                            helmet_temp = 0;
                            longClick = false;

                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    getAll();
                                    getImages();
                                }
                            }, 500);

                        }

                    });

                    back.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog4.dismiss();
                            longClick = false;
                        }
                    });

                }

                return true;
            }
        });

        armor_img.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {

                if(armor_temp != 0) {

                    final Dialog dialog4 = new Dialog(EquipmentActivity.this);
                    dialog4.setTitle("Uneq");
                    dialog4.setContentView(R.layout.popup_unequip);
                    dialog4.show();
                    dialog4.setCancelable(false);
                    dialog4.setCanceledOnTouchOutside(false);

                    longClick = true;

                    Button unequip = (Button) dialog4.findViewById(R.id.unequip);
                    Button back = (Button) dialog4.findViewById(R.id.back);

                    unequip.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            boolean ty = true;
                            int temporary = player_eq_armor;
                            player_eq_armor = 0;

                            for (int i = 0; i < idsDepot.length; i++) {
                                if (temporary == Integer.parseInt(idsDepot[i])) {
                                    updateItemDepot(id_player, temporary, (Integer.parseInt(quantityDepot[i]) + 1));
                                    ty = false;
                                    break;
                                }
                            }
                            if (ty == true) {
                                insertItemDepot(id_player, temporary, 1);
                            }

                            saveEquipment();
                            dialog4.dismiss();
                            armor_temp = 0;
                            longClick = false;

                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    getAll();
                                    getImages();
                                }
                            }, 500);

                        }

                    });

                    back.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog4.dismiss();
                            longClick = false;
                        }
                    });

                }

                return true;
            }
        });

        legs_img.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {

                if(legs_temp != 0) {

                    final Dialog dialog4 = new Dialog(EquipmentActivity.this);
                    dialog4.setTitle("Uneq");
                    dialog4.setContentView(R.layout.popup_unequip);
                    dialog4.show();
                    dialog4.setCancelable(false);
                    dialog4.setCanceledOnTouchOutside(false);

                    longClick = true;

                    Button unequip = (Button) dialog4.findViewById(R.id.unequip);
                    Button back = (Button) dialog4.findViewById(R.id.back);

                    unequip.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            boolean ty = true;
                            int temporary = player_eq_legs;
                            player_eq_legs = 0;

                            for (int i = 0; i < idsDepot.length; i++) {
                                if (temporary == Integer.parseInt(idsDepot[i])) {
                                    updateItemDepot(id_player, temporary, (Integer.parseInt(quantityDepot[i]) + 1));
                                    ty = false;
                                    break;
                                }
                            }
                            if (ty == true) {
                                insertItemDepot(id_player, temporary, 1);
                            }

                            saveEquipment();
                            dialog4.dismiss();
                            legs_temp = 0;
                            longClick = false;

                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    getAll();
                                    getImages();
                                }
                            }, 500);

                        }

                    });

                    back.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog4.dismiss();
                            longClick = false;
                        }
                    });

                }

                return true;
            }
        });

        boots_img.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {

                if(boots_temp != 0) {

                    final Dialog dialog4 = new Dialog(EquipmentActivity.this);
                    dialog4.setTitle("Uneq");
                    dialog4.setContentView(R.layout.popup_unequip);
                    dialog4.show();
                    dialog4.setCancelable(false);
                    dialog4.setCanceledOnTouchOutside(false);

                    longClick = true;

                    Button unequip = (Button) dialog4.findViewById(R.id.unequip);
                    Button back = (Button) dialog4.findViewById(R.id.back);

                    unequip.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            boolean ty = true;
                            int temporary = player_eq_boots;
                            player_eq_boots = 0;

                            for (int i = 0; i < idsDepot.length; i++) {
                                if (temporary == Integer.parseInt(idsDepot[i])) {
                                    updateItemDepot(id_player, temporary, (Integer.parseInt(quantityDepot[i]) + 1));
                                    ty = false;
                                    break;
                                }
                            }
                            if (ty == true) {
                                insertItemDepot(id_player, temporary, 1);
                            }

                            saveEquipment();
                            dialog4.dismiss();
                            boots_temp = 0;
                            longClick = false;

                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    getAll();
                                    getImages();
                                }
                            }, 500);

                        }

                    });

                    back.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog4.dismiss();
                            longClick = false;
                        }
                    });

                }

                return true;
            }
        });

        weapon_img.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {

                if(weapon_temp != 0) {

                    final Dialog dialog4 = new Dialog(EquipmentActivity.this);
                    dialog4.setTitle("Uneq");
                    dialog4.setContentView(R.layout.popup_unequip);
                    dialog4.show();
                    dialog4.setCancelable(false);
                    dialog4.setCanceledOnTouchOutside(false);

                    longClick = true;

                    Button unequip = (Button) dialog4.findViewById(R.id.unequip);
                    Button back = (Button) dialog4.findViewById(R.id.back);

                    unequip.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            boolean ty = true;
                            int temporary = player_eq_weapon;
                            player_eq_weapon = 0;
                            if(idsDepotFew != null) {
                                for (int i = 0; i < idsDepot.length; i++) {
                                    if (temporary == Integer.parseInt(idsDepot[i])) {
                                        updateItemDepot(id_player, temporary, (Integer.parseInt(quantityDepot[i]) + 1));
                                        ty = false;
                                        break;
                                    }
                                }
                            }
                            if (ty == true) {
                                insertItemDepot(id_player, temporary, 1);
                            }

                            saveEquipment();
                            dialog4.dismiss();
                            weapon_temp = 0;
                            longClick = false;

                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    getAll();
                                    getImages();
                                }
                            }, 500);

                        }

                    });

                    back.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog4.dismiss();
                            longClick = false;
                        }
                    });

                }

                return true;
            }
        });

        shield_img.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {

                if(shield_temp != 0) {

                    final Dialog dialog4 = new Dialog(EquipmentActivity.this);
                    dialog4.setTitle("Uneq");
                    dialog4.setContentView(R.layout.popup_unequip);
                    dialog4.show();
                    dialog4.setCancelable(false);
                    dialog4.setCanceledOnTouchOutside(false);

                    longClick = true;

                    Button unequip = (Button) dialog4.findViewById(R.id.unequip);
                    Button back = (Button) dialog4.findViewById(R.id.back);

                    unequip.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            boolean ty = true;
                            int temporary = player_eq_shield;
                            player_eq_shield = 0;
                            if(idsDepot != null) {
                                for (int i = 0; i < idsDepot.length; i++) {
                                    if (temporary == Integer.parseInt(idsDepot[i])) {
                                        updateItemDepot(id_player, temporary, (Integer.parseInt(quantityDepot[i]) + 1));
                                        ty = false;
                                        break;
                                    }
                                }
                            }
                            if (ty == true) {
                                insertItemDepot(id_player, temporary, 1);
                            }

                            saveEquipment();
                            dialog4.dismiss();
                            shield_temp = 0;
                            longClick = false;

                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    getAll();
                                    getImages();
                                }
                            }, 500);

                        }

                    });

                    back.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog4.dismiss();
                            longClick = false;
                        }
                    });

                }

                return true;
            }
        });

        amulet_img.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {

                if(amulet_temp != 0) {

                    final Dialog dialog4 = new Dialog(EquipmentActivity.this);
                    dialog4.setTitle("Uneq");
                    dialog4.setContentView(R.layout.popup_unequip);
                    dialog4.show();
                    dialog4.setCancelable(false);
                    dialog4.setCanceledOnTouchOutside(false);

                    longClick = true;

                    Button unequip = (Button) dialog4.findViewById(R.id.unequip);
                    Button back = (Button) dialog4.findViewById(R.id.back);

                    unequip.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            boolean ty = true;
                            int temporary = player_eq_amulet;
                            player_eq_amulet = 0;

                            for (int i = 0; i < idsDepot.length; i++) {
                                if (temporary == Integer.parseInt(idsDepot[i])) {
                                    updateItemDepot(id_player, temporary, (Integer.parseInt(quantityDepot[i]) + 1));
                                    ty = false;
                                    break;
                                }
                            }
                            if (ty == true) {
                                insertItemDepot(id_player, temporary, 1);
                            }

                            saveEquipment();
                            dialog4.dismiss();
                            amulet_temp = 0;
                            longClick = false;

                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    getAll();
                                    getImages();
                                }
                            }, 500);

                        }

                    });

                    back.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog4.dismiss();
                            longClick = false;
                        }
                    });

                }

                return true;
            }
        });

        ring_left_img.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {

                if(ring_left_temp != 0) {

                    final Dialog dialog4 = new Dialog(EquipmentActivity.this);
                    dialog4.setTitle("Uneq");
                    dialog4.setContentView(R.layout.popup_unequip);
                    dialog4.show();
                    dialog4.setCancelable(false);
                    dialog4.setCanceledOnTouchOutside(false);

                    longClick = true;

                    Button unequip = (Button) dialog4.findViewById(R.id.unequip);
                    Button back = (Button) dialog4.findViewById(R.id.back);

                    unequip.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            boolean ty = true;
                            int temporary = player_eq_ring_left;
                            player_eq_ring_left = 0;

                            for (int i = 0; i < idsDepot.length; i++) {
                                if (temporary == Integer.parseInt(idsDepot[i])) {
                                    updateItemDepot(id_player, temporary, (Integer.parseInt(quantityDepot[i]) + 1));
                                    ty = false;
                                    break;
                                }
                            }
                            if (ty == true) {
                                insertItemDepot(id_player, temporary, 1);
                            }

                            saveEquipment();
                            dialog4.dismiss();
                            ring_left_temp = 0;
                            longClick = false;

                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    getAll();
                                    getImages();
                                }
                            }, 500);

                        }

                    });

                    back.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog4.dismiss();
                            longClick = false;
                        }
                    });

                }

                return true;
            }
        });

        ring_right_img.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {

                if(ring_right_temp != 0) {

                    final Dialog dialog4 = new Dialog(EquipmentActivity.this);
                    dialog4.setTitle("Uneq");
                    dialog4.setContentView(R.layout.popup_unequip);
                    dialog4.show();
                    dialog4.setCancelable(false);
                    dialog4.setCanceledOnTouchOutside(false);

                    longClick = true;

                    Button unequip = (Button) dialog4.findViewById(R.id.unequip);
                    Button back = (Button) dialog4.findViewById(R.id.back);

                    unequip.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            boolean ty = true;
                            int temporary = player_eq_ring_right;
                            player_eq_ring_right = 0;

                            for (int i = 0; i < idsDepot.length; i++) {
                                if (temporary == Integer.parseInt(idsDepot[i])) {
                                    updateItemDepot(id_player, temporary, (Integer.parseInt(quantityDepot[i]) + 1));
                                    ty = false;
                                    break;
                                }
                            }
                            if (ty == true) {
                                insertItemDepot(id_player, temporary, 1);
                            }

                            saveEquipment();
                            dialog4.dismiss();
                            ring_right_temp = 0;
                            longClick = false;

                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    getAll();
                                    getImages();
                                }
                            }, 500);

                        }

                    });

                    back.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog4.dismiss();
                            longClick = false;
                        }
                    });

                }

                return true;
            }
        });

        final GridView grid = (GridView) findViewById(R.id.gridView);
        grid.setAdapter(new ImageAdapter(this));

        getAll();

        grid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {


            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, final int position, long arg3) {
                if(!ConfigDepot.images[0].equals("null") && ((ImageAdapter) grid.getAdapter()).checkImage(position)!=R.mipmap.empty) {
                    final Dialog dialog2 = new Dialog(EquipmentActivity.this);
                    dialog2.setTitle("Eq");
                    dialog2.setContentView(R.layout.popup_equip);
                    dialog2.show();
                    dialog2.setCancelable(false);
                    dialog2.setCanceledOnTouchOutside(false);

                    longClick = true;

                    Button equip = (Button) dialog2.findViewById(R.id.unequip);
                    Button back = (Button) dialog2.findViewById(R.id.back);

                    equip.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (!subtypesFew[position].equals("ring")) {

                                if (typesDepotFew[position].equals("weapon")) {

                                    if (weapon_temp == 0) {
                                        player_eq_weapon = Integer.parseInt(idsDepotFew[position]);
                                        saveEquipment();

                                        if (Integer.parseInt(quantityDepotFew[position]) == 1) {
                                            deleteItem(id_player, idsDepotFew[position]);
                                            getAll();
                                        } else {
                                            for (int i = 0; i < idsDepot.length; i++) {
                                                if (player_eq_weapon == Integer.parseInt(idsDepot[i])) {
                                                    updateItemDepot(id_player, Integer.parseInt(idsDepotFew[position]), (Integer.parseInt(quantityDepot[i]) - 1));
                                                    break;
                                                }
                                            }
                                        }

                                        dialog2.dismiss();
                                        weapon_temp = 1;
                                        longClick = false;
                                    } else {
                                        int temp = player_eq_weapon;
                                        player_eq_weapon = Integer.parseInt(idsDepotFew[position]);

                                        if (temp != player_eq_weapon) {

                                            if (Integer.parseInt(quantityDepotFew[position]) > 1) {
                                                updateItemDepot(id_player, Integer.parseInt(idsDepotFew[position]),
                                                        (Integer.parseInt(quantityDepotFew[position]) - 1));
                                                boolean ty = true;

                                                for (int i = 0; i < idsDepot.length; i++) {
                                                    if (temp == Integer.parseInt(idsDepot[i])) {
                                                        updateItemDepot(id_player, temp, (Integer.parseInt(quantityDepot[i]) + 1));
                                                        ty = false;
                                                        break;
                                                    }
                                                }
                                                if (ty == true) {
                                                    insertItemDepot(id_player, temp, 1);
                                                }

                                            } else {
                                                boolean tu = true;
                                                deleteItem(id_player, Integer.toString(player_eq_weapon));
                                                getAll();
                                                for (int i = 0; i < idsDepot.length; i++) {
                                                    if (temp == Integer.parseInt(idsDepot[i])) {
                                                        updateItemDepot(id_player, temp, (Integer.parseInt(quantityDepot[i]) + 1));
                                                        tu = false;
                                                        break;
                                                    }
                                                }
                                                if (tu == true) {
                                                    insertItemDepot(id_player, temp, 1);
                                                }


                                            }
                                        }

                                        dialog2.dismiss();
                                        longClick = false;
                                        saveEquipment();
                                        getAll();

                                    }

                                    final Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            getAll();
                                            getImages();
                                        }
                                    }, 500);


                                } else if (typesDepotFew[position].equals("shield")) {


                                    if (shield_temp == 0) {
                                        player_eq_shield = Integer.parseInt(idsDepotFew[position]);
                                        saveEquipment();
                                        if (Integer.parseInt(quantityDepotFew[position]) == 1) {
                                            deleteItem(id_player, idsDepotFew[position]);
                                        } else {
                                            for (int i = 0; i < idsDepot.length; i++) {
                                                if (player_eq_shield == Integer.parseInt(idsDepot[i])) {
                                                    updateItemDepot(id_player, Integer.parseInt(idsDepotFew[position]), (Integer.parseInt(quantityDepot[i]) - 1));
                                                    break;
                                                }
                                            }
                                        }
                                        dialog2.dismiss();
                                        shield_temp = 1;
                                        longClick = false;
                                    } else {
                                        int temp = player_eq_shield;
                                        player_eq_shield = Integer.parseInt(idsDepotFew[position]);

                                        if (temp != player_eq_shield) {

                                            if (Integer.parseInt(quantityDepotFew[position]) > 1) {
                                                updateItemDepot(id_player, Integer.parseInt(idsDepotFew[position]),
                                                        (Integer.parseInt(quantityDepotFew[position]) - 1));
                                                boolean ty = true;

                                                for (int i = 0; i < idsDepot.length; i++) {
                                                    if (temp == Integer.parseInt(idsDepot[i])) {
                                                        updateItemDepot(id_player, temp, (Integer.parseInt(quantityDepot[i]) + 1));
                                                        ty = false;
                                                        break;
                                                    }
                                                }
                                                if (ty == true) {
                                                    insertItemDepot(id_player, temp, 1);
                                                }

                                            } else {
                                                boolean tu = true;
                                                deleteItem(id_player, Integer.toString(player_eq_shield));

                                                for (int i = 0; i < idsDepot.length; i++) {
                                                    if (temp == Integer.parseInt(idsDepot[i])) {
                                                        updateItemDepot(id_player, temp, (Integer.parseInt(quantityDepot[i]) + 1));
                                                        tu = false;
                                                        break;
                                                    }
                                                }
                                                if (tu == true) {
                                                    insertItemDepot(id_player, temp, 1);
                                                }


                                            }
                                        }

                                        dialog2.dismiss();
                                        longClick = false;
                                        saveEquipment();

                                    }

                                    final Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            getAll();
                                            getImages();
                                        }
                                    }, 500);


                                } else if (subtypesFew[position].equals("helmet")) {

                                    if (helmet_temp == 0) {
                                        player_eq_helmet = Integer.parseInt(idsDepotFew[position]);
                                        saveEquipment();
                                        if (Integer.parseInt(quantityDepotFew[position]) == 1) {
                                            deleteItem(id_player, idsDepotFew[position]);
                                        } else {
                                            for (int i = 0; i < idsDepot.length; i++) {
                                                if (player_eq_helmet == Integer.parseInt(idsDepot[i])) {
                                                    updateItemDepot(id_player, Integer.parseInt(idsDepotFew[position]), (Integer.parseInt(quantityDepot[i]) - 1));
                                                    break;
                                                }
                                            }
                                        }
                                        dialog2.dismiss();
                                        helmet_temp = 1;
                                        longClick = false;
                                    } else {
                                        int temp = player_eq_helmet;
                                        player_eq_helmet = Integer.parseInt(idsDepotFew[position]);

                                        if (temp != player_eq_helmet) {

                                            if (Integer.parseInt(quantityDepotFew[position]) > 1) {
                                                updateItemDepot(id_player, Integer.parseInt(idsDepotFew[position]),
                                                        (Integer.parseInt(quantityDepotFew[position]) - 1));
                                                boolean ty = true;

                                                for (int i = 0; i < idsDepot.length; i++) {
                                                    if (temp == Integer.parseInt(idsDepot[i])) {
                                                        updateItemDepot(id_player, temp, (Integer.parseInt(quantityDepot[i]) + 1));
                                                        ty = false;
                                                        break;
                                                    }
                                                }
                                                if (ty == true) {
                                                    insertItemDepot(id_player, temp, 1);
                                                }

                                            } else {
                                                boolean tu = true;
                                                deleteItem(id_player, Integer.toString(player_eq_helmet));
                                                for (int i = 0; i < idsDepot.length; i++) {
                                                    if (temp == Integer.parseInt(idsDepot[i])) {
                                                        updateItemDepot(id_player, temp, (Integer.parseInt(quantityDepot[i]) + 1));
                                                        tu = false;
                                                        break;
                                                    }
                                                }
                                                if (tu == true) {
                                                    insertItemDepot(id_player, temp, 1);
                                                }


                                            }
                                        }

                                        dialog2.dismiss();
                                        longClick = false;
                                        saveEquipment();

                                    }

                                    final Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            getAll();
                                            getImages();
                                        }
                                    }, 500);


                                } else if (subtypesFew[position].equals("armor")) {


                                    if (armor_temp == 0) {
                                        player_eq_armor = Integer.parseInt(idsDepotFew[position]);
                                        saveEquipment();
                                        if (Integer.parseInt(quantityDepotFew[position]) == 1) {
                                            deleteItem(id_player, idsDepotFew[position]);
                                        } else {
                                            for (int i = 0; i < idsDepot.length; i++) {
                                                if (player_eq_armor == Integer.parseInt(idsDepot[i])) {
                                                    updateItemDepot(id_player, Integer.parseInt(idsDepotFew[position]), (Integer.parseInt(quantityDepot[i]) - 1));
                                                    break;
                                                }
                                            }
                                        }

                                        dialog2.dismiss();
                                        armor_temp = 1;
                                        longClick = false;
                                    } else {
                                        int temp = player_eq_armor;
                                        player_eq_armor = Integer.parseInt(idsDepotFew[position]);

                                        if (temp != player_eq_armor) {

                                            if (Integer.parseInt(quantityDepotFew[position]) > 1) {
                                                updateItemDepot(id_player, Integer.parseInt(idsDepotFew[position]),
                                                        (Integer.parseInt(quantityDepotFew[position]) - 1));
                                                boolean ty = true;

                                                for (int i = 0; i < idsDepot.length; i++) {
                                                    if (temp == Integer.parseInt(idsDepot[i])) {
                                                        updateItemDepot(id_player, temp, (Integer.parseInt(quantityDepot[i]) + 1));
                                                        ty = false;
                                                        break;
                                                    }
                                                }
                                                if (ty == true) {
                                                    insertItemDepot(id_player, temp, 1);
                                                }

                                            } else {
                                                boolean tu = true;
                                                deleteItem(id_player, Integer.toString(player_eq_armor));
                                                for (int i = 0; i < idsDepot.length; i++) {
                                                    if (temp == Integer.parseInt(idsDepot[i])) {
                                                        updateItemDepot(id_player, temp, (Integer.parseInt(quantityDepot[i]) + 1));
                                                        tu = false;
                                                        break;
                                                    }
                                                }
                                                if (tu == true) {
                                                    insertItemDepot(id_player, temp, 1);
                                                }


                                            }
                                        }


                                        dialog2.dismiss();
                                        longClick = false;
                                        saveEquipment();

                                    }

                                    final Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            getAll();
                                            getImages();
                                        }
                                    }, 500);


                                } else if (subtypesFew[position].equals("legs")) {

                                    if (legs_temp == 0) {
                                        player_eq_legs = Integer.parseInt(idsDepotFew[position]);
                                        saveEquipment();
                                        if (Integer.parseInt(quantityDepotFew[position]) == 1) {
                                            deleteItem(id_player, idsDepotFew[position]);
                                        } else {
                                            for (int i = 0; i < idsDepot.length; i++) {
                                                if (player_eq_legs == Integer.parseInt(idsDepot[i])) {
                                                    updateItemDepot(id_player, Integer.parseInt(idsDepotFew[position]), (Integer.parseInt(quantityDepot[i]) - 1));
                                                    break;
                                                }
                                            }
                                        }
                                        dialog2.dismiss();
                                        legs_temp = 1;
                                        longClick = false;
                                    } else {
                                        int temp = player_eq_legs;
                                        player_eq_legs = Integer.parseInt(idsDepotFew[position]);

                                        if (temp != player_eq_legs) {

                                            if (Integer.parseInt(quantityDepotFew[position]) > 1) {
                                                updateItemDepot(id_player, Integer.parseInt(idsDepotFew[position]),
                                                        (Integer.parseInt(quantityDepotFew[position]) - 1));
                                                boolean ty = true;

                                                for (int i = 0; i < idsDepot.length; i++) {
                                                    if (temp == Integer.parseInt(idsDepot[i])) {
                                                        updateItemDepot(id_player, temp, (Integer.parseInt(quantityDepot[i]) + 1));
                                                        ty = false;
                                                        break;
                                                    }
                                                }
                                                if (ty == true) {
                                                    insertItemDepot(id_player, temp, 1);
                                                }

                                            } else {
                                                boolean tu = true;
                                                deleteItem(id_player, Integer.toString(player_eq_legs));
                                                for (int i = 0; i < idsDepot.length; i++) {
                                                    if (temp == Integer.parseInt(idsDepot[i])) {
                                                        updateItemDepot(id_player, temp, (Integer.parseInt(quantityDepot[i]) + 1));
                                                        tu = false;
                                                        break;
                                                    }
                                                }
                                                if (tu == true) {
                                                    insertItemDepot(id_player, temp, 1);
                                                }


                                            }
                                        }

                                        dialog2.dismiss();
                                        longClick = false;
                                        saveEquipment();

                                    }

                                    final Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            getAll();
                                            getImages();
                                        }
                                    }, 500);

                                } else if (subtypesFew[position].equals("boots")) {

                                    if (boots_temp == 0) {
                                        player_eq_boots = Integer.parseInt(idsDepotFew[position]);
                                        saveEquipment();
                                        if (Integer.parseInt(quantityDepotFew[position]) == 1) {
                                            deleteItem(id_player, idsDepotFew[position]);
                                        } else {
                                            for (int i = 0; i < idsDepot.length; i++) {
                                                if (player_eq_boots == Integer.parseInt(idsDepot[i])) {
                                                    updateItemDepot(id_player, Integer.parseInt(idsDepotFew[position]), (Integer.parseInt(quantityDepot[i]) - 1));
                                                    break;
                                                }
                                            }
                                        }
                                        dialog2.dismiss();
                                        helmet_temp = 1;
                                        longClick = false;
                                    } else {
                                        int temp = player_eq_boots;
                                        player_eq_boots = Integer.parseInt(idsDepotFew[position]);

                                        if (temp != player_eq_boots) {

                                            if (Integer.parseInt(quantityDepotFew[position]) > 1) {
                                                updateItemDepot(id_player, Integer.parseInt(idsDepotFew[position]),
                                                        (Integer.parseInt(quantityDepotFew[position]) - 1));
                                                boolean ty = true;

                                                for (int i = 0; i < idsDepot.length; i++) {
                                                    if (temp == Integer.parseInt(idsDepot[i])) {
                                                        updateItemDepot(id_player, temp, (Integer.parseInt(quantityDepot[i]) + 1));
                                                        ty = false;
                                                        break;
                                                    }
                                                }
                                                if (ty == true) {
                                                    insertItemDepot(id_player, temp, 1);
                                                }

                                            } else {
                                                boolean tu = true;
                                                deleteItem(id_player, Integer.toString(player_eq_boots));
                                                for (int i = 0; i < idsDepot.length; i++) {
                                                    if (temp == Integer.parseInt(idsDepot[i])) {
                                                        updateItemDepot(id_player, temp, (Integer.parseInt(quantityDepot[i]) + 1));
                                                        tu = false;
                                                        break;
                                                    }
                                                }
                                                if (tu == true) {
                                                    insertItemDepot(id_player, temp, 1);
                                                }


                                            }
                                        }

                                        dialog2.dismiss();
                                        longClick = false;
                                        saveEquipment();

                                    }

                                    final Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            getAll();
                                            getImages();
                                        }
                                    }, 500);


                                } else if (subtypesFew[position].equals("amulet")) {

                                    if (amulet_temp == 0) {
                                        player_eq_amulet = Integer.parseInt(idsDepotFew[position]);
                                        saveEquipment();
                                        if (Integer.parseInt(quantityDepotFew[position]) == 1) {
                                            deleteItem(id_player, idsDepotFew[position]);
                                        } else {
                                            for (int i = 0; i < idsDepot.length; i++) {
                                                if (player_eq_amulet == Integer.parseInt(idsDepot[i])) {
                                                    updateItemDepot(id_player, Integer.parseInt(idsDepotFew[position]), (Integer.parseInt(quantityDepot[i]) - 1));
                                                    break;
                                                }
                                            }
                                        }
                                        dialog2.dismiss();
                                        amulet_temp = 1;
                                        longClick = false;
                                    } else {
                                        int temp = player_eq_amulet;
                                        player_eq_amulet = Integer.parseInt(idsDepotFew[position]);

                                        if (temp != player_eq_amulet) {

                                            if (Integer.parseInt(quantityDepotFew[position]) > 1) {
                                                updateItemDepot(id_player, Integer.parseInt(idsDepotFew[position]),
                                                        (Integer.parseInt(quantityDepotFew[position]) - 1));
                                                boolean ty = true;

                                                for (int i = 0; i < idsDepot.length; i++) {
                                                    if (temp == Integer.parseInt(idsDepot[i])) {
                                                        updateItemDepot(id_player, temp, (Integer.parseInt(quantityDepot[i]) + 1));
                                                        ty = false;
                                                        break;
                                                    }
                                                }
                                                if (ty == true) {
                                                    insertItemDepot(id_player, temp, 1);
                                                }

                                            } else {
                                                boolean tu = true;
                                                deleteItem(id_player, Integer.toString(player_eq_amulet));
                                                for (int i = 0; i < idsDepot.length; i++) {
                                                    if (temp == Integer.parseInt(idsDepot[i])) {
                                                        updateItemDepot(id_player, temp, (Integer.parseInt(quantityDepot[i]) + 1));
                                                        tu = false;
                                                        break;
                                                    }
                                                }
                                                if (tu == true) {
                                                    insertItemDepot(id_player, temp, 1);
                                                }


                                            }
                                        }

                                        dialog2.dismiss();
                                        longClick = false;
                                        saveEquipment();

                                    }

                                    final Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            getAll();
                                            getImages();
                                        }
                                    }, 500);

                                }
                            } else {
                                final Dialog dialog3 = new Dialog(EquipmentActivity.this);
                                dialog3.setTitle("Sell");
                                dialog3.setContentView(R.layout.popup_equip_ring);
                                dialog3.show();
                                dialog3.setCancelable(false);
                                dialog3.setCanceledOnTouchOutside(false);
                                dialog2.dismiss();

                                Button left = (Button) dialog3.findViewById(R.id.equipLeft);
                                Button right = (Button) dialog3.findViewById(R.id.equipRight);
                                Button backEq = (Button) dialog3.findViewById(R.id.back);

                                left.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        if (ring_left_temp == 0) {
                                            player_eq_ring_left = Integer.parseInt(idsDepotFew[position]);
                                            saveEquipment();
                                            if (Integer.parseInt(quantityDepotFew[position]) == 1) {
                                                deleteItem(id_player, idsDepotFew[position]);
                                            } else {
                                                for (int i = 0; i < idsDepot.length; i++) {
                                                    if (player_eq_ring_left == Integer.parseInt(idsDepot[i])) {
                                                        updateItemDepot(id_player, Integer.parseInt(idsDepotFew[position]), (Integer.parseInt(quantityDepot[i]) - 1));
                                                        break;
                                                    }
                                                }
                                            }
                                            dialog2.dismiss();
                                            dialog3.dismiss();
                                            ring_left_temp = 1;
                                            longClick = false;
                                        } else {
                                            int temp = player_eq_ring_left;
                                            player_eq_ring_left = Integer.parseInt(idsDepotFew[position]);

                                            if (temp != player_eq_ring_left) {

                                                if (Integer.parseInt(quantityDepotFew[position]) > 1) {
                                                    updateItemDepot(id_player, Integer.parseInt(idsDepotFew[position]),
                                                            (Integer.parseInt(quantityDepotFew[position]) - 1));
                                                    boolean ty = true;

                                                    for (int i = 0; i < idsDepot.length; i++) {
                                                        if (temp == Integer.parseInt(idsDepot[i])) {
                                                            updateItemDepot(id_player, temp, (Integer.parseInt(quantityDepot[i]) + 1));

                                                            ty = false;
                                                            break;
                                                        }
                                                    }
                                                    if (ty == true) {
                                                        insertItemDepot(id_player, temp, 1);
                                                    }

                                                } else {
                                                    boolean tu = true;
                                                    deleteItem(id_player, Integer.toString(player_eq_ring_left));
                                                    for (int i = 0; i < idsDepot.length; i++) {
                                                        if (temp == Integer.parseInt(idsDepot[i])) {
                                                            updateItemDepot(id_player, temp, (Integer.parseInt(quantityDepot[i]) + 1));
                                                            tu = false;
                                                            break;
                                                        }
                                                    }
                                                    if (tu == true) {
                                                        insertItemDepot(id_player, temp, 1);
                                                    }


                                                }
                                            }

                                            dialog2.dismiss();
                                            dialog3.dismiss();
                                            longClick = false;
                                            saveEquipment();

                                        }

                                        final Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                getAll();
                                                getImages();
                                            }
                                        }, 500);


                                    }
                                });

                                right.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        if (ring_right_temp == 0) {
                                            player_eq_ring_right = Integer.parseInt(idsDepotFew[position]);
                                            saveEquipment();
                                            if (Integer.parseInt(quantityDepotFew[position]) == 1) {
                                                deleteItem(id_player, idsDepotFew[position]);
                                            } else {
                                                for (int i = 0; i < idsDepot.length; i++) {
                                                    if (player_eq_ring_right == Integer.parseInt(idsDepot[i])) {
                                                        updateItemDepot(id_player, Integer.parseInt(idsDepotFew[position]), (Integer.parseInt(quantityDepot[i]) - 1));
                                                        break;
                                                    }
                                                }
                                            }
                                            dialog2.dismiss();
                                            dialog3.dismiss();
                                            ring_left_temp = 1;
                                            longClick = false;
                                        } else {
                                            int temp = player_eq_ring_right;
                                            player_eq_ring_right = Integer.parseInt(idsDepotFew[position]);

                                            if (temp != player_eq_ring_right) {

                                                if (Integer.parseInt(quantityDepotFew[position]) > 1) {
                                                    updateItemDepot(id_player, Integer.parseInt(idsDepotFew[position]),
                                                            (Integer.parseInt(quantityDepotFew[position]) - 1));
                                                    boolean ty = true;

                                                    for (int i = 0; i < idsDepot.length; i++) {
                                                        if (temp == Integer.parseInt(idsDepot[i])) {
                                                            updateItemDepot(id_player, temp, (Integer.parseInt(quantityDepot[i]) + 1));
                                                            ty = false;
                                                            break;
                                                        }
                                                    }
                                                    if (ty == true) {
                                                        insertItemDepot(id_player, temp, 1);
                                                    }

                                                } else {
                                                    boolean tu = true;
                                                    deleteItem(id_player, Integer.toString(player_eq_ring_right));
                                                    for (int i = 0; i < idsDepot.length; i++) {
                                                        if (temp == Integer.parseInt(idsDepot[i])) {
                                                            wait = true;
                                                            updateItemDepot(id_player, temp, (Integer.parseInt(quantityDepot[i]) + 1));
                                                            tu = false;
                                                            break;
                                                        }
                                                    }
                                                    if (tu == true) {
                                                        insertItemDepot(id_player, temp, 1);
                                                    }


                                                }
                                            }

                                            dialog2.dismiss();
                                            dialog3.dismiss();
                                            longClick = false;
                                            saveEquipment();

                                        }
                                        final Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                getAll();
                                                getImages();
                                            }
                                        }, 500);


                                    }
                                });


                                backEq.setOnClickListener(new View.OnClickListener() {
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

                if (longClick == false && ConfigDepot.images != null) {
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

    //Action after tapping Back Key
    @Override
    public void onBackPressed() {

        AlertDialog.Builder exit = new AlertDialog.Builder(this);
        exit.setMessage("Are you sure you want to exit the application?")
                .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        saveEquipment();
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

    public void back(View view) {

        saveEquipment();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();

    }

    public void loadGame() {

        SharedPreferences loadE = getSharedPreferences("Save", MODE_PRIVATE);

        id_player = loadE.getString("id_player", "");
        player_name = loadE.getString("player_name", "");

    }

    public void eq() {

        TextView hp = (TextView) findViewById(R.id.textViewHp);
        TextView mp = (TextView) findViewById(R.id.textViewMp);

        hp.setText(Integer.toString(player_health_potions)+"   ");
        mp.setText(Integer.toString(player_mana_potions) + "   ");

    }

    public void getPlayerId() {

        loadingGetPlayerId = ProgressDialog.show(this, "Please wait...", "Loading...", false, false);

        String url = ConfigPlayerID.DATA_URL+player_name;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loadingGetPlayerId.dismiss();
                showJSON(response);
                getImages();
                eq();

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(EquipmentActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void showJSON(String json) {

        String  playerHealthPotions = "", playerManaPotions = "", playerGold = "", playerEqHelmet = "",
                playerEqArmor = "", playerEqLegs = "", playerEqBoots = "", playerEqWeapon = "",
                playerEqShield = "", playerEqRingLeft = "", playerEqRingRight = "", playerEqAmulet = "";
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(ConfigPlayerID.JSON_ARRAY);//name of class
            JSONObject collegeData = result.getJSONObject(0);
            playerHealthPotions = collegeData.getString(ConfigPlayerID.KEY_HEALTH_POTIONS);
            playerManaPotions = collegeData.getString(ConfigPlayerID.KEY_MANA_POTIONS);
            playerEqHelmet = collegeData.getString(ConfigPlayerID.KEY_EQ_HELMET);
            playerEqArmor = collegeData.getString(ConfigPlayerID.KEY_EQ_ARMOR);
            playerEqLegs = collegeData.getString(ConfigPlayerID.KEY_EQ_LEGS);
            playerEqBoots = collegeData.getString(ConfigPlayerID.KEY_EQ_BOOTS);
            playerEqWeapon = collegeData.getString(ConfigPlayerID.KEY_EQ_WEAPON);
            playerEqShield = collegeData.getString(ConfigPlayerID.KEY_EQ_SHIELD);
            playerEqRingLeft = collegeData.getString(ConfigPlayerID.KEY_EQ_RING_LEFT);
            playerEqRingRight = collegeData.getString(ConfigPlayerID.KEY_EQ_RING_RIGHT);
            playerEqAmulet = collegeData.getString(ConfigPlayerID.KEY_EQ_AMULET);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Save data for equipment
        player_health_potions = Integer.parseInt(playerHealthPotions);
        player_mana_potions = Integer.parseInt(playerManaPotions);
        player_eq_helmet = Integer.parseInt(playerEqHelmet);
        player_eq_armor = Integer.parseInt(playerEqArmor);
        player_eq_legs = Integer.parseInt(playerEqLegs);
        player_eq_boots = Integer.parseInt(playerEqBoots);
        player_eq_weapon = Integer.parseInt(playerEqWeapon);
        player_eq_shield = Integer.parseInt(playerEqShield);
        player_eq_ring_left = Integer.parseInt(playerEqRingLeft);
        player_eq_ring_right = Integer.parseInt(playerEqRingRight);
        player_eq_amulet = Integer.parseInt(playerEqAmulet);

    }

    public void saveEquipment() {

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
                parameters.put("eq_helmet", Integer.toString(player_eq_helmet));
                parameters.put("eq_armor", Integer.toString(player_eq_armor));
                parameters.put("eq_legs", Integer.toString(player_eq_legs));
                parameters.put("eq_boots", Integer.toString(player_eq_boots));
                parameters.put("eq_weapon", Integer.toString(player_eq_weapon));
                parameters.put("eq_shield", Integer.toString(player_eq_shield));
                parameters.put("eq_ring_left", Integer.toString(player_eq_ring_left));
                parameters.put("eq_ring_right", Integer.toString(player_eq_ring_right));
                parameters.put("eq_amulet", Integer.toString(player_eq_amulet));

                return parameters;
            }
        };

        requestQueue.add(request);


    }

    public void showJSONImages(String json) {

        ConfigEquipment pj = new ConfigEquipment(json);
        pj.ConfigEquipment();

        idsEquipment = new int[ConfigEquipment.ids.length];
        imagesEquipment = new String[ConfigEquipment.images.length];
        descriptionsEquipment = new String[ConfigEquipment.descriptions.length];

        ImageView helmet = (ImageView) findViewById(R.id.imageViewHelmet);
        ImageView armor = (ImageView) findViewById(R.id.imageViewArmor);
        ImageView legs = (ImageView) findViewById(R.id.imageViewLegs);
        ImageView boots = (ImageView) findViewById(R.id.imageViewBoots);
        ImageView weapon = (ImageView) findViewById(R.id.imageViewWeapon);
        ImageView shield = (ImageView) findViewById(R.id.imageViewShield);
        ImageView ring_left = (ImageView) findViewById(R.id.imageViewRingLeft);
        ImageView ring_right = (ImageView) findViewById(R.id.imageViewRingRight);
        ImageView amulet = (ImageView) findViewById(R.id.imageViewAmulet);
        ImageView health_potion = (ImageView) findViewById(R.id.imageViewHp);
        ImageView mana_potion = (ImageView) findViewById(R.id.imageViewMp);

        //Fill table with data about equipment
        for (int i = 0; i < ConfigEquipment.ids.length; i++) {

            idsEquipment[i] = Integer.parseInt(ConfigEquipment.ids[i]);
            imagesEquipment[i] = ConfigEquipment.images[i];
            descriptionsEquipment[i] = ConfigEquipment.descriptions[i];

        }

        //Fill ImageView with images of equipment depends on type
        if(player_eq_helmet != 0) {
            for (int i = 0; i < idsEquipment.length; i++) {
                if(player_eq_helmet == idsEquipment[i]) {
                    int imageID = getResources().getIdentifier(imagesEquipment[i], "mipmap", "com.example.rafal.gra");
                    helmet.setImageResource(imageID);
                    descriptionHelmet = descriptionsEquipment[i];
                    helmet_temp = 1;
                    break;
                }
            }
        }else {
            int imageID = getResources().getIdentifier("helmet_empty", "mipmap", "com.example.rafal.gra");
            helmet.setImageResource(imageID);
        }

        if(player_eq_armor != 0) {
            for (int i = 0; i < idsEquipment.length; i++) {
                if(player_eq_armor == idsEquipment[i]) {
                    int imageID = getResources().getIdentifier(imagesEquipment[i], "mipmap", "com.example.rafal.gra");
                    armor.setImageResource(imageID);
                    descriptionArmor = descriptionsEquipment[i];
                    armor_temp = 1;
                    break;
                }
            }
        }else {
            int imageID = getResources().getIdentifier("armor_empty", "mipmap", "com.example.rafal.gra");
            armor.setImageResource(imageID);
        }

        if(player_eq_legs != 0) {
            for (int i = 0; i < idsEquipment.length; i++) {
                if(player_eq_legs == idsEquipment[i]) {
                    int imageID = getResources().getIdentifier(imagesEquipment[i], "mipmap", "com.example.rafal.gra");
                    legs.setImageResource(imageID);
                    descriptionLegs = descriptionsEquipment[i];
                    legs_temp = 1;
                    break;
                }
            }
        }else {
            int imageID = getResources().getIdentifier("legs_empty", "mipmap", "com.example.rafal.gra");
            legs.setImageResource(imageID);
        }

        if(player_eq_boots != 0) {
            for (int i = 0; i < idsEquipment.length; i++) {
                if(player_eq_boots == idsEquipment[i]) {
                    int imageID = getResources().getIdentifier(imagesEquipment[i], "mipmap", "com.example.rafal.gra");
                    boots.setImageResource(imageID);
                    descriptionBoots = descriptionsEquipment[i];
                    boots_temp = 1;
                    break;
                }
            }
        }else {
            int imageID = getResources().getIdentifier("boots_empty", "mipmap", "com.example.rafal.gra");
            boots.setImageResource(imageID);
        }

        if(player_eq_weapon != 0) {
            for (int i = 0; i < idsEquipment.length; i++) {
                if(player_eq_weapon == idsEquipment[i]) {
                    int imageID = getResources().getIdentifier(imagesEquipment[i], "mipmap", "com.example.rafal.gra");
                    weapon.setImageResource(imageID);
                    descriptionWeapon = descriptionsEquipment[i];
                    weapon_temp = 1;
                    break;
                }
            }
        }else {
            int imageID = getResources().getIdentifier("weapon_empty", "mipmap", "com.example.rafal.gra");
            weapon.setImageResource(imageID);
        }

        if(player_eq_shield != 0) {
            for (int i = 0; i < idsEquipment.length; i++) {
                if(player_eq_shield == idsEquipment[i]) {
                    int imageID = getResources().getIdentifier(imagesEquipment[i], "mipmap", "com.example.rafal.gra");
                    shield.setImageResource(imageID);
                    descriptionShield = descriptionsEquipment[i];
                    shield_temp = 1;
                    break;
                }
            }
        }else {
            int imageID = getResources().getIdentifier("shield_empty", "mipmap", "com.example.rafal.gra");
            shield.setImageResource(imageID);
        }

        if(player_eq_ring_left != 0) {
            for (int i = 0; i < idsEquipment.length; i++) {
                if(player_eq_ring_left == idsEquipment[i]) {
                    int imageID = getResources().getIdentifier(imagesEquipment[i], "mipmap", "com.example.rafal.gra");
                    ring_left.setImageResource(imageID);
                    descriptionRingLeft = descriptionsEquipment[i];
                    ring_left_temp = 1;
                    break;
                }
            }
        }else {
            int imageID = getResources().getIdentifier("ring_left_empty", "mipmap", "com.example.rafal.gra");
            ring_left.setImageResource(imageID);
        }

        if(player_eq_ring_right != 0) {
            for (int i = 0; i < idsEquipment.length; i++) {
                if(player_eq_ring_right == idsEquipment[i]) {
                    int imageID = getResources().getIdentifier(imagesEquipment[i], "mipmap", "com.example.rafal.gra");
                    ring_right.setImageResource(imageID);
                    descriptionRingRight = descriptionsEquipment[i];
                    ring_right_temp = 1;
                    break;
                }
            }
        }else {
            int imageID = getResources().getIdentifier("ring_right_empty", "mipmap", "com.example.rafal.gra");
            ring_right.setImageResource(imageID);
        }

        if(player_eq_amulet != 0) {
            for (int i = 0; i < idsEquipment.length; i++) {
                if(player_eq_amulet == idsEquipment[i]) {
                    int imageID = getResources().getIdentifier(imagesEquipment[i], "mipmap", "com.example.rafal.gra");
                    amulet.setImageResource(imageID);
                    descriptionAmulet = descriptionsEquipment[i];
                    amulet_temp = 1;
                    break;
                }
            }
        }else {
            int imageID = getResources().getIdentifier("amulet_empty", "mipmap", "com.example.rafal.gra");
            amulet.setImageResource(imageID);
        }

        if(player_health_potions == 0){
            health_potion.setImageResource(R.mipmap.h_potion_empty);
        }else{
            health_potion.setImageResource(R.mipmap.h_potion);
        }

        if(player_mana_potions == 0){
            mana_potion.setImageResource(R.mipmap.m_potion_empty);
        }else{
            mana_potion.setImageResource(R.mipmap.m_potion);
        }

    }

    public void getImages() {

        //loadingGetImages = ProgressDialog.show(this, "Please wait...", "Loading...", false, false);

        String url = ConfigEquipment.DATA_URL;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String json) {
                //loadingGetImages.dismiss();
                showJSONImages(json);
                wait = false;

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(EquipmentActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    public void imageClickHelmet(View view) {

        if(longClick==false && helmet_temp!=0) {
            ImageView helmet = (ImageView) findViewById(R.id.imageViewHelmet);
            String tag = (String) helmet.getTag();
            if (tag != "") {
                Toast.makeText(EquipmentActivity.this, descriptionHelmet, Toast.LENGTH_LONG).show();
            }
        }

    }

    public void imageClickArmor(View view) {

        if(longClick==false && armor_temp!=0) {
            ImageView armor = (ImageView) findViewById(R.id.imageViewArmor);
            String tag = (String) armor.getTag();
            if (tag != "") {
                Toast.makeText(EquipmentActivity.this, descriptionArmor, Toast.LENGTH_LONG).show();
            }
        }
    }

    public void imageClickLegs(View view) {

        if(longClick==false && legs_temp!=0) {
            ImageView legs = (ImageView) findViewById(R.id.imageViewLegs);
            String tag = (String) legs.getTag();
            if (tag != "") {
                Toast.makeText(EquipmentActivity.this, descriptionLegs, Toast.LENGTH_LONG).show();
            }
        }
    }

    public void imageClickBoots(View view) {

        if(longClick==false && boots_temp!=0) {
            ImageView boots = (ImageView) findViewById(R.id.imageViewBoots);
            String tag = (String) boots.getTag();
            if (tag != "") {
                Toast.makeText(EquipmentActivity.this, descriptionBoots, Toast.LENGTH_LONG).show();
            }
        }
    }

    public void imageClickWeapon(View view) {

        if(longClick==false && weapon_temp!=0) {
            ImageView weapon = (ImageView) findViewById(R.id.imageViewWeapon);
            String tag = (String) weapon.getTag();
            if (tag != "") {
                Toast.makeText(EquipmentActivity.this, descriptionWeapon, Toast.LENGTH_LONG).show();
            }
        }
    }

    public void imageClickShield(View view) {

        if(longClick==false && shield_temp!=0) {
            ImageView shield = (ImageView) findViewById(R.id.imageViewShield);
            String tag = (String) shield.getTag();
            if (tag != "") {
                Toast.makeText(EquipmentActivity.this, descriptionShield, Toast.LENGTH_LONG).show();
            }
        }
    }

    public void imageClickRingLeft(View view) {

        if(longClick==false && ring_left_temp!=0) {
            ImageView ringLeft = (ImageView) findViewById(R.id.imageViewRingLeft);
            String tag = (String) ringLeft.getTag();
            if (tag != "") {
                Toast.makeText(EquipmentActivity.this, descriptionRingLeft, Toast.LENGTH_LONG).show();
            }
        }
    }

    public void imageClickRingRight(View view) {

        if(longClick==false && ring_right_temp!=0) {
            ImageView ringRight = (ImageView) findViewById(R.id.imageViewRingRight);
            String tag = (String) ringRight.getTag();
            if (tag != "") {
                Toast.makeText(EquipmentActivity.this, descriptionRingRight, Toast.LENGTH_LONG).show();
            }
        }
    }

    public void imageClickAmulet(View view) {

        if(longClick==false && amulet_temp!=0) {
            ImageView amulet = (ImageView) findViewById(R.id.imageViewAmulet);
            String tag = (String) amulet.getTag();
            if (tag != "") {
                Toast.makeText(EquipmentActivity.this, descriptionAmulet, Toast.LENGTH_LONG).show();
            }
        }
    }

    public void imageClickHp(View view) {

        ImageView hp = (ImageView) findViewById(R.id.imageViewHp);
        String tag = (String) hp.getTag();

        //Changing format to english to switch ',' to '.' in double numbers and setting 2 numbers after '.'
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
        nf.setMinimumFractionDigits(2);

        if(player_health_potions != 0) {

            if (tag != "") {

                if (player_health_potions > 1) {

                    Toast.makeText(EquipmentActivity.this,
                            "You see " + player_health_potions + " health potions. They weigh " +
                                    nf.format(player_health_potions * 2.2) + " oz.",
                            Toast.LENGTH_LONG).show();

                } else {

                    Toast.makeText(EquipmentActivity.this,
                            "You see a health potion. It weighs 2.20 oz.",
                            Toast.LENGTH_LONG).show();

                }
            }
        }
    }

    public void imageClickMp(View view) {

        ImageView mp = (ImageView) findViewById(R.id.imageViewMp);
        String tag = (String) mp.getTag();

        //Changing format to english to switch ',' to '.' in double numbers and setting 2 numbers after '.'
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
        nf.setMinimumFractionDigits(2);

        if(player_health_potions != 0) {

            if (tag != "") {

                if (player_mana_potions > 1) {

                    Toast.makeText(EquipmentActivity.this,
                            "You see " + player_mana_potions + " mana potions. They weigh " +
                                    nf.format(player_mana_potions * 2.2) + " oz.",
                            Toast.LENGTH_LONG).show();

                } else {

                    Toast.makeText(EquipmentActivity.this,
                            "You see a mana potion. It weighs 2.20 oz.",
                            Toast.LENGTH_LONG).show();

                }
            }
        }

    }

    public void getAll() {

        loadingGetAll = ProgressDialog.show(this, "Please wait...", "Loading...", false, false);

        String url = ConfigDepot.DATA_URL + id_player;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String json) {
                loadingGetAll.dismiss();
                    showJSONgetAll(json);

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(EquipmentActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    public void showJSONgetAll(String json) {

        ConfigDepot pj = new ConfigDepot(json);
        pj.ConfigDepot();

        GridView grid = (GridView) findViewById(R.id.gridView);


        if(ConfigDepot.images != null)
        {
            imagesDepot = new String[ConfigDepot.images.length];
            typesDepot = new String[ConfigDepot.types.length];
            descriptionsDepot = new String[ConfigDepot.descriptions.length];
            weightDepot = new String[ConfigDepot.weight.length];
            quantityDepot = new String[ConfigDepot.quantity.length];
            namesDepot = new String[ConfigDepot.names.length];
            idsDepot = new String[ConfigDepot.ids.length];
            subtypes = new String[ConfigDepot.subtypes.length];

            for (int i = 0; i < 40; i++) {
                ((ImageAdapter) grid.getAdapter()).changeImage(i, R.mipmap.empty);
            }

            Arrays.fill(imagesDepot, null);
            Arrays.fill(typesDepot, null);
            Arrays.fill(weightDepot, null);
            Arrays.fill(namesDepot, null);
            Arrays.fill(quantityDepot, null);
            Arrays.fill(idsDepot, null);
            Arrays.fill(subtypes, null);

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
                idsDepot[i] = ConfigDepot.ids[i];
                //subtypes[i] = ConfigDepot.subtypes[i];
                quant += Integer.parseInt(quantityDepot[i]);

            }

            //Create arrays for items including repeated items
            imagesDepotFew = new String[quant];
            descriptionsDepotFew = new String[quant];
            typesDepotFew = new String[quant];
            quantityDepotFew = new String[quant];
            weightDepotFew = new String[quant];
            namesDepotFew = new String[quant];
            idsDepotFew = new String[quant];
            subtypesFew = new String[quant];

            //Reset arrays
            Arrays.fill(imagesDepotFew, null);
            Arrays.fill(descriptionsDepotFew, null);
            Arrays.fill(typesDepotFew, null);
            Arrays.fill(quantityDepotFew, null);
            Arrays.fill(weightDepotFew, null);
            Arrays.fill(namesDepotFew, null);
            Arrays.fill(idsDepotFew, null);
            Arrays.fill(subtypesFew, null);

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
                            idsDepotFew[k] = ConfigDepot.ids[i];
                            subtypesFew[k] = ConfigDepot.subtypes[i];

                            k += 1;
                        }
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
                        idsDepotFew[k] = ConfigDepot.ids[i];
                        subtypesFew[k] = ConfigDepot.subtypes[i];

                        k += 1;
                    }
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
                    Arrays.fill(idsDepotFew, null);
                }
            }

    }

    public void deleteItem(final String id_player, final String id_item){

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
        saveEquipment();
        wait = false;

    }

    public void insertItemDepot(final String id_player, final int id_item, final int quantity){

        StringRequest request = new StringRequest(Request.Method.POST, URL_INSERT_ITEM, new Response.Listener<String>() {
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
        wait = false;

    }

    public void updateItemDepot(final String id_player, final int id_item, final int quantity){

        StringRequest request = new StringRequest(Request.Method.POST, URL_UPDATE_ITEM, new Response.Listener<String>() {
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
        wait = false;


    }

}



