package whackamole.com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Thread(new Runnable() {
            @Override public void run()  {
                try {
                    synchronized (this) {
                        wait(5000);
                        Intent intent = new Intent (MainActivity.this,MainScreen.class);
                        startActivity(intent);
                        finish();
                    }
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
