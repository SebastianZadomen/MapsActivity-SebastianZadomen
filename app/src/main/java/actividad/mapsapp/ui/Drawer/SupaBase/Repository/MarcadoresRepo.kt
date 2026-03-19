package actividad.mapsapp.ui.Drawer.SupaBase.Repository

import actividad.mapsapp.ui.Drawer.SupaBase.Model.Marcadores
import actividad.mapsapp.ui.Drawer.SupaBase.Network.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest


class MarcadoresRepo {
    private val taula = SupabaseClient.client.postgrest["marcadores"]

    suspend fun añadirMarcador(marcador: Marcadores) {
        val nuevoMarcador = marcador
        taula.insert(nuevoMarcador)
    }

    suspend fun actualitzarEstatMarcador(id: String, completada: Boolean) {
        taula.update({
            set("completada", completada)
        }) {
            filter {
                eq("id", id)
            }
        }
    }
    suspend fun obtenerMarcadores(): List<Marcadores> {
        return taula.select().decodeList<Marcadores>()
    }


    suspend fun eliminarMarcador(id: String) {
        taula.delete {
            filter {
                eq("id", id)
            }
        }
    }
}