package com.whackamole;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class game extends AppCompatActivity {

    TableLayout table;
    private static final int TABLE_WIDTH = 5;
    private static final int TABLE_HEIGHT = 3;
    private long missesCounter = 0;
    private long hitsCounter = 0;
    private boolean[] moleArr;
    private int standard;
    private Random random = new Random();
    private final String[] difficulty = {"Easy", "Medium", "Hard"};
    private int change = 0;
    private boolean isGameOn = false;
    private boolean isSoundOn = false;
    private String userName = "";
    private static List<Score> scoreList = new ArrayList<Score>();
    private String difficultyChose ="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        getSupportActionBar().hide();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        SharedPreferences sharedpreferences = getSharedPreferences("WhackAMole", Context.MODE_PRIVATE);
        String value = sharedpreferences.getString("Sound","On");
        if (value.equals("On"))
            isSoundOn = true;
        else
            isSoundOn = false;

        value = sharedpreferences.getString("Difficulty","Easy");
        if (value.equals(difficulty[0])) {
            difficultyChose = difficulty[0];
            standard = 2500;
        }
        else if (value.equals(difficulty[1])) {
            standard = 2000;
            difficultyChose = difficulty[1];
        }
        else {
            difficultyChose = difficulty[2];
            standard = 1500;
        }

        value = sharedpreferences.getString("Background","Savana");
        View settingView = findViewById(R.id.gamePage);
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
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Failed to read value." + error.toException(), Toast.LENGTH_SHORT).show();
            }
        });

        createTable();

        /* Create a listener for the Menu button so it will move to the Main page */
        findViewById(R.id.menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(game.this, MainScreen.class);
                startActivity(intent);
            }
        });

        /* Create a listener for the Start Game button so it will start the animation and timer.*/
        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        });
        }

    private void createTable() {
        table = (TableLayout) findViewById(R.id.board);
        moleArr = new boolean[TABLE_HEIGHT * TABLE_WIDTH];
        /* Populate the table with buttons with background. */
        for (int y = 0; y < TABLE_HEIGHT; y++) {
            final int row = y;
            TableRow r = new TableRow(this);
            table.addView(r);
            for (int x = 0; x < TABLE_WIDTH; x++) {
                final int col = x;
                final Button b = new Button(this);
                b.setBackgroundResource(R.drawable.empty);
                b.setWidth(50);
                b.setHeight(50);
                //b.setMaxHeight(50);
                //b.setMaxWidth(50);
                b.setId(y * TABLE_WIDTH + x);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Hit(b.getId());
                    }
                });
                r.addView(b);
            }
        }
    }

    private void startGame() {
        isGameOn = true;
        if(isSoundOn)
        {
            MediaPlayer ring = MediaPlayer.create(game.this, R.raw.space_rangers_awakening);
            ring.start();
        }
        missesCounter = 0;
        hitsCounter = 0;
        TextView hitText = findViewById(R.id.hits);
        hitText.setText("Hits: 0");
        TextView missText = findViewById(R.id.hits);
        missText.setText("Miss: 0");
        final TextView newText = findViewById(R.id.time);
        CountDownTimer myCountdownTimer = new CountDownTimer(30500, 500) {

            public void onTick(long millisUntilFinished) {
                newText.setText("Time: " + millisUntilFinished / 1000);
                if(change % standard == 0)
                    ChangeMole();
                change += 500;
            }

            public void onFinish() {
                userName = null;
                // the 30 seconds is up now so do make any checks you need here.
                isGameOn = false;
                ChangeMole();
                GetUserName();
            }
        }.start();

    }

    private void WriteUser() {
        Score score= new Score((hitsCounter-missesCounter),userName, difficultyChose);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userToAddRef = database.getReference("Score").child(String.valueOf(scoreList.size() +1));
        userToAddRef.setValue(score);
    }

    private void GetUserName(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Game Ended! what's your name?");

        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                userName = input.getText().toString();
                if(userName == null || userName.equals(""))
                    userName= "noName";
                WriteUser();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                userName = "noName";
                WriteUser();
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void ChangeMole() {
        for (int i=0; i<moleArr.length; i++) {
            boolGenerator(i);
            if (moleArr[i] == true &isGameOn)
                findViewById(i).setBackgroundResource(R.drawable.mole);
            else
                findViewById(i).setBackgroundResource(R.drawable.empty);
        }
        TextView newText = new TextView(this);
        newText= (TextView)findViewById(R.id.miss);
        newText.setText("Miss: "+ missesCounter);
    }

    private void boolGenerator(int id){
        int rnd = random.nextInt(100);
        boolean tmp = rnd > 95;
        if (moleArr[id] == true && !tmp)
            missesCounter++;
        moleArr[id] = tmp;
    }

    private void Hit(int id) {
        if(!isGameOn)
            return;
        TextView newText = new TextView(this);
        if (moleArr[id] == true) {
            if(isSoundOn) {
                MediaPlayer ring = MediaPlayer.create(game.this, R.raw.sound_effect);
                ring.start();
            }
            findViewById(id).setBackgroundResource(R.drawable.hit);
            hitsCounter++;
            newText= (TextView)findViewById(R.id.hits);
            newText.setText("Hits: "+ hitsCounter);
            moleArr[id] = false;
        }
        else {
            missesCounter++;
            newText= (TextView)findViewById(R.id.miss);
            newText.setText("Miss: "+ missesCounter);
        }
    }
}
