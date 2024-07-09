package io.oculosdanilo.wimage

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.oculosdanilo.wimage.ui.theme.WimageTheme
import io.oculosdanilo.wimage.ui.theme.verTema
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

val Context.dataStore by preferencesDataStore(name = "settings")

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    
    val systemTheme = when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
      Configuration.UI_MODE_NIGHT_YES -> {
        true
      }
      
      Configuration.UI_MODE_NIGHT_NO -> {
        false
      }
      
      else -> {
        false
      }
    }
    
    setContent {
      val tema = verTema(applicationContext, systemTheme).collectAsState(initial = systemTheme)
      
      Wimage(tema.value)
    }
  }
}

@Serializable
object HomeRoute

@Serializable
object ConfigRoute

@Composable
fun Wimage(
  isDark: Boolean,
  ctx: Context = LocalContext.current
) {
  /* drawer */
  val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
  val scope = rememberCoroutineScope()
  
  /* navegação */
  val navController = rememberNavController()
  
  var routeAtual: String by rememberSaveable { mutableStateOf(HomeRoute.toString()) }
  
  /*var dark: Boolean by rememberSaveable {
    val darkmode = verTema(ctx) ?: isDark;
    mutableStateOf(darkmode)
  }*/
  
  WimageTheme(darkTheme = isDark) {
    ModalNavigationDrawer(
      drawerContent = {
        Drawer(
          onNavegarPraHome = {
            navController.navigate(route = HomeRoute)
            scope.launch {
              drawerState.apply {
                try {
                  async { close() }.await()
                  routeAtual = "$HomeRoute"
                } catch (e: CancellationException) {
                  routeAtual = "$HomeRoute"
                }
              }
            }
          },
          onNavegarPraConfig = {
            navController.navigate(route = ConfigRoute)
            scope.launch {
              drawerState.apply {
                try {
                  async { close() }.await()
                  routeAtual = "$ConfigRoute"
                } catch (e: CancellationException) {
                  routeAtual = "$ConfigRoute"
                }
              }
            }
          },
          routeAtual
        )
      },
      drawerState = drawerState
    ) {
      NavHost(navController = navController, startDestination = HomeRoute) {
        composable<HomeRoute> { Home(drawerState, scope) }
        composable<ConfigRoute> { Config(drawerState, scope) }
      }
    }
  }
}