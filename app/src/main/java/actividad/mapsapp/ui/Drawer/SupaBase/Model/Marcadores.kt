package actividad.mapsapp.ui.Drawer.SupaBase.Model
import kotlinx.serialization.Serializable


@Serializable
data class Marcadores(
    val id: String? = null,
    val Nombre: String,
    val Longitud: Double,
    val Latitud: Double,
    val Descripcion : String
    )