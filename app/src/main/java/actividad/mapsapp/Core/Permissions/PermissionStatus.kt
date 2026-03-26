package actividad.mapsapp.Core.Permissions

    sealed class PermissionStatus {
        object Unknown : PermissionStatus()
        object Granted : PermissionStatus()
        object Denied : PermissionStatus()
        object PermanentlyDenied : PermissionStatus()
    }

