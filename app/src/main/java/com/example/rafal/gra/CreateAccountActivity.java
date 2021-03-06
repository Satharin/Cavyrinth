package com.example.rafal.gra;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateAccountActivity extends AppCompatActivity {

    public static final String DATA_URL = "https://mundial2018.000webhostapp.com/savePlayer.php";

    private EditText editName, editEmail, editPassword, editConfirm;

    RequestQueue requestQueue;

    String name, email, password, confirm, name2 = "", email2 = "";

    private ProgressDialog loadingCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        editName = (EditText) findViewById(R.id.editTextName);
        editEmail = (EditText) findViewById(R.id.editTextEmail);
        editPassword = (EditText) findViewById(R.id.editTextPassword);
        editConfirm = (EditText) findViewById(R.id.editTextConfirm);

    }

    //Action after pressing Create button
    public void createAccount(View view) {

        name = editName.getText().toString();
        email = editEmail.getText().toString();
        password = editPassword.getText().toString();
        confirm = editConfirm.getText().toString();

        //Check if fields are filled
        if(name.equals("")&&email.equals("")){
            Toast.makeText(CreateAccountActivity.this, "Fields 'Name' and 'Email' are empty.", Toast.LENGTH_LONG).show();
        }else if(name.equals("")){
            Toast.makeText(CreateAccountActivity.this, "Field 'Name' is empty.", Toast.LENGTH_LONG).show();
        }else if(email.equals("")) {
            Toast.makeText(CreateAccountActivity.this, "Field 'Email' is empty.", Toast.LENGTH_LONG).show();
        }else if(password.equals("")) {
            Toast.makeText(CreateAccountActivity.this, "Field 'Password' is empty.", Toast.LENGTH_LONG).show();
        }else if(confirm.equals("")) {
            Toast.makeText(CreateAccountActivity.this, "Field 'Confirm password' is empty.", Toast.LENGTH_LONG).show();
        }else{

            saveGame();

            loadingCreate = ProgressDialog.show(this, "Please wait...", "Fetching...", false, false);

            String url = ConfigCreate.DATA_URL + name + "&email=" + email;

            StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    loadingCreate.dismiss();
                    showJSON(response);

                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(CreateAccountActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                        }
                    });

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }

    }

    //Get data from database
    private void showJSON(String json) {

        name = editName.getText().toString();
        email = editEmail.getText().toString();
        password = editPassword.getText().toString();
        confirm = editConfirm.getText().toString();

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(ConfigCreate.JSON_ARRAY);//name of class
            JSONObject collegeData = result.getJSONObject(0);
            name2 = collegeData.getString(ConfigCreate.KEY_NAME);
            email2 = collegeData.getString(ConfigCreate.KEY_EMAIL);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Check inserted data
        if(name2.equals(name)) {
            Toast.makeText(CreateAccountActivity.this, "Name " + name + " already registered.", Toast.LENGTH_LONG).show();
            editName.setText("");
            editEmail.setText("");
            editPassword.setText("");
            editConfirm.setText("");
        }else if(isValidEmail(email) == false) {
            Toast.makeText(CreateAccountActivity.this, "Wrong email format.", Toast.LENGTH_LONG).show();
        }else if(email2.equals(email)){
            Toast.makeText(CreateAccountActivity.this, "Email " + email + " already registered.", Toast.LENGTH_LONG).show();
            editName.setText("");
            editEmail.setText("");
            editPassword.setText("");
            editConfirm.setText("");
        }else if(password.equals(confirm)&&!password.equals("")){
            Toast.makeText(CreateAccountActivity.this, "Registration completed.", Toast.LENGTH_LONG).show();
            savePlayer(); //Player is added to database
            saveGame();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }else{
            Toast.makeText(CreateAccountActivity.this, "Passwords do not match.", Toast.LENGTH_LONG).show();
            editPassword.setText("");
            editConfirm.setText("");
        }

    }

    //Check if inserted email has correct format
    private boolean isValidEmail(String email) {

        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();

    }

    //Go to LoginActivity
    public void back(View view) {

        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();

    }

    //Add player to database with default statistics
    public void savePlayer(){

        StringRequest request = new StringRequest(Request.Method.POST, DATA_URL, new Response.Listener<String>() {
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
                parameters.put("name", name);
                parameters.put("email", email);
                parameters.put("password", password);

                return parameters;
            }
        };

        requestQueue.add(request);

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

}
