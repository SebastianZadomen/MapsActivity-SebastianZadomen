package actividad.mapsapp.ui.ViewModel

import actividad.mapsapp.ui.Drawer.SupaBase.Model.Marcadores
import actividad.mapsapp.ui.Drawer.SupaBase.Network.SupabaseClient
import actividad.mapsapp.ui.Drawer.SupaBase.Repository.MarcadoresRepo
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch



class MarcadorViewModel : ViewModel(){

    private val repository = MarcadoresRepo()
    private val _marcador = MutableStateFlow<List<Marcadores>>(emptyList())
    val marc: StateFlow<List<Marcadores>> = _marcador

    init {
        carregarMarcador()
    }


    private fun carregarMarcador() {
        viewModelScope.launch {
            try {
                _marcador.value = repository.obtenerMarcadores()
            } catch (e: Exception) {
                android.util.Log.e("SUPABASE_ERROR", "Error al cargar marcadores: ${e.message}")
            }
        }
    }



    fun añadirMarcador(
        nombre: String,
        descripcion: String,
        latitud: Double,
        longitud: Double,
        uriImagen: Uri?,
        context: Context
    ) {
        viewModelScope.launch {
            try {
                var imageUrl: String? = null

                if (uriImagen != null) {
                    val inputStream = context.contentResolver.openInputStream(uriImagen)
                    val byteArray = inputStream?.readBytes()
                    inputStream?.close()

                    if (byteArray != null) {
                        val fileName = "marcador_${System.currentTimeMillis()}.jpg"
                        // Subimos la foto al bucket "marcadores_img" (asegúrate de que existe y es público en Supabase)
                        SupabaseClient.client.storage.from("marcadores_img").upload(fileName, byteArray)
                        imageUrl = SupabaseClient.client.storage.from("marcadores_img").publicUrl(fileName)
                    }
                }

                val nuevoMarcador = Marcadores(
                    Nombre = nombre,
                    Longitud = longitud,
                    Latitud = latitud,
                    Descripcion = descripcion,
                    ImageUrl = imageUrl
                )

                repository.añadirMarcador(nuevoMarcador)
                carregarMarcador()

            } catch (e: Exception) {
                Log.e("SUPABASE_ERROR", "Error guardando con imagen: ${e.message}")
            }
        }
    }


    fun eliminarMarcador(marcador: Marcadores) {
        viewModelScope.launch {
            marcador.id?.let { id ->
                repository.eliminarMarcador(id)
                carregarMarcador()
            }
        }
    }
}