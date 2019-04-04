package com.mapbox.mapboxsdk.testapp.activity.style

import android.graphics.Camera
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.style.expressions.Expression.get
import com.mapbox.mapboxsdk.style.layers.LineLayer
import com.mapbox.mapboxsdk.style.layers.PropertyFactory.*
import com.mapbox.mapboxsdk.style.sources.GeoJsonOptions
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource
import com.mapbox.mapboxsdk.testapp.R
import timber.log.Timber
import java.io.IOException

/**
 * Activity showcasing applying a gradient coloring to a line layer.
 */
class LineActivity : AppCompatActivity(), OnMapReadyCallback {
  private var mapView: MapView? = null

  public override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_gradient_line)

    mapView = findViewById(R.id.mapView)
    mapView!!.onCreate(savedInstanceState)
    mapView!!.getMapAsync(this)
  }

  override fun onMapReady(mapboxMap: MapboxMap) {
    try {
      mapboxMap.cameraPosition = CameraPosition.DEFAULT
      mapboxMap.setStyle(Style.Builder()
        .fromUrl(Style.MAPBOX_STREETS)
        .withSource(GeoJsonSource(LINE_SOURCE, GEOJSON, GeoJsonOptions()))
        .withLayer(LineLayer("line", LINE_SOURCE)
          .withProperties(
            lineColor(get("line-color")),
            lineWidth(get("line-width")),
            linePattern(get("line-pattern"))
          )
        )
      )
    } catch (exception: IOException) {
      Timber.e(exception)
    }

  }

  override fun onStart() {
    super.onStart()
    mapView!!.onStart()
  }

  override fun onResume() {
    super.onResume()
    mapView!!.onResume()
  }

  override fun onPause() {
    super.onPause()
    mapView!!.onPause()
  }

  override fun onStop() {
    super.onStop()
    mapView!!.onStop()
  }

  public override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    mapView!!.onSaveInstanceState(outState)
  }

  override fun onLowMemory() {
    super.onLowMemory()
    mapView!!.onLowMemory()
  }

  public override fun onDestroy() {
    super.onDestroy()
    mapView!!.onDestroy()
  }

  companion object {

    val LINE_SOURCE = "gradient"

    val GEOJSON =
      """
{
	"type": "FeatureCollection",
	"features": [{
			"type": "Feature",
			"properties": {
				"line-pattern": "airfield-11",
				"line-color": "#ff0",
				"line-width": 5.0
			},
			"geometry": {
				"type": "LineString",
				"coordinates": [
					[
						0,
						90
					],
					[
						0,
						-90
					]
				]
			}
		},
		{
			"type": "Feature",
			"properties": {
				"line-pattern": "",
				"line-color": "#ff0",
				"line-width": 5.0
			},
			"geometry": {
				"type": "LineString",
				"coordinates": [
					[
						15,
						90
					],
					[
						15,
						-90
					]
				]
			}
		}
	]
}
      """


  }
}
