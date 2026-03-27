package actividad.mapsapp.ui.Screen

import actividad.mapsapp.Core.Map.MapPermissionState
import actividad.mapsapp.Core.Map.MapViewModel
import actividad.mapsapp.Core.Permissions.AppPermission
import actividad.mapsapp.Core.Permissions.PermissionContent
import actividad.mapsapp.Core.Permissions.PermissionStatus
import actividad.mapsapp.Core.Permissions.rememberPermissionManager
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
    // Mantenemos tu ViewModel para la base de datos
    spView: MarcadorViewModel = viewModel(),
    // Añadimos el ViewModel del profe para controlar los permisos
    permissionViewModel: MapViewModel = viewModel()
) {
    // 1. Lógica de permisos (del profe)
    val permissionManager = rememberPermissionManager(AppPermission.Location)
    val uiState by permissionViewModel.uiState

    LaunchedEffect(permissionManager.status) {
        if (permissionManager.status == PermissionStatus.Unknown) {
            permissionManager.requestPermissions()
        }
        permissionViewModel.onPermissionResult(permissionManager.status)
    }

    // 2. Control de la interfaz (El "portero")
    when (uiState) {
        // ¡PERMISO CONCEDIDO! Mostramos TU mapa
        MapPermissionState.NavigateToMap -> {
            // Obtenemos tu lista de marcadores
            val listMarcador by spView.marc.collectAsStateWithLifecycle(initialValue = emptyList<Marcadores>())

            Column(Modifier.fillMaxSize()) {
                val itb = LatLng(41.4534225, 2.1837151)
                val cameraPositionState = rememberCameraPositionState {
                    position = CameraPosition.fromLatLngZoom(itb, 17f)
                }

                // Propiedad activada para que se vea la bolita azul de tu ubicación (ya que tienes permiso)
                GoogleMap(
                    modifier = Modifier.fillMaxSize(),
                    cameraPositionState = cameraPositionState,
                    // properties = MapProperties(isMyLocationEnabled = true), <-- Descomenta esto para ver tu ubicación real
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
                            state = MarkerState(position = position),
                            title = marcador.Nombre
                        )
                    }
                }
            }
        }

        // PERMISO DENEGADO (Pantallas del profe)
        MapPermissionState.ShowDenied -> {
            PermissionContent(
                PermissionStatus.Denied,
                permissionManager.requestPermissions
            )
        }

        // PERMISO DENEGADO PERMANENTEMENTE (Pantallas del profe)
        MapPermissionState.ShowPermanentlyDenied -> {
            PermissionContent(
                PermissionStatus.PermanentlyDenied,
                {}
            )
        }

        // SOLICITANDO PERMISO (Pantallas del profe)
        MapPermissionState.Requesting -> {
            PermissionContent(
                PermissionStatus.Unknown,
                permissionManager.requestPermissions
            )
        }
    }
}
