package ru.legonat.sportsnow;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ClubsList extends ActionBarActivity{
    int chosenClub;

    ListView lvMain;


    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selection_layout);




        lvMain = (ListView) findViewById(R.id.lvMain);
        // setting selection mode
        lvMain.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        // creating adapter, populating it with array
        ArrayList<String> clubNames = new ArrayList<String>();
        for (int n =0;n<MainActivity.DBsize;n++){
            clubNames.add(MainActivity.names[n]);

        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_single_choice, clubNames);// creating adapter,that will populate single choice list
        lvMain.setAdapter(adapter);


    }


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.menu_selection, menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);



        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.next:
                chosenClub=lvMain.getCheckedItemPosition();
                Intent answerIntent=new Intent();
                answerIntent.putExtra("CLUB", chosenClub);// pass Extra to MainActivity
                setResult(RESULT_OK, answerIntent);// show, that Extras are put
                finish();// closing activity
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        moveTaskToBack(true);

    }
}