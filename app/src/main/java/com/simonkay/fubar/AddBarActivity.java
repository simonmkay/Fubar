package com.simonkay.fubar;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.EditText;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;


public class AddBarActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bar);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_bar, menu);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_add_bar, container, false);
            return rootView;

        }
    }

    public void submit(View view) {
        final Activity activity = this;

        EditText nameEditText = (EditText) findViewById(R.id.name);
        EditText addressEditText = (EditText) findViewById(R.id.address);
        EditText cityEditText = (EditText) findViewById(R.id.city);
        EditText stateEditText = (EditText) findViewById(R.id.state);
        EditText zipEditText = (EditText) findViewById(R.id.Zip);
        EditText special1EditText = (EditText) findViewById(R.id.Special1);
        EditText special2EditText = (EditText) findViewById(R.id.Special2);
        EditText special3EditText = (EditText) findViewById(R.id.Special3);

        String name = nameEditText.getText().toString();
        String address = addressEditText.getText().toString();
        String city = cityEditText.getText().toString();
        String state = stateEditText.getText().toString();
        String zip = zipEditText.getText().toString();
        String special1 = special1EditText.getText().toString();
        String special2 = special2EditText.getText().toString();
        String special3 = special3EditText.getText().toString();

        ParseObject parseObject = new ParseObject("Bar");
        parseObject.put("Name", name);
        parseObject.put("Address", address);
        parseObject.put("City", city);
        parseObject.put("State", state);
        parseObject.put("Zip", zip);
        parseObject.put("Special1", special1);
        parseObject.put("Special2", special2);
        parseObject.put("Special3", special3);
        parseObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null)
                    return;
                Intent intent = new Intent(activity, BarSearchActivity.class);

                startActivity(intent);
            }
        });
    }
}
