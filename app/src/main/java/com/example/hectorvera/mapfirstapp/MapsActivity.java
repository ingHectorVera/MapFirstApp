package com.example.hectorvera.mapfirstapp;

import android.app.Dialog;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final int GS_REQUEST_CODE = 9001;
    private static final float DEFAULTZOOM = 18;
    private SupportMapFragment mapFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_maps);

        if(servicesOK()) {
            setContentView(R.layout.activity_maps);
            if(initMap()){
                Toast.makeText(this, "Ready to map", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Map not available", Toast.LENGTH_SHORT).show();
            }
        }else{
            //setContentView(R.layout.activity_main);
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override

    public void onMapReady(GoogleMap googleMap) {
        gotoLocation(19.491903, -99.135972, DEFAULTZOOM, googleMap);
    }

    public boolean servicesOK(){
        GoogleApiAvailability gaa = GoogleApiAvailability.getInstance();
        int isAvailable =  gaa.isGooglePlayServicesAvailable(this);

        if(isAvailable == ConnectionResult.SUCCESS){
            return true;
        }else if(gaa.isUserResolvableError(isAvailable)) {
            Dialog dialog = gaa.getErrorDialog(this,isAvailable,  GS_REQUEST_CODE);
            dialog.show();
        }else {
            Toast.makeText(this, "Can't connect to Google Play services", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    private boolean initMap(){
        if(mapFragment == null){
            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }
        return (mapFragment != null);
    }

    private void gotoLocation(double lat, double lng, float zoom ,GoogleMap googleMap){
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng mexico = new LatLng(lat, lng);
        mMap.addMarker(new MarkerOptions().position(mexico).title("Marker in Mexico"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(mexico));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mexico,zoom));

    }
}
