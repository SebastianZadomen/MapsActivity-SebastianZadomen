package actividad.mapsapp.ui.Screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun AddMakerScreen() {
    var myText by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(top = 5.dp , bottom = 5.dp, start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {

            Box(
                modifier = Modifier
                    .padding(top = 5.dp , bottom = 5.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                OutlinedTextField(
                    value = myText,
                    onValueChange = { myText = it },
                    label = { Text("Introduce un nombre") },
                    modifier = Modifier.fillMaxWidth()
                )
            }


            Box(
                modifier = Modifier
                    .padding(top = 5.dp , bottom = 5.dp,)
                    .fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = descripcion,
                    minLines = 3,
                    maxLines = 5,
                    onValueChange = { descripcion = it },
                    label = { Text("Descripcion") },
                    modifier = Modifier.fillMaxWidth()
                )

            }



            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                IconButton (

                    onClick = {

                    }, modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.CameraAlt,
                        contentDescription = "favorite",
                        Modifier.size(250.dp))
                }

            }

        }
    }

}