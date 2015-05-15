package ru.legonat.sportsnow;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;
import java.util.List;

import ru.legonat.sportsnow.DatabaseReader.Club;
import ru.legonat.sportsnow.DatabaseReader.DB;

public class MainActivity extends ActionBarActivity {
    public static int selectedClub=0;
    SharedPreferences sPref;
    final String CHOSEN_CLUB = "saved_text";
    public static int DBsize=16;
    public static String[] names= new String[DBsize];
    public static String[] urls= new String[DBsize];
    static final private int CHOOSE_CLUB=0;
    final String LOG_TAG = "myLogs";
    public static String clubUrl="null";



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        testPreLoadedSQLiteDb();
        loadText();
        if (selectedClub==0) { // checking if user have already chosen club
            //if not chosen, loading ClubsList
            Intent questionIntent = new Intent(MainActivity.this, ClubsList.class);
            startActivityForResult(questionIntent, CHOOSE_CLUB);
        }
        else { //if chosen, loading news
            if (savedInstanceState == null) {
                clubUrl=urls[selectedClub];
                addRssFragment();
            }
        }
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.refresh: // refresh list of news
                Intent i = getBaseContext().getPackageManager()
                        .getLaunchIntentForPackage( getBaseContext().getPackageName() );
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);

                return true;

            case R.id.action_settings: // choosing another club from list
                clearPrefs();
                finish();
                Intent un = new Intent(this, MainActivity.class);

                startActivity(un);

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void addRssFragment() { //adding fragment to container
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        RssFragment fragment = new RssFragment();
        transaction.add(R.id.fragment_container, fragment);
        transaction.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) { // getting data from CLubsList activity result
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CHOOSE_CLUB) {// check request code
            if (resultCode == RESULT_OK) {// checking if results are passed

                selectedClub = data.getIntExtra("CLUB", 0);// getting number from result
                saveText();

                clubUrl=urls[selectedClub]; //choosing URL from a string
                addRssFragment();
            }

        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("fragment_added", true);
    }
    public void testPreLoadedSQLiteDb() {

        DB db = new DB(this);
        try {
            db.create();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Club> clubs = db.getClubs();
        DBsize=clubs.size();

        // get all clubs
        if (db.open()) {


            Log.d(LOG_TAG, "checked4: works" );
            for (int n = 0; n < clubs.size(); n++) {
                String name = clubs.get(n).name;
                String url = clubs.get(n).url;
                names[n]=name;// populating names string
                urls[n]=url;// populating urls string
            }
        }
    }
    void saveText() { // save num in pref file
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putInt(CHOSEN_CLUB, selectedClub);
        ed.commit();

    }

    void loadText() { //load num from pref file
        sPref = getPreferences(MODE_PRIVATE);
        int savedNum = sPref.getInt(CHOSEN_CLUB,0);
        selectedClub=savedNum;

    }
    void clearPrefs() { // clear pref in case user want to chose another club
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putInt(CHOSEN_CLUB, 0);
        ed.commit();

    }

}