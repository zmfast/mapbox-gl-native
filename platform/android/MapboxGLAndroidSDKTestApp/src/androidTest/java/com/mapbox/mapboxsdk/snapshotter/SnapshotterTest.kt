package com.mapbox.mapboxsdk.snapshotter

import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.testapp.activity.FeatureOverviewActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class MapSnapshotterDefault {

  @JvmField
  @Rule
  var rule = ActivityTestRule(FeatureOverviewActivity::class.java)

  private val latch: CountDownLatch = CountDownLatch(1)

  @Test
  fun snapshotterOverDenseArea() {
    rule.runOnUiThread {
      val options = MapSnapshotter.Options(1024, 1024)
        .withStyle(Style.MAPBOX_STREETS)
      val mapSnapshotter = MapSnapshotter(rule.activity, options)

      mapSnapshotter.start {
        resumeTestExecution()
      }
    }
    holdTestExecution()
  }

  fun holdTestExecution() {
    val timeout = !latch.await(15, TimeUnit.SECONDS)
    if (timeout) {
      throw IllegalStateException()
    }
  }

  fun resumeTestExecution() {
    latch.countDown()
  }
}