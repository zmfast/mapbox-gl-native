package com.mapbox.mapboxsdk.testapp.activity.maplayout

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.testapp.R
import com.redbooth.WelcomeCoordinatorLayout
import kotlinx.android.synthetic.main.activity_wizard.*

class WizardActivity : AppCompatActivity() {

    private var animationReady = false
    private val boston = LatLng(42.361418, -71.060593)
    private val new_york = LatLng(40.723102, -73.997900)
    private var animating = false
    private lateinit var mapboxMap: MapboxMap
    private var currentTarget: LatLng = LatLng(40.723102, -73.997900)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Mapbox.getInstance(
                this,
                "pk.eyJ1IjoidHZubXNrIiwiYSI6ImNqNTVvc2VlZDE3aW4yeGxrdWxhaDB4bG8ifQ.WZquEs3jfhbiujHXkI7BNw"
        )
        setContentView(R.layout.activity_wizard)

        overlain.setOnTouchListener { _, _ -> true }

        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync { map ->
            mapboxMap = map

            map.uiSettings.setAllGesturesEnabled(false)
            map.uiSettings.isCompassEnabled = false
            map.uiSettings.isAttributionEnabled = false
            map.uiSettings.isLogoEnabled = false

            map.cameraPosition = CameraPosition.Builder()
                    .target(LatLng(currentTarget))
                    .zoom(11.0)
                    .build()
            map.setStyle(Style.Builder().fromUrl("mapbox://styles/tvnmsk/cjnrolokd199f2sqmu3bd11qt")) { style ->
            }
        }

        initializeListeners()
        initializePages()
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.let {
            mapView.onSaveInstanceState(it)
        }
    }

    private fun initializePages() {
        coordinator.addPage(
                R.layout.welcome_page_1,
                R.layout.welcome_page_2,
                R.layout.welcome_page_3,
                R.layout.welcome_page_4
        )
    }

    private fun initializeListeners() {
        coordinator.setOnPageScrollListener(object : WelcomeCoordinatorLayout.OnPageScrollListener {
            override fun onScrollPage(v: View, progress: Float, maximum: Float) {
                if (!animationReady) {
                    animationReady = true
                }

                if (animating) {
                    return
                }


                val screenAmount = 4
                val offset = progress / maximum
                val zoom = 11.0 + (screenAmount * offset)
                val tilt = (15 * screenAmount * offset).toDouble()
                val bearing = 18.5 * screenAmount * offset
                val position =
                        CameraPosition.Builder().target(currentTarget).zoom(zoom).bearing(bearing).tilt(tilt).build()
                Log.e("TAG", position.toString())
                mapboxMap.moveCamera(
                        CameraUpdateFactory.newCameraPosition(
                                position
                        )
                )
            }

            override fun onPageSelected(v: View, pageSelected: Int) {
                when (pageSelected) {
                    1 -> if (currentTarget == boston) {
                        coordinator.setScrollingEnabled(false)
                        mapboxMap.animateCamera(
                                CameraUpdateFactory.newCameraPosition(
                                        CameraPosition.Builder()
                                                .target(LatLng(new_york))
                                                .zoom(12.330864191055298)
                                                .tilt(19.962963104248047)
                                                .bearing(24.62098753452301)
                                                .build()
                                ), 2750, object : MapboxMap.CancelableCallback {
                            override fun onFinish() {
                                currentTarget = new_york
                                animating = false
                                coordinator.setScrollingEnabled(true)
                            }

                            override fun onCancel() {
                            }

                        }
                        )
                    }

                    2 -> if (currentTarget == new_york) {
                        coordinator.setScrollingEnabled(false)
                        animating = true
                        mapboxMap.animateCamera(
                                CameraUpdateFactory.newCameraPosition(
                                        CameraPosition.Builder()
                                                .target(boston)
                                                .zoom(13.658024787902832)
                                                .tilt(39.8703727722168)
                                                .bearing(49.17345857620239)
                                                .build()

                                ), 4850, object : MapboxMap.CancelableCallback {
                            override fun onFinish() {
                                currentTarget = boston
                                animating = false
                                coordinator.setScrollingEnabled(true)
                            }

                            override fun onCancel() {
                                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                            }
                        }
                        )
                    }
                }
            }
        })
    }
}
