package com.mapbox.mapboxsdk.testapp.activity.camera;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.mapbox.mapboxsdk.style.layers.FillLayer;
import com.mapbox.mapboxsdk.style.sources.TileSet;
import com.mapbox.mapboxsdk.style.sources.VectorSource;

import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.testapp.R;

import static com.mapbox.mapboxsdk.style.expressions.Expression.rgb;

import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.fillColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.fillOpacity;

import timber.log.Timber;

/**
 * Test activity showcasing using maximum and minimum zoom levels to restrict camera movement.
 */
public class MaxMinZoomActivity extends AppCompatActivity implements OnMapReadyCallback {

  private MapView mapView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_maxmin_zoom);

    mapView = findViewById(R.id.mapView);
    mapView.onCreate(savedInstanceState);
    mapView.getMapAsync(this);

    mapView.addOnDidFinishLoadingStyleListener(() -> Timber.d("Style Loaded"));
  }

  @Override
  public void onMapReady(@NonNull final MapboxMap mapboxMap) {
    mapboxMap.setDebugActive(true);
    Style.Builder builder = new Style.Builder()
            .fromUrl("https://cdn.airmap.com/static/map-styles/stage/0.10.0-beta1/standard.json");
    mapboxMap.setStyle(builder, new Style.OnStyleLoaded() {
      @Override
      public void onStyleLoaded(@NonNull Style style) {
        Map<String, String> sourceMap = new HashMap<>();
        sourceMap.put("usa_ama", "ama_field");
        sourceMap.put("usa_fish_wildlife_refuge", "park");
        sourceMap.put("usa_national_marine_sanctuary", "park");
        sourceMap.put("usa_national_park", "park");
        sourceMap.put("usa_sec_91", "emergency,fire,special_use_airspace,tfr,wildfire");
        sourceMap.put("usa_sec_336", "airport,controlled_airspace");
        sourceMap.put("usa_wilderness_area", "park");
        sourceMap.put("usa_airmap_rules", "airport,hospital,power_plant,prison,school");

        for (String source : sourceMap.keySet()) {
          String layers = sourceMap.get(source);

          String urlTemplates = "https://stage.api.airmap.com/tiledata/v1/" + source + "/" + layers + "/{z}/{x}/{y}";
          TileSet tileSet = new TileSet("2.2.0", urlTemplates);
          tileSet.setMaxZoom(12f);
          tileSet.setMinZoom(8f);
          VectorSource tileSource = new VectorSource(source, tileSet);
          style.addSource(tileSource);

          String[] layersArray = layers.split(",");
          for (String layer : layersArray) {
            FillLayer fillLayer = new FillLayer("airmap|" + source + "|" + layer, source)
                    .withProperties(
                            fillOpacity(0.2f),
                            fillColor(rgb(new Random().nextInt(255), new Random().nextInt(255), new Random().nextInt(255)))
                    );
            fillLayer.setSourceLayer(source + "_" + layer);
            style.addLayer(fillLayer);
          }
        }
      }
    });
//    mapboxMap.setStyle(Style.MAPBOX_STREETS);
//    mapboxMap.setMinZoomPreference(3);
//    mapboxMap.setMaxZoomPreference(5);
//    mapboxMap.addOnMapClickListener(clickListener);

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
