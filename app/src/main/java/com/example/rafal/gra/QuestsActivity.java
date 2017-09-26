package com.example.rafal.gra;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class QuestsActivity extends AppCompatActivity {

    int killedGoblinWarrior, killedGoblinMage, killedFireDragon, killedWaterDragon,
        questStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quests);

        loadGame();

        Button startGoblinWarrior = (Button) findViewById(R.id.startWarrior);
        Button startGoblinMage = (Button) findViewById(R.id.startMage);
        Button startFireDragon = (Button) findViewById(R.id.startFire);
        Button startWaterDragon = (Button) findViewById(R.id.startWater);

        TextView status = (TextView) findViewById(R.id.textViewStatus);

        if (questStarted == 0) {

            startGoblinWarrior.setClickable(true);
            startGoblinWarrior.setEnabled(true);
            startGoblinMage.setClickable(true);
            startGoblinMage.setEnabled(true);
            startFireDragon.setClickable(true);
            startFireDragon.setEnabled(true);
            startWaterDragon.setClickable(true);
            startWaterDragon.setEnabled(true);

        } else {

            startGoblinWarrior.setClickable(false);
            startGoblinWarrior.setEnabled(false);
            startGoblinMage.setClickable(false);
            startGoblinMage.setEnabled(false);
            startFireDragon.setClickable(false);
            startFireDragon.setEnabled(false);
            startWaterDragon.setClickable(false);
            startWaterDragon.setEnabled(false);

        }

        switch(questStarted)
        {
            case 1:
                status.setText("Goblin Warriors: " + Integer.toString(killedGoblinWarrior) + "/25");
                break;
            case 2:
                status.setText("Goblin Mages: " + Integer.toString(killedGoblinMage) + "/25");
                break;
            case 3:
                status.setText("Fire Dragons: " + Integer.toString(killedFireDragon) + "/25");
                break;
            case 4:
                status.setText("Water Dragons: " + Integer.toString(killedWaterDragon) + "/25");
                break;
            default:
                status.setText("");
        }

    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder exit = new AlertDialog.Builder(this);
        exit.setMessage("Are you sure you want to exit the application?")
                .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        saveGame();
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

    public void saveGame() {

        SharedPreferences saveGame = getSharedPreferences("Save", MODE_PRIVATE);
        SharedPreferences.Editor save = saveGame.edit();
        save.putInt("killedGoblinWarrior", killedGoblinWarrior);
        save.putInt("killedGoblinMage", killedGoblinMage);
        save.putInt("killedFireDragon", killedFireDragon);
        save.putInt("killedWaterDragon", killedWaterDragon);
        save.putInt("questStarted", questStarted);
        save.apply();
    }

    public void loadGame() {

        SharedPreferences loadE = getSharedPreferences("Save", MODE_PRIVATE);

        killedGoblinWarrior = loadE.getInt("killedGoblinWarrior", 0);
        killedGoblinMage = loadE.getInt("killedGoblinMage", 0);
        killedFireDragon = loadE.getInt("killedFireDragon", 0);
        killedWaterDragon = loadE.getInt("killedWaterDragon", 0);
        questStarted = loadE.getInt("questStarted", 0);

    }

    public void back(View view) {

        saveGame();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();

    }

    public void startGoblinWarriorQuest(View view) {

        final TextView status = (TextView) findViewById(R.id.textViewStatus);

        final Button startGoblinWarrior = (Button) findViewById(R.id.startWarrior);
        final Button startGoblinMage = (Button) findViewById(R.id.startMage);
        final Button startFireDragon = (Button) findViewById(R.id.startFire);
        final Button startWaterDragon = (Button) findViewById(R.id.startWater);

        AlertDialog.Builder start = new AlertDialog.Builder(this);
        start.setMessage("You have to kill 25 Goblin Warriors. Are you sure you want to start this quest? Reward: 250 experience and 200 gold.")
                .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        startGoblinWarrior.setClickable(false);
                        startGoblinWarrior.setEnabled(false);
                        startGoblinMage.setClickable(false);
                        startGoblinMage.setEnabled(false);
                        startFireDragon.setClickable(false);
                        startFireDragon.setEnabled(false);
                        startWaterDragon.setClickable(false);
                        startWaterDragon.setEnabled(false);

                        questStarted = 1;

                        status.setText("Goblin Warriors: " + Integer.toString(killedGoblinWarrior) + "/25");

                        killedGoblinWarrior = 0;
                        killedGoblinMage = 0;
                        killedFireDragon = 0;
                        killedWaterDragon = 0;

                    }
                })
                .setPositiveButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();

        start.show();

    }

    public void startGoblinMageQuest(View view) {

        final TextView status = (TextView) findViewById(R.id.textViewStatus);

        final Button startGoblinWarrior = (Button) findViewById(R.id.startWarrior);
        final Button startGoblinMage = (Button) findViewById(R.id.startMage);
        final Button startFireDragon = (Button) findViewById(R.id.startFire);
        final Button startWaterDragon = (Button) findViewById(R.id.startWater);

        AlertDialog.Builder start = new AlertDialog.Builder(this);
        start.setMessage("You have to kill 25 Goblin Mages. Are you sure you want to start this quest? Reward: 200 experience and 100 gold.")
                .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        startGoblinWarrior.setClickable(false);
                        startGoblinWarrior.setEnabled(false);
                        startGoblinMage.setClickable(false);
                        startGoblinMage.setEnabled(false);
                        startFireDragon.setClickable(false);
                        startFireDragon.setEnabled(false);
                        startWaterDragon.setClickable(false);
                        startWaterDragon.setEnabled(false);

                        questStarted = 2;

                        status.setText("Goblin Mages: " + Integer.toString(killedGoblinMage) + "/25");

                        killedGoblinWarrior = 0;
                        killedGoblinMage = 0;
                        killedFireDragon = 0;
                        killedWaterDragon = 0;

                    }
                })
                .setPositiveButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();

        start.show();

    }

    public void startFireDragonQuest(View view) {

        final TextView status = (TextView) findViewById(R.id.textViewStatus);

        final Button startGoblinWarrior = (Button) findViewById(R.id.startWarrior);
        final Button startGoblinMage = (Button) findViewById(R.id.startMage);
        final Button startFireDragon = (Button) findViewById(R.id.startFire);
        final Button startWaterDragon = (Button) findViewById(R.id.startWater);

        AlertDialog.Builder start = new AlertDialog.Builder(this);
        start.setMessage("You have to kill 25 Fire Dragons. Are you sure you want to start this quest? Reward: 500 experience and 400 gold.")
                .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        startGoblinWarrior.setClickable(false);
                        startGoblinWarrior.setEnabled(false);
                        startGoblinMage.setClickable(false);
                        startGoblinMage.setEnabled(false);
                        startFireDragon.setClickable(false);
                        startFireDragon.setEnabled(false);
                        startWaterDragon.setClickable(false);
                        startWaterDragon.setEnabled(false);

                        questStarted = 3;

                        status.setText("Fire Dragons: " + Integer.toString(killedFireDragon) + "/25");
                        killedGoblinWarrior = 0;
                        killedGoblinMage = 0;
                        killedFireDragon = 0;
                        killedWaterDragon = 0;

                    }
                })
                .setPositiveButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();

        start.show();

    }

    public void startWaterDragonQuest(View view) {

        final TextView status = (TextView) findViewById(R.id.textViewStatus);

        final Button startGoblinWarrior = (Button) findViewById(R.id.startWarrior);
        final Button startGoblinMage = (Button) findViewById(R.id.startMage);
        final Button startFireDragon = (Button) findViewById(R.id.startFire);
        final Button startWaterDragon = (Button) findViewById(R.id.startWater);

        AlertDialog.Builder start = new AlertDialog.Builder(this);
        start.setMessage("You have to kill 25 Water Dragons. Are you sure you want to start this quest? Reward: 500 experience and 400 gold.")
                .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        startGoblinWarrior.setClickable(false);
                        startGoblinWarrior.setEnabled(false);
                        startGoblinMage.setClickable(false);
                        startGoblinMage.setEnabled(false);
                        startFireDragon.setClickable(false);
                        startFireDragon.setEnabled(false);
                        startWaterDragon.setClickable(false);
                        startWaterDragon.setEnabled(false);

                        questStarted = 4;

                        status.setText("Water Dragons: " + Integer.toString(killedWaterDragon) + "/25");

                        killedGoblinWarrior = 0;
                        killedGoblinMage = 0;
                        killedFireDragon = 0;
                        killedWaterDragon = 0;

                    }
                })
                .setPositiveButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();

        start.show();

    }


}
