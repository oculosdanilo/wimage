package io.oculosdanilo.wimage

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.rounded.DarkMode
import androidx.compose.material.icons.rounded.Palette
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import io.oculosdanilo.wimage.datastore.DataStoreUtil
import io.oculosdanilo.wimage.ui.theme.MYViewModel
import io.oculosdanilo.wimage.ui.theme.TemaViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Config(drawerState: DrawerState, scope: CoroutineScope, temaViewModel: TemaViewModel, myViewModel: MYViewModel) {
  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text(text = stringResource(R.string.config)) },
        navigationIcon = {
          IconButton(
            onClick = { scope.launch { drawerState.apply { open() } } }
          ) { Icon(Icons.Filled.Menu, "Menu") }
        }
      )
    }
  ) { padding ->
    Column(modifier = Modifier.padding(padding)) {
      ListTile(icon = Icons.Rounded.DarkMode, label = stringResource(R.string.darkmode), temaViewModel)
      ListTile(icon = Icons.Rounded.Palette, label = stringResource(R.string.m3color), myViewModel)
    }
  }
}

@Composable
private fun ListTile(
  icon: ImageVector,
  label: String,
  viewModel: ViewModel
) {
  
  val scope = rememberCoroutineScope()
  val context: Context = LocalContext.current
  val dataStoreUtil = DataStoreUtil(context)
  
  var switchState by rememberSaveable {
    when (viewModel) {
      is TemaViewModel -> viewModel.darkModeAtivado
      is MYViewModel -> viewModel.myAtivado
      else -> mutableStateOf(false)
    }
  }
  
  Surface(
    onClick = {
      switchState = !switchState
      
      scope.launch {
        if (viewModel is TemaViewModel) dataStoreUtil.setarTema(switchState) else dataStoreUtil.setarMY(switchState)
      }
    }
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier
        .fillMaxWidth()
        .height(56.dp)
    ) {
      Spacer(Modifier.padding(horizontal = 16.dp))
      
      Icon(icon, label)
      
      Spacer(Modifier.padding(horizontal = 16.dp))
      
      Text(label, Modifier.weight(1.0f))
      
      Spacer(Modifier.padding(horizontal = 16.dp))
      
      Switch(
        checked = switchState,
        onCheckedChange = {
          switchState = it
          
          scope.launch {
            if (viewModel is TemaViewModel) dataStoreUtil.setarTema(it) else dataStoreUtil.setarMY(it)
          }
        }
      )
      
      Spacer(Modifier.padding(horizontal = 16.dp))
    }
  }
}