package io.oculosdanilo.wimage

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(drawerState: DrawerState, scope: CoroutineScope) {
  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text(text = stringResource(R.string.app_name)) },
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
            Icon(Icons.Filled.Menu, "Menu")
          }
        }
      )
    }
  ) { padding ->
    Box(modifier = Modifier.padding(padding))
  }
}