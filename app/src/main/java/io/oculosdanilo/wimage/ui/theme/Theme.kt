package io.oculosdanilo.wimage.ui.theme

import android.content.Context
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.datastore.preferences.core.booleanPreferencesKey
import io.oculosdanilo.wimage.dataStore
import kotlinx.coroutines.flow.map

@Composable
fun WimageTheme(
  darkTheme: Boolean,
  content: @Composable () -> Unit
) {
  MaterialTheme(
    colorScheme = if (darkTheme) darkColorScheme(corFonte) else darkColorScheme(corFonte),
    typography = Typography,
    content = content
  )
}

fun verTema(ctx: Context): Boolean? {
  val darkmodeKey = booleanPreferencesKey("darkmode")
  var darkmodeValor: Boolean? = null
  ctx.dataStore.data.map { preferences ->
    darkmodeValor = preferences[darkmodeKey]
  }
  return darkmodeValor
}

fun mudarTema() {
  /* TODO */
}