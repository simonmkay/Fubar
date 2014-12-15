package com.simonkay.fubar;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;




public class BarListActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_list);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bar_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == R.id.action_add) {
            Intent intent = new Intent(this, AddBarActivity.class);
            startActivity(intent);
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_bar_list, container, false);

            Activity activity = getActivity();

            Intent intent = activity.getIntent();
            final String city = intent.getStringExtra("City");

            ParseQueryAdapter.QueryFactory<ParseObject> factory = new ParseQueryAdapter.QueryFactory<ParseObject>() {
                        public ParseQuery create() {
                            ParseQuery query = new ParseQuery("Bar");
                            query.whereContains("City", city);
                            query.orderByAscending("City");
                            return query;
                        }
                    };

            final ParseQueryAdapter parseQueryAdapter = new ParseQueryAdapter(activity, factory);
            parseQueryAdapter.setTextKey("Name");

            ListView barsListView = (ListView) rootView.findViewById(R.id.bars);
            barsListView.setAdapter(parseQueryAdapter);

            ListView listView = (ListView) rootView.findViewById(R.id.bars);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    ParseObject bar = parseQueryAdapter.getItem(position);
                    Intent intent = new Intent (view.getContext(), Details.class);
                    intent.putExtra("BarKey", bar.getObjectId());
                    startActivity(intent);

                }
            });

            return rootView;


        }



    }
}
