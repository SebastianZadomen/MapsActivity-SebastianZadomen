package actividad.mapsapp.ui.Drawer.SupaBase.Network


import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage
object SupabaseClient {
    val client = createSupabaseClient(
        supabaseUrl = "https://yifrbjirfxubwimiepab.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InlpZnJiamlyZnh1YndpbWllcGFiIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NzMyODg4NzEsImV4cCI6MjA4ODg2NDg3MX0.pnnnuJrwX0w_xjUzU9PeDpfS_e7sljfdMoz5R6EUZaU"
    ) {
        install(Postgrest)
        install(Storage) //
    }
}