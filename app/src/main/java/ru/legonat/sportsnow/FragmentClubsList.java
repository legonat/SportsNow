package ru.legonat.sportsnow;


import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class FragmentClubsList extends ListFragment {

        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setRetainInstance(true);
        }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayList<String> clubNames = new ArrayList<String>();// creating Array List with CLub names from array names[]
        for (int n = 0; n < MainActivity.DBsize; n++) {
            clubNames.add(MainActivity.names[n]);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_activated_1, clubNames);
        setListAdapter(adapter);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.list_view_layout, null);
    }
    public void onListItemClick(ListView list, View view, int position, long id) {
        super.onListItemClick(list, view, position, id);
        Constants.chosenClub=position;
        Toast.makeText(getActivity(), "Press Next" , Toast.LENGTH_SHORT).show();

    }


    }
