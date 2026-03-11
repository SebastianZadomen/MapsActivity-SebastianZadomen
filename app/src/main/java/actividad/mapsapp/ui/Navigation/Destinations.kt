package actividad.mapsapp.ui.Navigation

sealed class Destinations (val route: String){
    object MapsScreen : Destinations("mapsScreen")

    object ListScreen : Destinations("listScreen")

    object AddMakerScreen : Destinations("addMakerScreen")

}