package com.mapbox.mapboxsdk.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

import java.util.List;
import java.util.Random;

public class MainActivity extends Activity implements OnMapReadyCallback {

  private MapView mapView;
  private LocationComponent locationComponent;
  private LocationEngine locationEngine;
  private PermissionsManager permissionsManager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    locationEngine = LocationEngineProvider.getBestLocationEngine(this, false);

    Button fabManualUpdate = findViewById(R.id.button);
    fabManualUpdate.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (locationComponent != null && locationComponent.getLocationEngine() == null) {
          locationComponent.forceLocationUpdate(
            getRandomLocation(LatLngBounds.from(60, 25, 40, -5)));
        }
      }
    });

    mapView = findViewById(R.id.mapView);
    mapView.onCreate(savedInstanceState);

    if (PermissionsManager.areLocationPermissionsGranted(this)) {
      mapView.getMapAsync(this);
    } else {
      permissionsManager = new PermissionsManager(new PermissionsListener() {
        @Override
        public void onExplanationNeeded(List<String> permissionsToExplain) {
          Toast.makeText(MainActivity.this.getApplicationContext(),
            "You need to accept location permissions.",
            Toast.LENGTH_SHORT
          ).show();
        }

        @Override
        public void onPermissionResult(boolean granted) {
          if (granted) {
            mapView.getMapAsync(MainActivity.this);
          } else {
            finish();
          }
        }
      });
      permissionsManager.requestLocationPermissions(this);
    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
  }

  @SuppressLint("MissingPermission")
  @Override
  public void onMapReady(final MapboxMap mapboxMap) {
    mapboxMap.setStyle(new Style.Builder().fromUrl(Style.MAPBOX_STREETS), new Style.OnStyleLoaded() {
      @Override
      public void onStyleLoaded(Style style) {
        locationComponent = mapboxMap.getLocationComponent();

        locationComponent.activateLocationComponent(
          LocationComponentActivationOptions
            .builder(MainActivity.this, style)
            .locationEngine(locationEngine)
            .locationEngineRequest(new LocationEngineRequest.Builder(500)
              .setFastestInterval(500)
              .setPriority(LocationEngineRequest.PRIORITY_HIGH_ACCURACY).build())
            .build());

        locationComponent.setLocationComponentEnabled(true);
        locationComponent.setRenderMode(RenderMode.COMPASS);
      }
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

  public static Location getRandomLocation(LatLngBounds bounds) {
    Random random = new Random();

    double randomLat = bounds.getLatSouth() + (bounds.getLatNorth() - bounds.getLatSouth()) * random.nextDouble();
    double randomLon = bounds.getLonWest() + (bounds.getLonEast() - bounds.getLonWest()) * random.nextDouble();

    Location location = new Location("random-loc");
    location.setLongitude(randomLon);
    location.setLatitude(randomLat);
    return location;
  }
}