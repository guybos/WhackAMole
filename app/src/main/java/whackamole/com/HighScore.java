package whackamole.com;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import java.lang.String;


import static whackamole.com.R.*;


public class HighScore extends AppCompatActivity {

    TableLayout table;
    private static final int TABLE_WIDTH = 4;
    private static final int TABLE_HEIGHT = 6;
    private static final String[] titles = {
            "rank","name","score", "difficulty"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_high_score);

        table = (TableLayout) findViewById(id.scores);

        // Populate the table with stuff
        for (int y = 0; y < TABLE_HEIGHT; y++) {
            final int row = y;
            TableRow r = new TableRow(this);
            table.addView(r);
            for (int x = 0; x < TABLE_WIDTH; x++) {
                final int col = x;
                TextView v = new TextView(this);
                v.setWidth(400);
                v.setTextSize(26);
                v.setTypeface(null, Typeface.BOLD);
                v.setBackgroundResource(R.drawable.back);
                v.setTextColor(getResources().getColor(color.black));
                if(y==0)
                    v.setText(titles[x]);
                else
                    v.setText("0");
                r.addView(v);
            }
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


}
