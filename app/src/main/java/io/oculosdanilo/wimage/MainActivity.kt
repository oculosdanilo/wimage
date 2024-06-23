package io.oculosdanilo.wimage

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Modifier
import io.oculosdanilo.wimage.ui.theme.WimageTheme

class MainActivity : ComponentActivity() {
  @OptIn(ExperimentalMaterial3Api::class)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    
    setContent {
      WimageTheme {
        Scaffold(
          topBar = {
            TopAppBar(
              title = { Text(text = "Wimage") },
              navigationIcon = { Icon(Icons.Filled.Menu, "menu") }
            )
          }
        ) { padding ->
          Box(modifier = Modifier.padding(padding))
        }
      }
    }
  }
}