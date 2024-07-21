package io.oculosdanilo.wimage.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModel

@Composable
fun WimageTheme(
  darkTheme: Boolean,
  deviceColors: Boolean,
  content: @Composable () -> Unit
) {
  val context = LocalContext.current
  val cores = if (darkTheme) {
    if (deviceColors) dynamicDarkColorScheme(context) else darkColorScheme(corFonte)
  } else {
    if (deviceColors) dynamicLightColorScheme(context) else lightColorScheme(corFonte)
  }
  
  MaterialTheme(
    colorScheme = cores,
    typography = Typography,
    content = content
  )
  
  val view = LocalView.current
  if (!view.isInEditMode) {
    SideEffect {
      val window = (view.context as Activity).window
      window.statusBarColor = cores.background.toArgb()
      
      WindowCompat.getInsetsController(window, view)
        .isAppearanceLightStatusBars = !darkTheme
    }
  }
}

class TemaViewModel : ViewModel() {
  var darkModeAtivado = mutableStateOf(false)
    private set
  
  fun setDarkModeAtivado(novoDarkMode: Boolean) {
    darkModeAtivado.value = novoDarkMode
  }
}

class MYViewModel : ViewModel() {
  var myAtivado = mutableStateOf(false)
    private set
  
  fun setMYAtivado(novoMYAtivado: Boolean) {
    myAtivado.value = novoMYAtivado
  }
}