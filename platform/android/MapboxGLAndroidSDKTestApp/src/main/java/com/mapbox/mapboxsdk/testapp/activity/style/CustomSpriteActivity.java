package com.mapbox.mapboxsdk.testapp.activity.style;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.google.gson.JsonObject;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.expressions.Expression;
import com.mapbox.mapboxsdk.style.layers.Layer;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.mapboxsdk.testapp.R;
import com.mapbox.mapboxsdk.utils.BitmapUtils;

import java.util.ArrayList;
import java.util.List;

import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.*;

/**
 * Test activity showcasing adding a sprite image and use it in a Symbol Layer
 */
public class CustomSpriteActivity extends AppCompatActivity {

  private GeoJsonSource source;
  private MapboxMap mapboxMap;
  private MapView mapView;
  private Layer layer;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_sprite);

    mapView = findViewById(R.id.mapView);
    mapView.onCreate(savedInstanceState);
    mapView.getMapAsync(map -> {
      mapboxMap = map;

      map.setStyle(Style.MAPBOX_STREETS, style -> {
        List<Feature> featureList = new ArrayList<>();

        LatLng[] latLngs = new LatLng[] {
          new LatLng(52.518635, 13.400857),
          new LatLng(52.518485, 13.401109),
          new LatLng(52.518666, 13.400852),
          new LatLng(52.518660, 13.401920),
          new LatLng(52.518751, 13.401626)
        };

        for (int i = 0; i < latLngs.length; i++) {
          JsonObject jsonObject = new JsonObject();
          jsonObject.addProperty("title", String.valueOf(i));
          jsonObject.addProperty("id", i);
          featureList.add(Feature.fromGeometry(Point.fromLngLat(latLngs[i].getLongitude(), latLngs[i].getLatitude()), jsonObject));
        }


        source = new GeoJsonSource("source", FeatureCollection.fromFeatures(featureList));
        style.addSource(source);

        style.addImage("ic_launcher_round", BitmapUtils.getDrawableFromRes(getApplicationContext(), R.drawable.ic_launcher_round));

        style.addLayer(new SymbolLayer("layer", "source")
          .withProperties(
            iconAllowOverlap(true),
            textAllowOverlap(true),
            iconIgnorePlacement(true),
            textIgnorePlacement(true),
            textPadding(4.0f),
            symbolSortKey(Expression.get("id")),
            textField(Expression.get("title")),
            iconImage("ic_launcher_round")
          )
        );
      });
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
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    mapView.onSaveInstanceState(outState);
  }

  @Override
  public void onLowMemory() {
    super.onLowMemory();
    mapView.onLowMemory();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    mapView.onDestroy();
  }
}
