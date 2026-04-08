package actividad.mapsapp

import actividad.mapsapp.ui.Drawer.Navigation.AppNavHost
import actividad.mapsapp.ui.ViewModel.MarcadorViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import actividad.mapsapp.ui.theme.MapsAppTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController


import androidx.compose.ui.platform.LocalConfiguration

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MapsAppTheme {
                val configuration = LocalConfiguration.current

                val isTablet = configuration.screenWidthDp > 600

                val supaViewModel : MarcadorViewModel = viewModel()
                val navController = rememberNavController()
                AppNavHost(navController = navController, supaViewModel, isTablet)
                }

        }
    }
}

