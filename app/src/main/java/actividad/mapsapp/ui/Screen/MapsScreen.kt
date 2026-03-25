package actividad.mapsapp.ui.Screen

import actividad.mapsapp.ui.Drawer.SupaBase.Model.Marcadores
import actividad.mapsapp.ui.ViewModel.MarcadorViewModel
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
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
fun MapsScreen(spView:  MarcadorViewModel = viewModel() ) {

    val listMarcador by spView.marc.collectAsStateWithLifecycle(initialValue = emptyList<Marcadores>())

    Column(Modifier.fillMaxSize()) {
        val itb = LatLng(41.4534225, 2.1837151)
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(itb, 17f)
        }
        GoogleMap(
            Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            onMapClick = {
                Log.d("MAP CLICKED", it.toString())
            }, onMapLongClick = {
                Log.d("MAP CLICKED LONG", it.toString())
            }){

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