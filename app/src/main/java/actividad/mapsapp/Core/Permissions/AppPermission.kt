
package actividad.mapsapp.Core.Permissions

import android.Manifest

sealed class AppPermission(val permissions: List<String>) {

    object Location : AppPermission(
        // Es buena práctica pedir el COARSE junto con el FINE en las versiones nuevas de Android
        listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    object CameraAndAudio : AppPermission(
        listOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)
    )

    // ¡NUEVO! Aquí creamos el permiso combinado que busca nuestra pantalla
    object LocationAndCamera : AppPermission(
        listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.CAMERA
        )
    )
}