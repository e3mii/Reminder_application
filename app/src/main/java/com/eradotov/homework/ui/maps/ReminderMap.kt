package com.eradotov.homework.ui.maps

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.eradotov.homework.Graph
import com.eradotov.homework.util.DefaultSnackbar
import com.eradotov.homework.util.rememberMapViewWithLifecycle
import com.google.accompanist.insets.systemBarsPadding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.ktx.awaitMap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*

@Composable
fun ReminderMap(
    location: String,
    navController: NavController
){
    val mapView = rememberMapViewWithLifecycle()
    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    var locationStr = location
    lateinit var locationArr: List<String>
    lateinit var location: LatLng
    if(locationStr != ","){
        locationArr = locationStr.split(",")
        location = LatLng(locationArr[0].toDouble(), locationArr[1].toDouble())
    }

    Scaffold(
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
        ) {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.secondary.copy(alpha = 0.87f),
            ){
                IconButton(
                    onClick = {
                        navController.popBackStack()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                        tint = MaterialTheme.colors.primary
                    )
                }
                Text(
                    text = "Pick reminder location",
                    color = MaterialTheme.colors.primary,
                    style = MaterialTheme.typography.h6
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
            ) {
                AndroidView({ mapView }) { mapView ->
                    coroutineScope.launch {
                        val map = mapView.awaitMap()
                        map.uiSettings.isZoomControlsEnabled = true

                        if(locationStr != ","){
                            map.moveCamera(
                                CameraUpdateFactory.newLatLngZoom(location, 13.5f)
                            )

                            val markerOptions = MarkerOptions()
                                .title("Reminder")
                                .position(location)
                            map.addMarker(markerOptions)
                        } else {
                            location = LatLng(65.02, 25.47)
                            map.moveCamera(
                                CameraUpdateFactory.newLatLngZoom(location, 13.5f)
                            )
                        }

                        setMapLongClick(map = map, navController = navController)
                    }
                }
            }
        }
    }
}

private fun setMapLongClick(
    map: GoogleMap,
    navController: NavController
){
    var marker: Marker? = null

    map.setOnMapLongClickListener { latlng ->
        val snippet = String.format(
            Locale.getDefault(),
            "Lat: %1$.2f, Lng: %2$.2f",
            latlng.latitude,
            latlng.longitude
        )

        marker?.remove()

        marker = map.addMarker(
            MarkerOptions().position(latlng).title("Reminder location").snippet(snippet)
        ).apply {
            navController.previousBackStackEntry
                ?.savedStateHandle
                ?.set("location_data", latlng)
            //navController.popBackStack()
        }

        Toast.makeText(Graph.appContext,"Position updated: ${latlng.latitude}, ${latlng.longitude}", Toast.LENGTH_SHORT).show()
    }
}