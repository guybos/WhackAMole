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

public class Instructions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);
        SharedPreferences sharedpreferences = getSharedPreferences("WhackAMole", Context.MODE_PRIVATE);

        String value = sharedpreferences.getString("Background","Savana");
        View settingView = findViewById(R.id.instructionsPage);
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

        /* Create a listener for the Menu button so it will move to the Main page */
        findViewById(R.id.menu).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Instructions.this, MainScreen.class);
                startActivity(intent);
            }
        });
    }
}
