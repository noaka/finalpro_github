package com.matan.listit;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapOfCoupons extends FragmentActivity {

    public GoogleMap mMap; // Might be null if Google Play services APK is not available.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_of_coupons);

        Log.i("contectview", "over" );
        createMapView();
        Log.i("creatmapw", "fine" );
        addMarker();
        Log.i("marker", "ok" );
      //  setUpMapIfNeeded();
    }

  //  @Override
  //  protected void onResume() {
  //      super.onResume();
      // setUpMapIfNeeded();
  // }
    private void setUpMap() {
            mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker").snippet("Snippet"));
                        // Enable MyLocation Layer of Google Map
                 mMap.setMyLocationEnabled(true);

                // Get LocationManager object from System Service LOCATION_SERVICE
                 LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                 // Create a criteria object to retrieve provider
                 Criteria criteria = new Criteria();

                // Get the name of the best provider
                 String provider = locationManager.getBestProvider(criteria, true);

                // Get Current Location
                 Location myLocation = locationManager.getLastKnownLocation(provider);

                 // set map type
                 mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                 // Get latitude of the current location
                 double latitude = myLocation.getLatitude();

                 // Get longitude of the current location
                 double longitude = myLocation.getLongitude();

                 // Create a LatLng object for the current location
                 LatLng latLng = new LatLng(latitude, longitude);

                 // Show the current location in Google Map
                 mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

                 // Zoom in the Google Map
                 mMap.animateCamera(CameraUpdateFactory.zoomTo(14));
                 mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("You are here!").snippet("Consider yourself located"));
             }

    private void createMapView(){
        /**
         * Catch the null pointer exception that
         * may be thrown when initialising the map
         */
        try {
            if(null == mMap){
                mMap = ((MapFragment) getFragmentManager().findFragmentById(
                        R.id.map)).getMap();

                /**
                 * If the map is still null after attempted initialisation,
                 * show an error to the user
                 */
                if(null == mMap) {
                    Toast.makeText(getApplicationContext(),
                            "Error creating map", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (NullPointerException exception){
            Log.e("mapApp", exception.toString());
        }
    }

    private void addMarker(){

        /** Make sure that the map has been initialised **/
        if(null != mMap){
            mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(0, 0))
                            .title("here")
                            .draggable(true)
            );
        }
    }
}
