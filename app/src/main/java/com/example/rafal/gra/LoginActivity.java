package com.example.rafal.gra;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.rafal.gra.R.id.imageView4;


public class LoginActivity extends AppCompatActivity {

    private EditText editName, editPassword;

    String name, password;

    private ProgressDialog loadingLogin;

    RequestQueue requestQueue;

    public Integer image[] = {R.mipmap.logo_11, R.mipmap.logo_22};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        editName = (EditText) findViewById(R.id.editText);
        editPassword = (EditText) findViewById(R.id.editText2);

        final ImageView imageAnim =  (ImageView) findViewById(R.id.imageView4);

        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            int i = 0;

            public void run() {
                imageAnim.setImageResource(image[i]);
                i++;
                if (i > image.length - 1) {
                    i = 0;
                }
                handler.postDelayed(this, 1000);
            }
        };
        handler.postDelayed(runnable, 1000);
    }

    //Action after pressing Login button
    public void login(View view) {

        if(haveNetworkConnection() == true) {

            name = editName.getText().toString();
            password = editPassword.getText().toString();

            loadingLogin = ProgressDialog.show(this, "Please wait...", "Loading...", false, false);

            String url = ConfigLogin.DATA_URL + name + "&password=" + password;

            StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    loadingLogin.dismiss();
                    showJSON(response);

                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(LoginActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                        }
                    });

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }else{
            Toast.makeText(LoginActivity.this,"No network connection.", Toast.LENGTH_LONG).show();
        }

    }

    //Get data from database
    private void showJSON(String json) {

        String name2 = "", password2 = "";

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(ConfigLogin.JSON_ARRAY);//name of class
            JSONObject collegeData = result.getJSONObject(0);
            name2 = collegeData.getString(ConfigLogin.KEY_NAME);
            password2 = collegeData.getString(ConfigLogin.KEY_PASSWORD);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Check if name and password exist in databse
        if(name2.equals(name) && password2.equals(password)){
            saveGame();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }else{
            Toast.makeText(LoginActivity.this, "Wrong name or password.", Toast.LENGTH_LONG).show();
            editName.setText("");
            editPassword.setText("");
        }

    }

    //Go to CreateAccountActivity
    public void newAccount(View view){

        startActivity(new Intent(getApplicationContext(), CreateAccountActivity.class));
        finish();

    }

    //Check if player really wants to leave the game
    public void exitGame(View view) {

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

    //Action after tapping Back Key
    @Override
    public void onBackPressed() {

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

    //Save player_name
    public void saveGame() {

        SharedPreferences saveGame = getSharedPreferences("Save", MODE_PRIVATE);
        SharedPreferences.Editor save = saveGame.edit();
        save.putString("player_name", name);
        save.apply();

    }

    //Check if there is a network connection
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

