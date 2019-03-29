package com.mapbox.mapboxsdk.testapp.activity.location;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.android.core.location.LocationEngineRequest;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.mapboxsdk.geometry.LatLngBounds;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.testapp.R;

import java.util.List;

public class ManualLocationUpdatesActivity extends AppCompatActivity implements OnMapReadyCallback {

  private MapView mapView;
  private LocationComponent locationComponent;
  private LocationEngine locationEngine;
  private PermissionsManager permissionsManager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_location_manual_update);
    mapView = findViewById(R.id.mapView);
    mapView.onCreate(savedInstanceState);
    mapView.getMapAsync(this);
  }

  @SuppressLint("MissingPermission")
  @Override
  public void onMapReady(@NonNull MapboxMap mapboxMap) {
    mapboxMap.setStyle(new Style.Builder().fromUrl(Style.MAPBOX_STREETS), style -> {
//      locationComponent = mapboxMap.getLocationComponent();
//
//      locationComponent.activateLocationComponent(
//        LocationComponentActivationOptions
//          .builder(this, style)
//          .locationEngine(locationEngine)
//          .locationEngineRequest(new LocationEngineRequest.Builder(500)
//            .setFastestInterval(500)
//            .setPriority(LocationEngineRequest.PRIORITY_HIGH_ACCURACY).build())
//          .build());
//
//      locationComponent.setLocationComponentEnabled(true);
//      locationComponent.setRenderMode(RenderMode.COMPASS);
    });
  }

  @Override
  protected void onStart() {
    super.onStart();
    mapView.onStart();
  }

  @Override
  protected void onResume() {
    super.onResume();
    mapView.onResume();
  }

  @Override
  protected void onPause() {
    super.onPause();
    mapView.onPause();
  }

  @Override
  protected void onStop() {
    super.onStop();
    mapView.onStop();
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    mapView.onSaveInstanceState(outState);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    mapView.onDestroy();
  }

  @Override
  public void onLowMemory() {
    super.onLowMemory();
    mapView.onLowMemory();
  }
}