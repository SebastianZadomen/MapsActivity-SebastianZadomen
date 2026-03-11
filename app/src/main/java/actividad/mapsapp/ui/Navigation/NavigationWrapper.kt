package actividad.mapsapp.ui.Navigation

import actividad.mapsapp.ui.Screen.AddMakerScreen
import actividad.mapsapp.ui.Screen.ListScreen
import actividad.mapsapp.ui.Screen.MapsScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavigationWrapper(
    navController: NavHostController,
    ) {


    NavHost(
        navController = navController,
        startDestination = Destinations.MapsScreen.route
    ) {

        composable(Destinations.MapsScreen.route) {
            MapsScreen(navController)
        }

        composable(Destinations.ListScreen.route) {
            ListScreen(navController)
        }
        composable(Destinations.AddMakerScreen.route) {
            AddMakerScreen(navController)
        }

    }
}