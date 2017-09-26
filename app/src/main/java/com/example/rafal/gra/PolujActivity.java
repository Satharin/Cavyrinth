package com.example.rafal.gra;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PolujActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poluj);

    }

    //Object to save variables
    public static Bundle dataPacketHunt = new Bundle();

    //Goblin's cave case
    public void goblinsCave(View view) {

        startActivity(new Intent(getApplicationContext(), WalkaActivity.class));
        int expPlace = 0;
        PolujActivity.dataPacketHunt.putInt("expPlace", expPlace);
        finish();

    }

    //Dragon's lair case
    public void dragonsLair(View view) {

        startActivity(new Intent(getApplicationContext(), WalkaActivity.class));
        int expPlace = 1;
        PolujActivity.dataPacketHunt.putInt("expPlace", expPlace);
        finish();

    }

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

    public void back(View view) {

        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();

    }

}
