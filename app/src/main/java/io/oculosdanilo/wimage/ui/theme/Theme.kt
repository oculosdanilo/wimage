package io.oculosdanilo.wimage.ui.theme

import android.content.Context
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import io.oculosdanilo.wimage.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Composable
fun WimageTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  content: @Composable () -> Unit
) {
  MaterialTheme(
    colorScheme = if (darkTheme) darkColorScheme(corFonte) else darkColorScheme(corFonte),
    typography = Typography,
    content = content
  )
}

val MODO_ESCURO = booleanPreferencesKey("modoescuro")

fun verTema(ctx: Context, isSystemDark: Boolean): Flow<Boolean> {
  return ctx.dataStore.data.map { pref ->
    pref[MODO_ESCURO] ?: isSystemDark
  }
}

suspend fun mudarTema(ctx: Context, novoTema: Boolean) {
  ctx.dataStore.edit { pref ->
    pref[MODO_ESCURO] = novoTema;
  }
}

class TemaViewModel : ViewModel() {
  var darkModeAtivado = mutableStateOf(false)
    private set
  
  fun mudarTema(modoEscuro: Boolean) {
    darkModeAtivado.value = modoEscuro;
  }
}