package io.oculosdanilo.wimage

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import io.oculosdanilo.wimage.ui.theme.WimageTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

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
    ModalNavigationDrawer(drawerContent = {
      Drawer()
    }, drawerState = drawerState) {
      Home(drawerState, scope)
    }
  }
}

@Composable
fun Drawer() {
  val ctx = LocalContext.current
  val intentRep: Intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/oculosdanilo/wimage"))
  val intentDanilo: Intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/oculosdanilo"))
  
  ModalDrawerSheet {
    NavigationDrawerItem(
      label = { Text(text = stringResource(R.string.github_rep)) },
      selected = false,
      onClick = { ctx.startActivity(intentRep) }
    )
    NavigationDrawerItem(
      label = { Text(text = stringResource(R.string.github_danilo)) },
      selected = false,
      onClick = { ctx.startActivity(intentDanilo) }
    )
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(drawerState: DrawerState, scope: CoroutineScope) {
  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text(text = "Wimage") },
        navigationIcon = {
          IconButton(
            onClick = {
              scope.launch {
                drawerState.apply {
                  if (isClosed) open() else close()
                }
              }
            }
          ) {
            Icon(Icons.Filled.Menu, contentDescription = "Menu")
          }
        }
      )
    }
  ) { padding ->
    Box(modifier = Modifier.padding(padding))
  }
}