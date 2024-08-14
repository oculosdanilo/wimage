package io.oculosdanilo.wimage

import android.app.WallpaperManager
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import io.oculosdanilo.wimage.ui.theme.corFonte
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun Home(
  drawerState: DrawerState,
  scope: CoroutineScope,
  darkTheme: Boolean,
  deviceColors: Boolean,
  ctx: Context = LocalContext.current,
) {
  val permissaoDialog = remember { mutableStateOf(false) }
  val wallpaper: MutableState<Drawable?> = remember { mutableStateOf(null) }
  
  val midiaPermissao = rememberPermissionState(
    when {
      Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2 -> android.Manifest.permission.READ_EXTERNAL_STORAGE
      else -> android.Manifest.permission.READ_MEDIA_IMAGES
    }
  )
  val pedirPermissaoLauncher = rememberLauncherForActivityResult(
    ActivityResultContracts.RequestPermission()
  ) { isPermitido ->
    if (!Environment.isExternalStorageManager()) {
      val allfiles = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
      allfiles.setData(Uri.parse(String.format("package:%s", ctx.packageName)))
      startActivity(ctx, allfiles, null)
      
      val wallpaperManager = WallpaperManager.getInstance(ctx)
      wallpaper.value = wallpaperManager.drawable
    } else {
      val wallpaperManager = WallpaperManager.getInstance(ctx)
      wallpaper.value = wallpaperManager.drawable
    }
    
    if (!isPermitido) {
      val toast = Toast.makeText(ctx, ctx.getText(R.string.permNegada), Toast.LENGTH_LONG)
      toast.show()
    }
    
    permissaoDialog.value = false
  }
  
  val cores = if (darkTheme) {
    if (deviceColors) dynamicDarkColorScheme(ctx) else darkColorScheme(corFonte)
  } else {
    if (deviceColors) dynamicLightColorScheme(ctx) else lightColorScheme(corFonte)
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
      when {
        permissaoDialog.value -> DialogoPermissao(
          onConfirmar = {
            pedirPermissaoLauncher.launch(
              when {
                Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2 -> android.Manifest.permission.READ_EXTERNAL_STORAGE
                else -> android.Manifest.permission.READ_MEDIA_IMAGES
              }
            )
          },
          onCancelar = {
            permissaoDialog.value = false
          }
        )
      }
      
      LaunchedEffect(midiaPermissao) {
        if (!midiaPermissao.status.isGranted) {
          permissaoDialog.value = true
        }
        if (Environment.isExternalStorageManager()) {
          val wallpaperManager = WallpaperManager.getInstance(ctx)
          wallpaper.value = wallpaperManager.drawable
        }
      }
      
      /* ===================== pagina inicial ===================== */
      Column(
        Modifier
          .fillMaxSize()
          .padding(all = 16.dp)
      ) {
        
        /* caixa principal */
        Box(
          Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .padding(16.dp)
            .clip(shape = RoundedCornerShape(32.dp))
            .background(cores.primary),
          contentAlignment = Alignment.Center
        ) {
          when {
            Environment.isExternalStorageManager() -> Box(
              Modifier
                .fillMaxSize()
                .padding(4.dp)
                .clip(shape = RoundedCornerShape(30.dp))
                .paint(rememberDrawablePainter(wallpaper.value), contentScale = ContentScale.Crop)
            )
          }
          
          /* TODO: aprender a fazer widget beijos */
        }
        
        /* botoes */
        Row(
          Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
        ) {
          FilledTonalButton(
            onClick = {},
            Modifier
              .fillMaxWidth()
              .weight(1f)
          ) { Text(stringResource(R.string.btnSelect)) }
          Spacer(Modifier.width(16.dp))
          FilledTonalButton(
            onClick = {
              val toast = Toast.makeText(ctx, wallpaper.value.toString(), Toast.LENGTH_LONG)
              toast.show()
            },
            Modifier
              .fillMaxWidth()
              .weight(1f)
          ) { Text(stringResource(R.string.btnWidget)) }
        }
      }
    }
  }
}