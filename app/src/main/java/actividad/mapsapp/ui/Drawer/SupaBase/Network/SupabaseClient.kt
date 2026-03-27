package actividad.mapsapp.ui.Drawer.SupaBase.Network


import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage
object SupabaseClient {
    val client = createSupabaseClient(
        supabaseUrl = "https://yifrbjirfxubwimiepab.supabase.co",
        supabaseKey = "sb_publishable_xuqzEGjYRl_SoF-hVkpKDQ_66VuH7AL"
    ) {
        install(Postgrest)
        install(Storage) //
    }
}