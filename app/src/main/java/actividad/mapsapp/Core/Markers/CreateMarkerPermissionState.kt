package actividad.mapsapp.Core.Markers

sealed class CreateMarkerPermissionState {
    object Requesting : CreateMarkerPermissionState()
    object ShowDenied : CreateMarkerPermissionState()
    object ShowPermanentlyDenied : CreateMarkerPermissionState()
    object NavigateToMap : CreateMarkerPermissionState()
}
