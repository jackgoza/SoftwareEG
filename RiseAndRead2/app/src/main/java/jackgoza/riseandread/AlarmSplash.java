package jackgoza.riseandread;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class AlarmSplash extends AppCompatActivity {
    AlarmManager alarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_splash);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
    }

    public void offButtonPressed(View view) {
        Intent myIntent = new Intent(getApplicationContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
        Intent intent = getIntent();
        intent.setClass(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplicationContext().startActivity(intent);
    }
}
