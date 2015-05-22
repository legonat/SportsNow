package ru.legonat.sportsnow;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import ru.legonat.sportsnow.DatabaseReader.Club;
import ru.legonat.sportsnow.DatabaseReader.DB;

public class MainActivity extends ActionBarActivity {
    public static int selectedClub=0;
    SharedPreferences sPref;
    final String CHOSEN_CLUB = "saved_club";
    final String FINISHED_TUTORIAL = "saved_bool";
    public static int DBsize=16;
    public static String[] names= new String[DBsize];
    public static String[] urls= new String[DBsize];
    static final private int CHOOSE_CLUB=0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testPreLoadedSQLiteDb();
        loadText();
        if (Constants.APP_TUTORIAL==0) { // checking if user have already started application
            //if not chosen, loading ScreenSlideActivity
            Intent questionIntent = new Intent(MainActivity.this, ScreenSlideActivity.class);
            startActivityForResult(questionIntent, CHOOSE_CLUB);
        }
        else if (selectedClub==0){// checking if user have already chosen club
            //if not chosen, loading ClubsList
            Intent questionIntent = new Intent(MainActivity.this, ClubsList.class);
            startActivityForResult(questionIntent, CHOOSE_CLUB);
        }
        else {//if club is chosen, loading news
            if (savedInstanceState == null) {
                Constants.RSS_LINK=urls[selectedClub];
                String Name= names[selectedClub];
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

                reloadRssFragment();

                return true;

            case R.id.action_settings: // choosing another club from list
                clearPrefs();
                finish();
                Intent chooseAnother = new Intent(this, MainActivity.class);

                startActivity(chooseAnother);

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
        TextView clubNews=(TextView)findViewById(R.id.clubName);
        clubNews.setText(names[selectedClub] + " club news");
    }
    private void reloadRssFragment() { //reloading fragment
        loadText();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        RssFragment fragment = new RssFragment();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
        TextView clubNews=(TextView)findViewById(R.id.clubName);
        clubNews.setText(names[selectedClub] + " club news");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) { // getting data from CLubsList activity result
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CHOOSE_CLUB) {// check request code
            if (resultCode == RESULT_OK) {// checking if results are passed

                selectedClub = data.getIntExtra("CLUB", 0);// getting number from result
                saveText();
                Constants.APP_TUTORIAL=1;
                Constants.RSS_LINK=urls[selectedClub];
                reloadRssFragment();
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


            for (int n = 0; n < clubs.size(); n++) {
                String name = clubs.get(n).name;
                String url = clubs.get(n).url;
                names[n]=name;// populating names array
                urls[n]=url;// populating urls array
            }
        }
        db.close();
    }
    public void saveText() { // save num in pref file
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putInt(CHOSEN_CLUB, selectedClub);
        ed.putInt(FINISHED_TUTORIAL, 1);
        ed.commit();

    }

    public void loadText() { //load num from pref file
        sPref = getPreferences(MODE_PRIVATE);
        int savedBool = sPref.getInt(FINISHED_TUTORIAL,0);
        int savedNum = sPref.getInt(CHOSEN_CLUB,0);
        selectedClub=savedNum;
        Constants.APP_TUTORIAL=savedBool;

    }
    public void clearPrefs() { // clear pref in case user want to chose another club
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putInt(CHOSEN_CLUB, 0);
        ed.commit();

    }

}