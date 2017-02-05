package jackgoza.umpirebuddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int strikes;
    private int balls;
    private int outs;
    private TextView ball_count;
    private TextView strike_count;
    private TextView out_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        strikes = 0;
        balls = 0;

        outs = 0;

        ball_count = (TextView) findViewById(R.id.ball_number);
        strike_count = (TextView) findViewById(R.id.strike_number);
        out_count = (TextView) findViewById(R.id.out_number);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.about_menu, menu);
        return true;
    }

    public void addStrike(View view){
        if (strikes < 2){
            strikes++;
            strike_count.setText(Integer.toString(strikes));
        }
        else{
            outs++;
            strikes = 0;
            balls = 0;
            strike_count.setText(Integer.toString(strikes));
            ball_count.setText(Integer.toString(balls));
            out_count.setText(Integer.toString(outs));
        }
    }

    public void addBall(View view){
        if (balls < 3){
            balls++;
            ball_count.setText(Integer.toString(balls));

        }
        else{
            strikes = 0;
            balls = 0;
            strike_count.setText(Integer.toString(strikes));
            ball_count.setText(Integer.toString(balls));
            out_count.setText(Integer.toString(outs));
        }
    }

    public boolean showAboutSplash(MenuItem item){
        Intent menuIntent = new Intent(this, SplashActivity.class);
        startActivity(menuIntent);
        return true;
    }

    public boolean resetCount(MenuItem item){
        strikes = 0;
        balls = 0;
        strike_count.setText(Integer.toString(strikes));
        ball_count.setText(Integer.toString(balls));
        out_count.setText(Integer.toString(outs));
        return true;
    }

}
