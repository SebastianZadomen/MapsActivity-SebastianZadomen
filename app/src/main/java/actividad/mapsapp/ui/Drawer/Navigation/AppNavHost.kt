package actividad.mapsapp.ui.Drawer.Navigation

import actividad.mapsapp.ui.Drawer.Layout.MainScaffold
import actividad.mapsapp.ui.Screen.AddMakerScreen
import actividad.mapsapp.ui.Screen.ListScreen
import actividad.mapsapp.ui.Screen.MapsScreen
import actividad.mapsapp.ui.ViewModel.MarcadorViewModel
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavHost(navController: NavHostController, supaViewModel: ViewModel) {
    MainScaffold(navController) {
        NavHost(navController = navController, startDestination = Destinations.MapsScreen) {
            composable<Destinations.MapsScreen> { MapsScreen() }
            composable<Destinations.ListScreen> { ListScreen(supaViewModel as MarcadorViewModel) }
            composable<Destinations.AddMakerScreen> { AddMakerScreen() }
        }
    }
}