package com.eradotov.homework.ui.maps

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color.argb
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Info
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.eradotov.homework.Graph
import com.eradotov.homework.R
import com.eradotov.homework.data.entity.Reminder
import com.eradotov.homework.util.rememberMapViewWithLifecycle
import com.eradotov.homework.util.viewModelProviderFactoryOf
import com.google.accompanist.insets.systemBarsPadding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import com.google.maps.android.ktx.awaitMap
import kotlinx.coroutines.launch
import java.util.*


@Composable
fun SearchRemindersMap(
    navController: NavController,
    userId: Long
){
    val mapViewModel: MapViewModel = viewModel(
        key = "user_id_$userId",
        factory = viewModelProviderFactoryOf { MapViewModel(userId) }
    )
    val mapViewSate by mapViewModel.state.collectAsState()

    val mapView = rememberMapViewWithLifecycle()
    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    var showMe = rememberSaveable{ mutableStateOf(false) }

    Scaffold(
        scaffoldState = scaffoldState
    ) {
        if(showMe.value){
            AlertDialog(
                onDismissRequest = {},
                title = {
                    Text(
                        text = "DEMO info",
                        color = MaterialTheme.colors.primary
                    )
                },
                text = {
                    Column{
                        Text(
                            text = "Welcome to moving simulation feature!",
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "1) How it works?",
                            color = Color.Black,
                            fontStyle = FontStyle.Italic,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(1.dp))
                        Text(
                            text = "By clicking desired location and then " +
                                    "application returns all reminders in specific radius, " +
                                    "and it notifies you about ones which have notification turned on.",
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "2) Why is here?",
                            color = Color.Black,
                            fontStyle = FontStyle.Italic,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(1.dp))
                        Text(
                            text = "This feature is for moving testing, because of emulator problems, " +
                                    "as well for the finding near reminder testing.",
                            color = Color.Black
                        )
                    }
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            showMe.value = !showMe.value
                        }
                    ) {
                        Text(
                            text = "OK",
                            color = MaterialTheme.colors.primary
                        )
                    }
                },
                backgroundColor = Color.White
            )
        }
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
                    text = "Displaying reminders (DEMO)",
                    color = MaterialTheme.colors.primary,
                    style = MaterialTheme.typography.h6
                )
                IconButton(
                    onClick = {
                        showMe.value = !showMe.value
                    }){
                    Icon(
                        imageVector = Icons.Outlined.Info,
                        contentDescription = stringResource(R.string.account),
                        tint = MaterialTheme.colors.primary
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
            ) {
                AndroidView({ mapView }) { mapView ->
                    coroutineScope.launch {
                        val location = LatLng(65.02, 25.47)
                        val map = mapView.awaitMap()
                        map.uiSettings.isZoomControlsEnabled = true

                        map.clear()

                        map.moveCamera(
                            CameraUpdateFactory.newLatLngZoom(location, 13.5f)
                        )

                        setMapClick(map = map, navController = navController, list = mapViewSate.reminders)
                    }
                }
            }
        }
    }
}

private fun setMapClick(
    map: GoogleMap,
    navController: NavController,
    list: List<Reminder>
){
    var marker: Marker? = null
    var circle: Circle? = null

    map.setOnMapClickListener { latlng ->
        map.clear()
        var remindersToNotify = 0
        for(reminder in list){
            if(distance(latlng.latitude, reminder.rLocationX, latlng.longitude, reminder.rLocationY) <= 0.4){
                if(reminder.toNotify){
                    remindersToNotify++
                    map.addMarker(
                        MarkerOptions()
                            .title("Reminder in reach")
                            .position(LatLng(reminder.rLocationX, reminder.rLocationY))
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)))
                } else {
                    map.addMarker(
                        MarkerOptions()
                            .title("Reminder in reach")
                            .position(LatLng(reminder.rLocationX, reminder.rLocationY)))
                }
            }
        }

        if(remindersToNotify != 0){
            reminderInReachNotification(remindersToNotify)
        }

        val snippet = String.format(
            Locale.getDefault(),
            "Hello!",
            latlng.latitude,
            latlng.longitude
        )

        marker?.remove()
        circle?.remove()

        marker = map.addMarker(
            MarkerOptions().position(latlng).title("Your DEMO location").snippet(snippet).icon(
                bitmapFromVector(Graph.appContext, R.drawable.custom_marker_map)
            )
        )

        circle = map.addCircle(
            CircleOptions()
                .center(latlng)
                .radius(400.0)
                .strokeColor(argb(255, 255, 0, 0))
                .fillColor(argb(50, 255, 0, 0))
                .strokeWidth(5.0F)
        )
    }
}

private fun bitmapFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
    // below line is use to generate a drawable.
    val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)

    // below line is use to set bounds to our vector drawable.
    vectorDrawable!!.setBounds(0, 0, vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight)

    // below line is use to create a bitmap for our
    // drawable which we have added.
    val bitmap = Bitmap.createBitmap(
        vectorDrawable.intrinsicWidth,
        vectorDrawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )

    // below line is use to add bitmap in our canvas.
    val canvas = Canvas(bitmap)

    // below line is use to draw our
    // vector drawable in canvas.
    vectorDrawable.draw(canvas)

    // after generating our bitmap we are returning our bitmap.
    return BitmapDescriptorFactory.fromBitmap(bitmap)
}

fun distance(
    lat1: Double,
    lat2: Double, lon1: Double,
    lon2: Double
): Double {

    // The math module contains a function
    // named toRadians which converts from
    // degrees to radians.
    var lat1 = lat1
    var lat2 = lat2
    var lon1 = lon1
    var lon2 = lon2
    lon1 = Math.toRadians(lon1)
    lon2 = Math.toRadians(lon2)
    lat1 = Math.toRadians(lat1)
    lat2 = Math.toRadians(lat2)

    // Haversine formula
    val dlon = lon2 - lon1
    val dlat = lat2 - lat1
    val a = (Math.pow(Math.sin(dlat / 2), 2.0)
            + (Math.cos(lat1) * Math.cos(lat2)
            * Math.pow(Math.sin(dlon / 2), 2.0)))
    val c = 2 * Math.asin(Math.sqrt(a))

    // Radius of earth in kilometers. Use 3956
    // for miles
    val r = 6371.0

    // calculate the result
    return c * r
}

private fun reminderInReachNotification(
    numberOfReminders: Int
){
    val notificationId = 5
    if(numberOfReminders == 1){
        val builder = NotificationCompat.Builder(Graph.appContext, "REMINDERS_CHANNEL_ID")
            .setSmallIcon(R.drawable.ic_baseline_my_location_24)
            .setContentTitle("Reminders in reach")
            .setContentText("You are near ${numberOfReminders} important reminder!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_REMINDER)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
        with(NotificationManagerCompat.from(Graph.appContext)){
            notify(notificationId, builder.build())
        }
    } else {
        val builder = NotificationCompat.Builder(Graph.appContext, "REMINDERS_CHANNEL_ID")
            .setSmallIcon(R.drawable.ic_baseline_my_location_24)
            .setContentTitle("Reminders in reach")
            .setContentText("You are near ${numberOfReminders} important reminders!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_REMINDER)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
        with(NotificationManagerCompat.from(Graph.appContext)) {
            notify(notificationId, builder.build())
        }
    }
}