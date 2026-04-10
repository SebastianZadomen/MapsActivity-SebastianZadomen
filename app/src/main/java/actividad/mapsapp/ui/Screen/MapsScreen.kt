package actividad.mapsapp.ui.Screen

import actividad.mapsapp.Core.Map.MapPermissionState
import actividad.mapsapp.Core.Map.MapViewModel
import actividad.mapsapp.Core.Permissions.AppPermission
import actividad.mapsapp.Core.Permissions.PermissionContent
import actividad.mapsapp.Core.Permissions.PermissionStatus
import actividad.mapsapp.Core.Permissions.rememberPermissionManager
import actividad.mapsapp.ui.Drawer.Navigation.Destinations
import actividad.mapsapp.ui.Drawer.SupaBase.Model.Marcadores
import actividad.mapsapp.ui.ViewModel.MarcadorViewModel
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.flow.forEach

@SuppressLint("UnrememberedMutableState")
@Composable
fun MapsScreen(
    navController: NavController,
    spView: MarcadorViewModel = viewModel(),
    permissionViewModel: MapViewModel = viewModel()
) {
    val permissionManager = rememberPermissionManager(AppPermission.Location)
    val uiState by permissionViewModel.uiState

    LaunchedEffect(permissionManager.status) {
        if (permissionManager.status == PermissionStatus.Unknown) {
            permissionManager.requestPermissions()
        }
        permissionViewModel.onPermissionResult(permissionManager.status)
    }

    when (uiState) {
        MapPermissionState.NavigateToMap -> {
            val listMarcador by spView.marc.collectAsStateWithLifecycle(initialValue = emptyList<Marcadores>())
            val lastList = listMarcador.lastOrNull()

            Column(Modifier.fillMaxSize()) {
                val camaraActual = LatLng(lastList?.Latitud ?: 41.4534225  , lastList?.Longitud ?: 2.1837151)
                val cameraPositionState = rememberCameraPositionState {
                    position = CameraPosition.fromLatLngZoom(camaraActual, 17f)
                }
                LaunchedEffect(lastList) {
                    lastList?.let { marcador ->
                        cameraPositionState.position = CameraPosition.fromLatLngZoom(
                            LatLng(marcador.Latitud, marcador.Longitud),
                            17f
                        )
                    }
                }

                GoogleMap(
                    modifier = Modifier.fillMaxSize(),
                    cameraPositionState = cameraPositionState,
                    onMapClick = {
                        Log.d("MAP CLICKED", it.toString())
                    },
                    onMapLongClick = {
                        Log.d("MAP CLICKED LONG", it.toString())
                    }
                ) {
                    listMarcador.forEach { marcador ->
                        val position = LatLng(marcador.Latitud, marcador.Longitud)

                        Marker(
                            state = MarkerState(position =  position),
                            title = marcador.Nombre,
                            snippet = marcador.Nombre,
                            onClick = {
                                spView.marcadorSelect = marcador
                                navController.navigate(Destinations.DetailScreen)
                                false
                            }
                        )
                    }
                }
            }
        }

        MapPermissionState.ShowDenied -> {
            PermissionContent(
                PermissionStatus.Denied,
                permissionManager.requestPermissions
            )
        }

        MapPermissionState.ShowPermanentlyDenied -> {
            PermissionContent(
                PermissionStatus.PermanentlyDenied,
                {}
            )
        }

        MapPermissionState.Requesting -> {
            PermissionContent(
                PermissionStatus.Unknown,
                permissionManager.requestPermissions
            )
        }
    }
}
