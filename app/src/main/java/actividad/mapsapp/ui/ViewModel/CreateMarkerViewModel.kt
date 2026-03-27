package actividad.mapsapp.ui.ViewModel

import actividad.mapsapp.Core.Markers.CreateMarkerPermissionState
import actividad.mapsapp.Core.Permissions.PermissionStatus
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import android.net.Uri
import androidx.compose.runtime.State


class CreateMarkerViewModel: ViewModel() {
    private val _uiState =
        mutableStateOf<CreateMarkerPermissionState>(CreateMarkerPermissionState.Requesting)

    val uiState: MutableState<CreateMarkerPermissionState> = _uiState

    fun onPermissionResult(status: PermissionStatus) {
        _uiState.value = when (status) {
            PermissionStatus.Granted ->
                CreateMarkerPermissionState.NavigateToMap

            PermissionStatus.Denied ->
                CreateMarkerPermissionState.ShowDenied

            PermissionStatus.PermanentlyDenied ->
                CreateMarkerPermissionState.ShowPermanentlyDenied

            PermissionStatus.Unknown ->
                CreateMarkerPermissionState.Requesting
        }
    }

    private var _imageUri = mutableStateOf<Uri?>(null)
    val imageUri: State<Uri?> = _imageUri

    private var _showDialog = mutableStateOf(false)
    val showDialog: State<Boolean> = _showDialog

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _creationSuccess = mutableStateOf(false)
    val creationSuccess: State<Boolean> = _creationSuccess

    var tempUri: Uri? = null

    fun onCameraImageSaved() {
        _imageUri.value = tempUri
    }

    fun setImageUri(uri: Uri?) {
        _imageUri.value = uri
    }

    fun changeShowDialog(value: Boolean) {
        _showDialog.value = value
    }

}