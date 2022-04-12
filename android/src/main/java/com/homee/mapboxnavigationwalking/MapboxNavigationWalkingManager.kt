package com.homee.mapboxnavigationwalking

import android.content.pm.PackageManager
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReadableArray
import com.facebook.react.common.MapBuilder
import com.facebook.react.uimanager.SimpleViewManager
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.annotations.ReactProp
import com.mapbox.geojson.Point
import com.mapbox.maps.ResourceOptionsManager
import com.mapbox.maps.TileStoreUsageMode
import javax.annotation.Nonnull

class MapboxNavigationWalkingManager(var mCallerContext: ReactApplicationContext) : SimpleViewManager<MapboxNavigationWalkingView>() {
    private var accessToken: String? = null

    init {
        mCallerContext.runOnUiQueueThread {
            try {
                val app = mCallerContext.packageManager.getApplicationInfo(mCallerContext.packageName, PackageManager.GET_META_DATA)
                val bundle = app.metaData
                val accessToken = bundle.getString("MAPBOX_ACCESS_TOKEN")
                this.accessToken = accessToken
                ResourceOptionsManager.getDefault(mCallerContext, accessToken).update {
                    tileStoreUsageMode(TileStoreUsageMode.READ_ONLY)
                }
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }
        }
    }

    override fun getName(): String {
        return "MapboxNavigationWalking"
    }

    public override fun createViewInstance(@Nonnull reactContext: ThemedReactContext): MapboxNavigationWalkingView {
        return MapboxNavigationWalkingView(reactContext, this.accessToken)
    }

    override fun onDropViewInstance(view: MapboxNavigationWalkingView) {
        view.onDropViewInstance()
        super.onDropViewInstance(view)
    }

    override fun getExportedCustomDirectEventTypeConstants(): MutableMap<String, Map<String, String>>? {
        return MapBuilder.of<String, Map<String, String>>(
                "onLocationChange", MapBuilder.of("registrationName", "onLocationChange"),
                "onError", MapBuilder.of("registrationName", "onError"),
                "onCancelNavigation", MapBuilder.of("registrationName", "onCancelNavigation"),
                "onArrive", MapBuilder.of("registrationName", "onArrive"),
                "onRouteProgressChange", MapBuilder.of("registrationName", "onRouteProgressChange"),
        )
    }

    @ReactProp(name = "origin")
    fun setOrigin(view: MapboxNavigationWalkingView, sources: ReadableArray?) {
        if (sources == null) {
            view.setOrigin(null)
            return
        }
        view.setOrigin(Point.fromLngLat(sources.getDouble(0), sources.getDouble(1)))
    }

    @ReactProp(name = "destination")
    fun setDestination(view: MapboxNavigationWalkingView, sources: ReadableArray?) {
        if (sources == null) {
            view.setDestination(null)
            return
        }
        view.setDestination(Point.fromLngLat(sources.getDouble(0), sources.getDouble(1)))
    }

    @ReactProp(name = "shouldSimulateRoute")
    fun setShouldSimulateRoute(view: MapboxNavigationWalkingView, shouldSimulateRoute: Boolean) {
        view.setShouldSimulateRoute(shouldSimulateRoute)
    }

    @ReactProp(name = "showsEndOfRouteFeedback")
    fun setShowsEndOfRouteFeedback(view: MapboxNavigationWalkingView, showsEndOfRouteFeedback: Boolean) {
        view.setShowsEndOfRouteFeedback(showsEndOfRouteFeedback)
    }

    @ReactProp(name = "mute")
    fun setMute(view: MapboxNavigationWalkingView, mute: Boolean) {
        view.setMute(mute)
    }
}
