package com.whackamole;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;



public class Settings extends AppCompatActivity {
    SharedPreferences.Editor editor;
    private final String[] difficulty = {"Easy", "Medium", "Hard"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        SharedPreferences sharedpreferences = getSharedPreferences("WhackAMole", Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        String value = sharedpreferences.getString("Background","Savana");
        RadioGroup group = findViewById(R.id.background);
        View settingView = findViewById(R.id.setting);
        switch (value) {
            case "Savana":
                settingView.setBackgroundResource(R.drawable.savana);
                group.check(R.id.savana);
                break;
            case "Forest":
                group.check(R.id.forest);
                settingView.setBackgroundResource(R.drawable.forest);
                break;
            case "White":
                group.check(R.id.white);
                settingView.setBackgroundResource(R.drawable.white);
                break;
        }

        value = sharedpreferences.getString("Sound","On");
        group = findViewById(R.id.sound);
        if (value.equals("On"))
            group.check(R.id.on);
        else
            group.check(R.id.mute);

        value = sharedpreferences.getString("Difficulty","Easy");
        group = findViewById(R.id.difficulty);
        if (value.equals(difficulty[0]))
            group.check(R.id.easy);
        else if (value.equals(difficulty[1]))
            group.check(R.id.normal);
        else
            group.check(R.id.hard);



        /* Create a listener for the Menu button so it will move to the Main page */
        findViewById(R.id.menu).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.this, MainScreen.class);
                startActivity(intent);
            }
        });
    }

    public void onClickSoundOn(View view) {
        editor.putString("Sound", "On");
        editor.commit();

    }

    public void onClickSoundOff(View view) {
        editor.putString("Sound", "Mute");
        editor.commit();
    }

    public void onClickEasy(View view) {
        editor.putString("Difficulty", "Easy");
        editor.commit();
    }

    public void onClickNormal(View view) {
        editor.putString("Difficulty", "Normal");
        editor.commit();
    }

    public void onClickHard(View view) {
        editor.putString("Difficulty", "Hard");
        editor.commit();
    }

    public void onClickSavana(View view) {
        editor.putString("Background", "Savana");
        editor.commit();
        View settingView = findViewById(R.id.setting);
        settingView.setBackgroundResource(R.drawable.savana);

    }

    public void onClickForest(View view) {
        editor.putString("Background", "Forest");
        editor.commit();
        View settingView = findViewById(R.id.setting);
        settingView.setBackgroundResource(R.drawable.forest);
    }

    public void onClickWhite(View view) {
        editor.putString("Background", "White");
        editor.commit();
        View settingView = findViewById(R.id.setting);
        settingView.setBackgroundResource(R.drawable.white);
    }
}
