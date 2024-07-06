package io.oculosdanilo.wimage

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import io.oculosdanilo.wimage.ui.theme.WimageTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    
    setContent {
      Wimage()
    }
  }
}

@Composable
fun Wimage() {
  val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
  val scope = rememberCoroutineScope()
  
  WimageTheme {
    ModalNavigationDrawer(drawerContent = { Drawer() }, drawerState = drawerState) {
      Home(drawerState, scope)
    }
  }
}