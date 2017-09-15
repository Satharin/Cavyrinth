package com.example.rafal.gra;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GetLoot {

    public static String[] items;
    public static String[] chances;
    public static String[] ids;

    public static final String JSON_ARRAY = "result";
    public static final String KEY_NAME = "name";
    public static final String KEY_RARITY_CHANCES = "rarity_chances";
    public static final String KEY_ID_ITEM = "id_item";

    private JSONArray loot = null;

    private String json;

    public GetLoot(String json){

        this.json = json;

    }

    protected void GetLoot(){

        JSONObject jsonObject=null;

        try {
            jsonObject = new JSONObject(json);
            loot = jsonObject.getJSONArray(JSON_ARRAY);

            items = new String[loot.length()];
            chances = new String[loot.length()];
            ids = new String[loot.length()];

            for(int i=0;i<loot.length();i++){
                JSONObject jo = loot.getJSONObject(i);
                items[i] = jo.getString(KEY_NAME);
                chances[i] = jo.getString(KEY_RARITY_CHANCES);
                ids[i] = jo.getString(KEY_ID_ITEM);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
