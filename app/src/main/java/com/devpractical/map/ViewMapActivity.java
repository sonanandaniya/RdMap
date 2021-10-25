package com.devpractical.map;


import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.devpractical.R;
import com.devpractical.databinding.ActivityViewMapBinding;
import com.devpractical.room.CityData;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ViewMapActivity extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback {

    GoogleMap gMap;
    List<CityData> locationsList = new ArrayList<>();
    ActivityViewMapBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_map);
        setStatusBarTransparent();
        binding.imgToolbarMainLeftToolbar.setOnClickListener(this);
        locationsList = (List<CityData>) getIntent().getSerializableExtra("locationsList");

        setUpGoogleMap();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_toolbarMain_left_Toolbar:
                finish();

                break;
        }
    }

    private void setUpGoogleMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(ViewMapActivity.this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        //  askPermission();
        Route route = new Route();
        ArrayList<LatLng> locationsListtemp = new ArrayList<>();
        for (int i = 0; i < locationsList.size(); i++) {
            locationsListtemp.add(new LatLng(Double.parseDouble(locationsList.get(i).getLatitude()), Double.parseDouble(locationsList.get(i).getLongitude())));

        }

        gMap.clear();
        route.drawRoute(gMap, ViewMapActivity.this, locationsListtemp, false, "", true);

        for (int i = 0; i < locationsListtemp.size(); i++) {
            gMap.addMarker(new MarkerOptions()
                    .position(locationsListtemp.get(i))
                    .title(locationsList.get(i).getLocationName())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

        }


        gMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                return true;

            }
        });
    }


    public void setStatusBarTransparent() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }


}