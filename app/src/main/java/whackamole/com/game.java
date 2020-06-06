package whackamole.com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class game extends AppCompatActivity {

    TableLayout table;
    private static final int TABLE_WIDTH = 5;
    private static final int TABLE_HEIGHT = 3;
    private int missesCounter = 0;
    private  int hitsCounter = 0;
    private int currentId = 0;
    private int lastId = 0;
    private Random random = new Random();
    private static final String[] difficulty = {"Easy", "Medium", "Hard"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        table = (TableLayout) findViewById(R.id.board);

        /* Populate the table with buttons with background. */
        for (int y = 0; y < TABLE_HEIGHT; y++) {
            final int row = y;
            TableRow r = new TableRow(this);
            table.addView(r);
            for (int x = 0; x < TABLE_WIDTH; x++) {
                final int col = x;
                Button b = new Button(this);
                b.setBackgroundResource(R.drawable.empty);
                b.setWidth(50);
                b.setId(y*TABLE_WIDTH+x);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Miss();
                    }
                });
                r.addView(b);
            }
        }

        /* Create a listener for the Menu button so it will move to the Main page */
        findViewById(R.id.menu).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(game.this, MainScreen.class);
                startActivity(intent);
            }
        });

        /* Create a listener for the Start Game button so it will start the animation and timer.*/
        findViewById(R.id.start).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
              startgame();
            }
        });
    }

    private void startgame() {
        do {
            ChangeMole();
        }while(missesCounter > 3);
    }

    private void ChangeMole() {
        int timeintTd = random.nextInt(150000);
        do {
            currentId = random.nextInt(TABLE_HEIGHT*TABLE_WIDTH-1);
            findViewById(currentId).setBackgroundResource(R.drawable.mole);
        } while (currentId == lastId);


        final CountDownTimer counter = new CountDownTimer(timeintTd, 1000) {
            @Override
            public void onTick(long millisUntilFinished) { }

            public void onFinish() {
                ChangeBack();
            }
        }.start();
        findViewById(currentId).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Hit(counter);
            }
        });
        lastId = currentId;
    }

    private void ChangeBack() {
        int timeintTd = random.nextInt(10000);
        findViewById(currentId).setBackgroundResource(R.drawable.empty);
        findViewById(currentId).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Miss();
            }
        });
        new CountDownTimer(timeintTd, 1000) {
            @Override
            public void onTick(long millisUntilFinished) { }

            public void onFinish() {
                ChangeMole();
            }
        }.start();
    }

    private void Hit(CountDownTimer counter) {
        counter.cancel();
        hitsCounter++;
        TextView newText = new TextView(this);
        newText= (TextView)findViewById(R.id.hits);
        newText.setText("Hits: "+ hitsCounter);
        ChangeBack();
    }

    private void Miss() {
        missesCounter++;
        TextView newText = new TextView(this);
        newText= (TextView)findViewById(R.id.miss);
        newText.setText("Misses: "+ hitsCounter);
    }
}
