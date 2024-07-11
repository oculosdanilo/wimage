package io.oculosdanilo.wimage.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModel

@Composable
fun WimageTheme(
  darkTheme: Boolean,
  content: @Composable () -> Unit
) {
  val cores = if (darkTheme) {
    darkColorScheme(corFonte)
  } else {
    lightColorScheme(corFonte)
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
}

class MYViewModel : ViewModel() {
  var myAtivado = mutableStateOf(false)
    private set
}