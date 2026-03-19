package actividad.mapsapp.ui.Drawer.Components
import actividad.mapsapp.ui.Drawer.Navigation.Destinations
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Home

enum class DrawerItem(
    val icon: ImageVector,
    val text: String,
    val route: Destinations
)
{
    MapsScreen(Icons.Default.Home, "MapsScreen", Destinations.MapsScreen),
    ListScreen(Icons.Default.Assignment, "ListScreen", Destinations.ListScreen),
    AddMarkerScreen(Icons.Default.Add, "AddMarkerScreen", Destinations.AddMakerScreen),

}