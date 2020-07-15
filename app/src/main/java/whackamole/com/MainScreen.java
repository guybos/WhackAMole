package com.whackamole;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainScreen extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        SharedPreferences sharedpreferences = getSharedPreferences("WhackAMole", Context.MODE_PRIVATE);
        String value = sharedpreferences.getString("Background","Savana");
        View settingView = findViewById(R.id.mainPages);
        switch (value) {
            case "Savana":
                settingView.setBackgroundResource(R.drawable.savana);
                break;
            case "Forest":
                settingView.setBackgroundResource(R.drawable.forest);
                break;
            case "White":
                settingView.setBackgroundResource(R.drawable.white);
                break;
        }


        /* Create a listener for the Play button so it will move to the game page */
        findViewById(R.id.game).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainScreen.this, game.class);
             startActivity(intent);
        }
        });
        /* Create a listener for the High Score button so it will move to the High Score page*/
        findViewById(R.id.highscore).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainScreen.this, HighScore.class);
                startActivity(intent);
            }
        });
        /* Create a listener for the Instructions button so it will move to the Instructions page*/
        findViewById(R.id.instructions).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainScreen.this, Instructions.class);
                startActivity(intent);
            }
        });
        /* Create a listener for the Settings button so it will move to the Settings page*/
        findViewById(R.id.settings).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainScreen.this, Settings.class);
                startActivity(intent);
            }
        });
    }
}
