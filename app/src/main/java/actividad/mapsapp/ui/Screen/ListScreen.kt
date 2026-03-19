package actividad.mapsapp.ui.Screen

import actividad.mapsapp.ui.Drawer.SupaBase.Model.Marcadores
import actividad.mapsapp.ui.ViewModel.MarcadorViewModel
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ListScreen(spView: MarcadorViewModel = viewModel() ) {
    val listMarcador by spView.marc.collectAsStateWithLifecycle(initialValue = emptyList<Marcadores>())

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(listMarcador) { marc: Marcadores ->
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Card(
                    modifier = Modifier

                        .fillMaxWidth()
                        .padding(8.dp)
                ) {

                    Text(
                        text = marc.Nombre,
                        modifier = Modifier.weight(1f)
                    )
                }


            }
        }
    }
}

