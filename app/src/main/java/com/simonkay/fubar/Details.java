package com.simonkay.fubar;

import android.content.Intent;
import android.location.Address;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.location.Geocoder;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import org.w3c.dom.Text;
import java.io.IOException;
import java.util.List;
import java.util.logging.Handler;
import java.io.IOException;


public class Details extends ActionBarActivity implements OnMapReadyCallback {

    static TextView textViewName;
    static TextView textViewStreet;
    static TextView textViewCity;
    static TextView textViewState;
    static TextView textViewZip;
    static TextView textViewSpecial1;
    static TextView textViewSpecial2;
    static TextView textViewSpecial3;
    GoogleMap map;
    Geocoder geocoder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        textViewName = (TextView)findViewById(R.id.BarName);
        textViewStreet = (TextView)findViewById(R.id.Street);
        textViewCity = (TextView)findViewById(R.id.City);
        textViewState = (TextView)findViewById(R.id.State);
        textViewZip = (TextView)findViewById(R.id.Zip);
        textViewSpecial1 = (TextView) findViewById(R.id.Special1);
        textViewSpecial2 = (TextView) findViewById(R.id.Special2);
        textViewSpecial3 = (TextView) findViewById(R.id.Special3);
        geocoder = new Geocoder(getBaseContext());
        Intent intent = getIntent();
        String barName = intent.getStringExtra("BarKey");
        //Log.d("Bar Name", barName);
        map = ((SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map)).getMap();


        ParseQuery<ParseObject> query = ParseQuery.getQuery("Bar");
        query.whereEqualTo("objectId", barName);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
               if (e == null) {
                   Log.d("Result",parseObject.getString("Name"));
                   textViewName.setText(parseObject.getString("Name"));
                   textViewStreet.setText(parseObject.getString("Address"));
                   textViewCity.setText(parseObject.getString("City"));
                   textViewState.setText(parseObject.getString("State"));
                   textViewZip.setText(parseObject.getString("Zip"));
                   textViewSpecial1.setText(parseObject.getString("Special1"));
                   textViewSpecial2.setText(parseObject.getString("Special2"));
                   textViewSpecial3.setText(parseObject.getString("Special3"));


                   if(geocoder.isPresent() && parseObject.getString("Address") != "")
                   {
                       try {
                           List<Address> addressList = geocoder.getFromLocationName(parseObject.getString("Address"),1);
                           Address address = addressList.get(0);
                           double lat = address.getLatitude();
                           double lng = address.getLongitude();
                           LatLngBounds.Builder builder = new LatLngBounds.Builder();
                           builder.include(new LatLng(lat,lng));
                           map.addMarker(new MarkerOptions().position(new LatLng(lat,lng)) );
                           LatLngBounds bounds = builder.build();
                           int padding = 0;
                           CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
                           map.moveCamera(cu);
                           map.animateCamera(cu);
                       } catch (IOException e1) {
                           e1.printStackTrace();
                       }
                   }

               }
            }

        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_details, menu);
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

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
}
