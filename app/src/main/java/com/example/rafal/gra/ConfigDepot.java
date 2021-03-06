package com.example.rafal.gra;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class ConfigDepot {

    public static String[] images, types, descriptions, quantity, weight, names, prices, ids, subtypes;

    public static final String DATA_URL = "https://mundial2018.000webhostapp.com/getDepotByID.php?id=";

    public static final String JSON_ARRAY = "result";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_TYPE = "type";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_QUANTITY = "quantity";
    public static final String KEY_WEIGHT = "weight";
    public static final String KEY_NAME = "name";
    public static final String KEY_PRICE = "price";
    public static final String KEY_ID_ITEM = "id_item";
    public static final String KEY_SUBTYPE = "subtype";

    private JSONArray depot = null;

    private String json;

    public ConfigDepot(String json){
        this.json = json;
    }

    protected void ConfigDepot(){

        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(json);
            if(!JSON_ARRAY.equals("null")) {
            depot = jsonObject.getJSONArray(JSON_ARRAY);

                images = new String[depot.length()];
                types = new String[depot.length()];
                descriptions = new String[depot.length()];
                quantity = new String[depot.length()];
                weight = new String[depot.length()];
                names = new String[depot.length()];
                prices = new String[depot.length()];
                ids = new String[depot.length()];
                subtypes = new String[depot.length()];



                    for (int i = 0; i < depot.length(); i++) {
                        JSONObject jo = depot.getJSONObject(i);
                        images[i] = jo.getString(KEY_IMAGE);
                        types[i] = jo.getString(KEY_TYPE);
                        descriptions[i] = jo.getString(KEY_DESCRIPTION);
                        quantity[i] = jo.getString(KEY_QUANTITY);
                        weight[i] = jo.getString(KEY_WEIGHT);
                        names[i] = jo.getString(KEY_NAME);
                        prices[i] = jo.getString(KEY_PRICE);
                        ids[i] = jo.getString(KEY_ID_ITEM);
                        subtypes[i] = jo.getString(KEY_SUBTYPE);
                    }
            }else{
                    if(images != null){
                        Arrays.fill(images, null);}}


            }catch(JSONException e){
                e.printStackTrace();
        }
    }

}
