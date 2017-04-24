package jackgoza.riseandread;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Calendar;

import jackgoza.riseandread.fragments.AlarmFragment;
import jackgoza.riseandread.fragments.PageRight;
import jackgoza.riseandread.fragments.PlaceholderFragment;
import jackgoza.riseandread.fragments.WelcomeFragment;

public class MainActivity extends AppCompatActivity implements AlarmFragment.OnFragmentInteractionListener,
        PageRight.OnFragmentInteractionListener {

    MainActivity inst = this;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private JSONArray linkArray;

    public MainActivity instance() {
        return inst;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.

        String fakeIt = "[{\"itemType\":\"WEBSITE\",\"jumpURI\":\"www.cnet.com\",\"prettyName\":\"CNET\"},{\"itemType\":\"WEBSITE\",\"jumpURI\":\"www.gizmodo.com\",\"prettyName\":\"Gizmodo\"},{\"itemType\":\"MOBILE_APPLICATION\",\"jumpURI\":\"com.imgur.mobile\",\"prettyName\":\"Imgur\"}]";

        File directory;
        directory = getApplicationContext().getFilesDir();

        File[] files = directory.listFiles();

        /*
        String filePath = getApplicationContext().getFilesDir().getPath().toString() + "/user_preferences";
        File f = new File(filePath);


        try {
            f.delete();
            f.createNewFile();
            FileOutputStream fout = new FileOutputStream(f);
            ObjectOutputStream oout = new ObjectOutputStream(fout);
            oout.writeObject(fakeIt);
            fout.close();
        } catch (Exception e){
            throw new RuntimeException("SHIT");
        }
*/


        if (files.length == 0) {
            try {
                String filePath = getApplicationContext().getFilesDir().getPath().toString() + "/user_preferences";
                File f = new File(filePath);
                f.createNewFile();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                File fileIn = files[0];
                FileInputStream fin = new FileInputStream(fileIn);
                ObjectInputStream ois = new ObjectInputStream(fin);
                String intakeString = (String) ois.readObject();
                linkArray = new JSONArray(intakeString);
            } catch (IOException | ClassNotFoundException | JSONException e) {
                throw new RuntimeException(e);
            }
        }



        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), linkArray);
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(1);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void onFragmentInteraction(int hour, int minute, boolean buttonState) {

        if (buttonState) {
            Log.d("MyActivity", "Alarm On");

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
            calendar.add(Calendar.SECOND, 5);
            Intent myIntent = new Intent(MainActivity.this, AlarmReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            Toast.makeText(getApplicationContext(), "Alarm set for " + calendar.get(Calendar.HOUR) + ":" +
                    calendar.get(Calendar.MINUTE), Toast.LENGTH_SHORT).show();

        } else {
            alarmManager.cancel(pendingIntent);
            Log.d("MyActivity", "Alarm Off");
            Toast.makeText(getApplicationContext(), "Alarm turned off!", Toast.LENGTH_SHORT).show();
        }

    }

    public void onPageRightInteraction() {
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

        startActivityForResult(chooserIntent, 1);
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        AlarmFragment alarmFragment = AlarmFragment.newInstance(null, null);
        PlaceholderFragment placeHolder = PlaceholderFragment.newInstance(3);
        private JSONArray linkArray;
        WelcomeFragment welcomeFragment = WelcomeFragment.newInstance();
        PageRight pageRight = PageRight.newInstance(linkArray);

        public SectionsPagerAdapter(FragmentManager fm, JSONArray inputArray) {
            super(fm);
            linkArray = inputArray;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    return alarmFragment;
                case 1:
                    return welcomeFragment;
                case 2:
                    return pageRight;
                default:
                    return placeHolder;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }


    }
}
