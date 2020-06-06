package whackamole.com;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MainScreen extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

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
