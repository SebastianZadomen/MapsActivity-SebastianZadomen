package actividad.mapsapp.ui.ViewModel

import actividad.mapsapp.ui.Drawer.SupaBase.Model.Marcadores
import actividad.mapsapp.ui.Drawer.SupaBase.Network.SupabaseClient
import actividad.mapsapp.ui.Drawer.SupaBase.Repository.MarcadoresRepo
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.jan.supabase.postgrest.postgrest
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
                e.printStackTrace()
            }
        }
    }



    fun añadirMarcador(marcador: Marcadores) {
        viewModelScope.launch {
            repository.añadirMarcador(marcador)
            carregarMarcador()
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