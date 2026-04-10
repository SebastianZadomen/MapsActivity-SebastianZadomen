package actividad.mapsapp.ui.Screen
import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource

import actividad.mapsapp.Core.Markers.CreateMarkerPermissionState
import actividad.mapsapp.Core.Permissions.AppPermission
import actividad.mapsapp.Core.Permissions.PermissionContent
import actividad.mapsapp.Core.Permissions.PermissionStatus
import actividad.mapsapp.Core.Permissions.rememberPermissionManager
// IMPORTANTE: Asegúrate de que esta ruta de FileUtils es correcta en tu proyecto
import actividad.mapsapp.Core.Utils.FileUtils
import actividad.mapsapp.ui.Drawer.Navigation.Destinations
import actividad.mapsapp.ui.Drawer.SupaBase.Model.Marcadores
import actividad.mapsapp.ui.ViewModel.CreateMarkerViewModel
import actividad.mapsapp.ui.ViewModel.MarcadorViewModel
import androidx.navigation.NavHostController


@SuppressLint("MissingPermission")
@Composable
fun AddMakerScreen(
    navController: NavHostController,
    isTablet : Boolean,
    model: MarcadorViewModel,
    viewModel: CreateMarkerViewModel = viewModel()

) {
    val context = LocalContext.current
    val permissionManager = rememberPermissionManager(AppPermission.LocationAndCamera)
    val uiState by viewModel.uiState
    val imageUri by viewModel.imageUri
    val showDialog by viewModel.showDialog
    val isLoading by viewModel.isLoading

    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }

    LaunchedEffect(permissionManager.status) {
        if (permissionManager.status == PermissionStatus.Unknown) {
            permissionManager.requestPermissions()
        }
        viewModel.onPermissionResult(permissionManager.status)
    }

    val takePictureLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) viewModel.onCameraImageSaved()
    }

    val pickImageLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { viewModel.setImageUri(it) }
    }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            val uri = FileUtils.createImageUri(context)
            uri?.let {
                viewModel.tempUri = it
                takePictureLauncher.launch(it)
            }
        } else {
            Log.e("PERMISOS", "El usuario ha denegado el acceso a la cámara.")
        }
    }

    when (uiState) {
        CreateMarkerPermissionState.NavigateToMap -> {
            var myText by remember { mutableStateOf("") }
            var descripcion by remember { mutableStateOf("") }

            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { viewModel.changeShowDialog(false) },
                    title = { Text("Selecciona una opción") },
                    text = { Text("¿Quieres hacer una foto o elegir una de la galería?") },
                    confirmButton = {
                        TextButton(onClick = {
                            viewModel.changeShowDialog(false)

                            val permissionCheckResult = ContextCompat.checkSelfPermission(
                                context,
                                Manifest.permission.CAMERA
                            )

                            if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                                val uri = FileUtils.createImageUri(context)
                                uri?.let {
                                    viewModel.tempUri = it
                                    takePictureLauncher.launch(it)
                                }
                            } else {
                                cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                            }
                        }) { Text("Cámara") }
                    },
                    dismissButton = {
                        TextButton(onClick = {
                            viewModel.changeShowDialog(false)
                            pickImageLauncher.launch("image/*")
                        }) { Text("Galería") }
                    }
                )
            }

            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedTextField(
                        value = myText,
                        onValueChange = { myText = it },
                        label = { Text("Introduce un nombre") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = descripcion,
                        minLines = 3,
                        maxLines = 5,
                        onValueChange = { descripcion = it },
                        label = { Text("Descripción") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    if (imageUri != null) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(imageUri)
                                .crossfade(true)
                                .build(),
                            contentDescription = "Imagen seleccionada",
                            modifier = Modifier
                                .size(200.dp)
                                .clip(RoundedCornerShape(12.dp)),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        IconButton(
                            onClick = { viewModel.changeShowDialog(true)

                            },
                            modifier = Modifier.size(200.dp)
                        ) {
                            Icon(
                                Icons.Default.CameraAlt,
                                contentDescription = "Abrir cámara o galería",
                                Modifier.fillMaxSize()
                            )
                        }
                    }

                    Button(
                        onClick = {
                            val cancellationTokenSource = CancellationTokenSource()
                            fusedLocationClient.getCurrentLocation(
                                Priority.PRIORITY_HIGH_ACCURACY,
                                cancellationTokenSource.token
                            ).addOnSuccessListener { location ->
                                if (location != null) {
                                    model.añadirMarcador(
                                        nombre = myText,
                                        descripcion = descripcion,
                                        latitud = location.latitude,
                                        longitud = location.longitude,
                                        uriImagen = imageUri,
                                        context = context
                                    )
                                    model.marcadorSelect = Marcadores(Nombre = myText,
                                        Descripcion = descripcion,
                                        Latitud = location.latitude,
                                        Longitud = location.longitude,
                                        ImageUrl = "")
                                    myText = ""
                                    descripcion = ""
                                    viewModel.setImageUri(null)
                                    navController.navigate(Destinations.MapsScreen)


                                } else {
                                    Log.e("GPS", "No se pudo obtener la ubicación")
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                        enabled = myText.isNotBlank() && !isLoading
                    ) {
                        Text("Añadir Marcador")
                    }
                }
            }
        }

        CreateMarkerPermissionState.ShowDenied ->
            PermissionContent(PermissionStatus.Denied, permissionManager.requestPermissions)
        CreateMarkerPermissionState.ShowPermanentlyDenied ->
            PermissionContent(PermissionStatus.PermanentlyDenied, {})
        CreateMarkerPermissionState.Requesting ->
            PermissionContent(PermissionStatus.Unknown, permissionManager.requestPermissions)
    }
}