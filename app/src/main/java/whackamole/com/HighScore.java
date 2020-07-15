package com.whackamole;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.String;
import java.util.ArrayList;
import java.util.List;


import static com.whackamole.R.*;


public class HighScore extends AppCompatActivity {

    TableLayout table;
    private static final int TABLE_WIDTH = 4;
    private static final int TABLE_HEIGHT = 6;
    private static final String[] titles = {
            "rank","name","score", "difficulty"};
    private static List<Score> scoreList = new ArrayList<Score>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_high_score);
        SharedPreferences sharedpreferences = getSharedPreferences("WhackAMole", Context.MODE_PRIVATE);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference scoreRef = database.getReference("Score");
        scoreRef.orderByChild("points").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                long points;
                String name, difficulty;
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    if(ds.hasChild("name")) {
                        points = Long.parseLong(ds.child("points").getValue().toString());
                        name = ds.child("name").getValue().toString();
                        difficulty = ds.child("difficulty").getValue().toString();
                        Score score = new Score(points, name, difficulty);
                        scoreList.add(score);
                    }

                }
                CreateTable();
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Failed to read value." + error.toException(), Toast.LENGTH_SHORT).show();
            }
        });



        String value = sharedpreferences.getString("Background","Savana");
        View settingView = findViewById(R.id.scorePage);
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
        findViewById(id.menu).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HighScore.this, MainScreen.class);
                startActivity(intent);
            }
        });


    }

    private void CreateTable() {
        table = (TableLayout) findViewById(id.scores);

        // Populate the table with stuff
        for (int y = 0; y < TABLE_HEIGHT; y++) {
            TableRow r = new TableRow(this);
            table.addView(r);
            for (int x = 0; x < TABLE_WIDTH; x++) {
                TextView v = new TextView(this);
                v.setMinWidth(200);
                v.setTextSize(20);
                v.setGravity(Gravity.CENTER);
                v.setTypeface(null, Typeface.BOLD);
                v.setBackgroundResource(R.drawable.back);
                v.setTextColor(getResources().getColor(color.black));
                if(y == 0)
                    v.setText(titles[x]);
                else if(x == 0)
                    v.setText(String.valueOf(y));
                else if(scoreList.size() >= y && x ==1)
                    v.setText(scoreList.get(scoreList.size()- y).name);
                else if(scoreList.size() >= y && x ==2)
                    v.setText(String.valueOf(scoreList.get(scoreList.size()-y).points));
                else if(scoreList.size() >= y && x ==3)
                    v.setText(scoreList.get(scoreList.size() - y).difficulty);
                else
                    v.setText("0");
                r.addView(v);
            }
        }
    }


}
