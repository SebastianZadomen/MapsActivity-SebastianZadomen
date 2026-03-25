package actividad.mapsapp.ui.Drawer.SupaBase.Repository

import actividad.mapsapp.ui.Drawer.SupaBase.Model.Marcadores
import actividad.mapsapp.ui.Drawer.SupaBase.Network.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest


class MarcadoresRepo {
    private val taula = SupabaseClient.client.postgrest["Marcadores"]

    suspend fun añadirMarcador(marcador: Marcadores) {
        val nuevoMarcador = marcador
        taula.insert(nuevoMarcador)
    }


    suspend fun obtenerMarcadores(): List<Marcadores> {
        return taula.select().decodeList<Marcadores>()
    }


    suspend fun eliminarMarcador(id: Long) {
        taula.delete {
            filter {
                eq("id", id)
            }
        }
    }
}