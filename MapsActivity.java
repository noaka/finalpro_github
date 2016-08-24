package com.matan.listit;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;


public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    protected Context context;
    TextView txtLat;
    String lat;
    String provider;
    protected String latitude,longitude;
    protected boolean gps_enabled,network_enabled;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "siV3hDDGjBNayXYBEIg1XBv8G7s6LTA1p5clPycJ", "T6QQLhOV7Uplc1xWsq1FIQCSJEEyOJwax2QWrTSu");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        try {
            setUpMapIfNeeded();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //   locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
     //   locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) this);
     //   txtLat = (TextView) findViewById(R.id.latlongLocation);
    //    Maploc();
    }

    @Override
    protected void onResume() {
        super.onResume();
      //  setUpMapIfNeeded();
    }
    /*
    private void Maploc(){
    SupportMapFragment supportMapFragment =
            (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mMap = supportMapFragment.getMap();
        mMap.setMyLocationEnabled(true);
    LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
    Criteria criteria = new Criteria();
    String bestProvider = locationManager.getBestProvider(criteria, true);
    Location location = locationManager.getLastKnownLocation(bestProvider);
    if (location != null) {
        onLocationChanged(location);
    }
    locationManager.requestLocationUpdates(bestProvider, 20000, 0, (android.location.LocationListener) this);
}



    public void onLocationChanged(Location location) {
        TextView locationTv = (TextView) findViewById(R.id.latlongLocation);
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        LatLng latLng = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(latLng));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        locationTv.setText("Latitude:" + latitude + ", Longitude:" + longitude);
    }
    //
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub
    }

   // @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub
    }

  //  @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub
    }

    private boolean isGooglePlayServicesAvailable() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {
            GooglePlayServicesUtil.getErrorDialog(status, this, 0).show();
            return false;
        }

    }


    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */

    private void setUpMap() throws ParseException {
       // mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Here").snippet("snnipet"));
        mMap.setMyLocationEnabled(true);
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, true);
        Location myLocation = locationManager.getLastKnownLocation(provider);
        Log.i("location", String.valueOf(myLocation));
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        double latitude = myLocation.getLatitude();
        double longitude = myLocation.getLongitude();
        LatLng latLng = new LatLng(latitude, longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(14));
        BitmapDescriptor bitmapDescriptor
                = BitmapDescriptorFactory.defaultMarker(
                BitmapDescriptorFactory.HUE_AZURE);
        mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).icon(bitmapDescriptor).title("You are here!"));
Log.i("latitude", String.valueOf(latitude));
        Log.i("longitude", String.valueOf(longitude));
     //  ParseGeoPoint point = new ParseGeoPoint(latitude, longitude);
       // ParseObject object = new ParseObject("Core");
        //object.put("location", point);
        //object.save();

    //    ParseGeoPoint point = new ParseGeoPoint(31.67984000, 34.57624449);
     //   ParseObject object = new ParseObject("PlaceObject");
     //   object.put("location", point);
     //   object.save();

        ParseGeoPoint userLocation = new ParseGeoPoint(latitude,longitude);
        ParseObject corecop = new ParseObject("Core");
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Core");
        query.whereNear("location", userLocation);
        query.setLimit(10);
        query.findInBackground(new FindCallback<ParseObject>() {
            ParseGeoPoint copLocation = new ParseGeoPoint();
            public void done(List<ParseObject> cop, ParseException e) {
                if (e == null) {
                    if(!cop.isEmpty()){
                    // iterate over all messages and delete them
                    for (ParseObject invite : cop) {
                        copLocation=invite.getParseGeoPoint("location");
                        double latitudecop=copLocation.getLatitude();
                        double longitudecop = copLocation.getLongitude();
                    //   if (invite.getString("name")=="mega") {
                           mMap.addMarker(new MarkerOptions().position(new LatLng(latitudecop, longitudecop)).title(invite.getString("name")).snippet("Cereal, Honey"));

                         //  mMap.addMarker(new MarkerOptions().position(new LatLng(latitudecop, longitudecop)).title(invite.getString("name")).snippet("Cola"));
                           //   if (invite.getString("name")=="victory")
                           //    mMap.addMarker(new MarkerOptions().position(new LatLng(latitudecop, longitudecop)).title(invite.getString("name")).snippet("Cola"));






                        invite.getString("name");
                      //  Toast.makeText(MapsActivity.this, invite.getString("name"), Toast.LENGTH_LONG).show();
                    }
                } else {
                    //Handle condition here
                }
            }}


        });
     //   mMap.addMarker(new MarkerOptions().position(new LatLng(latitudecop, longitudecop)).title("You are here!"));

    }

    //

    private void setUpMapIfNeeded() throws ParseException {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    public void onLocationChanged(Location location) {
        txtLat = (TextView) findViewById(R.id.latlongLocation);
        txtLat.setText("Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());
    }


    public void onProviderDisabled(String provider) {
        Log.d("Latitude","disable");
    }


    public void onProviderEnabled(String provider) {
        Log.d("Latitude", "enable");
    }


    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude","status");
    }




}
