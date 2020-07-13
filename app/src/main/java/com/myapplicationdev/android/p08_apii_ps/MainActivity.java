package com.myapplicationdev.android.p08_apii_ps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity {

    private GoogleMap map;
    Spinner spn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spn = findViewById(R.id.spinner);

        FragmentManager fm = getSupportFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment)
                fm.findFragmentById(R.id.map);

        mapFragment.getMapAsync(new OnMapReadyCallback(){
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
            }
        });

        if (map != null){
            map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }

        mapFragment.getMapAsync(new OnMapReadyCallback(){
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;

                final LatLng BN = new LatLng(1.461708, 103.813500);

                final LatLng BC = new LatLng(1.300542, 103.841226);

                final LatLng BE = new LatLng(1.350057, 103.934452);

                final LatLng SG = new LatLng(1.3521, 103.8198);

                UiSettings ui = map.getUiSettings();
                ui.setCompassEnabled(true);

                UiSettings ui2 = map.getUiSettings();
                ui2.setZoomControlsEnabled(true);

                int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION);

                if (permissionCheck == PermissionChecker.PERMISSION_GRANTED) {
                    map.setMyLocationEnabled(true);
                } else {
                    Log.e("GMap - Permission", "GPS access has not been granted");
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
                }

                final Marker n = map.addMarker(new
                        MarkerOptions()
                        .position(BN)
                        .title("North - HQ")
                        .snippet("Block 333, Admiralty Ave 3, 765654 ")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

                final Marker c = map.addMarker(new
                        MarkerOptions()
                        .position(BC)
                        .title("Central")
                        .snippet("Block 3A, Orchard Ave 3, 134542")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

                Marker e = map.addMarker(new
                        MarkerOptions()
                        .position(BE)
                        .title("East")
                        .snippet("Block 555, Tampines Ave 3, 287788")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));

                map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        if (marker.equals(n)) {
                            Toast.makeText(MainActivity.this, "North - HQ", Toast.LENGTH_SHORT).show();
                        } else if (marker.equals(c)) {
                            Toast.makeText(MainActivity.this, "Central", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "East", Toast.LENGTH_SHORT).show();
                        }
                        return false;
                    }
                });

                spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        String selected = spn.getSelectedItem().toString();

                        if (selected.contentEquals("North")) {
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(BN,
                                    15));
                        } else if (selected.contentEquals("Central")) {
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(BC,
                                    15));
                        } else if (selected.contentEquals("East")){
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(BE,
                                    15));
                        } else {
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(SG,
                                    11));
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
        });
    }
}
