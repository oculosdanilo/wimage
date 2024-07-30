package io.oculosdanilo.wimage

import android.app.WallpaperManager
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun Home(drawerState: DrawerState, scope: CoroutineScope, ctx: Context = LocalContext.current) {
  val permissaoDialog = remember { mutableStateOf(false) }
  val permissaoNegadaDialog = remember { mutableStateOf(false) }
  val wallpaper: MutableState<Drawable?> = remember { mutableStateOf(null) }
  
  val wallpaperPermissao = rememberPermissionState(android.Manifest.permission.MANAGE_EXTERNAL_STORAGE)
  val midiaPermissao = rememberPermissionState(
    when {
      Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2 -> android.Manifest.permission.READ_EXTERNAL_STORAGE
      Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE -> android.Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED
      else -> android.Manifest.permission.READ_MEDIA_IMAGES
    }
  )
  val pedirPermissaoLauncher = rememberLauncherForActivityResult(
    ActivityResultContracts.RequestPermission()
  ) { isPermitido ->
    if (isPermitido) {
      val wallpaperManager = WallpaperManager.getInstance(ctx)
      wallpaper.value = wallpaperManager.drawable
    } else {
    
    }
  }
  
  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text(text = stringResource(R.string.app_name)) },
        navigationIcon = {
          IconButton(
            onClick = { scope.launch { drawerState.apply { open() } } }
          ) { Icon(Icons.Filled.Menu, "Menu") }
        }
      )
    }
  ) { padding ->
    Box(modifier = Modifier.padding(padding)) {
      Button(onClick = { /*TODO*/ }, colors = ButtonDefaults.textButtonColors()) {
        Text("Danilo")
      }
    }
  }
}