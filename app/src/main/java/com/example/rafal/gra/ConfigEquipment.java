package com.example.rafal.gra;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ConfigEquipment {

    public static String[] ids, images, descriptions;

    public static final String DATA_URL = "https://mundial2018.000webhostapp.com/getItemsImage.php";

    public static final String JSON_ARRAY = "result";
    public static final String KEY_ID_ITEM = "id_item";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_DESCRIPTION = "description";

    private JSONArray equipment = null;

    private String json;

    public ConfigEquipment(String json){
        this.json = json;
    }

    protected void ConfigEquipment(){

        JSONObject jsonObject=null;

        try {
            jsonObject = new JSONObject(json);

            equipment = jsonObject.getJSONArray(JSON_ARRAY);

            ids = new String[equipment.length()];
            images = new String[equipment.length()];
            descriptions = new String[equipment.length()];

            for (int i = 0; i < equipment.length(); i++) {
                JSONObject jo = equipment.getJSONObject(i);
                ids[i] = jo.getString(KEY_ID_ITEM);
                images[i] = jo.getString(KEY_IMAGE);
                descriptions[i] = jo.getString(KEY_DESCRIPTION);
            }

        }catch(JSONException e){
            e.printStackTrace();
        }
    }

}
