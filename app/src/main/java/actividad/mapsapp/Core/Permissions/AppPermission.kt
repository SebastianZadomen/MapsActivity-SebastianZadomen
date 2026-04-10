package actividad.mapsapp.Core.Permissions

import android.Manifest
sealed class AppPermission(val permissions: List<String>) {
    object Location : AppPermission(
        listOf(Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.CAMERA)
    )
    object CameraAndAudio : AppPermission(
        listOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)
    )
    object LocationAndCamera : AppPermission(
        listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.CAMERA
        )
    )

}
