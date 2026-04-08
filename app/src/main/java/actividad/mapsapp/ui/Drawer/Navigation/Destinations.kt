package actividad.mapsapp.ui.Drawer.Navigation

import kotlinx.serialization.Serializable

sealed class Destinations (){
    @Serializable

    object MapsScreen : Destinations()
    @Serializable

    object ListScreen : Destinations()
    @Serializable

    object AddMakerScreen : Destinations()

    @Serializable
    object  DetailScreen : Destinations()

}