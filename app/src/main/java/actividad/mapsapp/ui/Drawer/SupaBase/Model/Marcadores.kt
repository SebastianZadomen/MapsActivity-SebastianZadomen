package actividad.mapsapp.ui.Drawer.SupaBase.Model
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Marcadores(
    val id: Long? = null,
    @SerialName("Nombre")
    val Nombre: String,

    @SerialName("Longitude")
    val Longitud: Double,

    @SerialName("Latitude")
    val Latitud: Double,

    @SerialName("Description")
    val Descripcion: String? = null,

    @SerialName("ImageUrl")
    val ImageUrl: String? = null
    )