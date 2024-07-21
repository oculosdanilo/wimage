package io.oculosdanilo.wimage

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
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
import androidx.lifecycle.ViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.oculosdanilo.wimage.datastore.DataStoreUtil
import io.oculosdanilo.wimage.ui.theme.MYViewModel
import io.oculosdanilo.wimage.ui.theme.TemaViewModel
import io.oculosdanilo.wimage.ui.theme.WimageTheme
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
  private val temaViewModel: TemaViewModel by viewModels()
  private val myViewModel: MYViewModel by viewModels()
  private val carregouViewModel: CarregouViewModel by viewModels()
  private lateinit var dataStoreUtil: DataStoreUtil
  
  @SuppressLint("CoroutineCreationDuringComposition")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    
    dataStoreUtil = DataStoreUtil(applicationContext)
    
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
      val tema by dataStoreUtil.pegarTema(systemTheme).collectAsState(initial = systemTheme)
      temaViewModel.setDarkModeAtivado(tema)
      val my by dataStoreUtil.pegarMY().collectAsState(initial = false)
      myViewModel.setMYAtivado(my)
      val scope = rememberCoroutineScope()
      scope.launch {
        carregouViewModel.setCarregou(dataStoreUtil)
      }
      
      Wimage(tema, my, myViewModel, temaViewModel)
    }
    
    val conteudo: View = findViewById(android.R.id.content)
    conteudo.viewTreeObserver.addOnPreDrawListener(
      object : ViewTreeObserver.OnPreDrawListener {
        override fun onPreDraw(): Boolean {
          // Check whether the initial data is ready.
          return if (carregouViewModel.carregou.value) {
            // The content is ready. Start drawing.
            conteudo.viewTreeObserver.removeOnPreDrawListener(this)
            true
          } else {
            // The content isn't ready. Suspend.
            false
          }
        }
      }
    )
    
  }
}

@Serializable
object HomeRoute

@Serializable
object ConfigRoute

@Composable
fun Wimage(
  isDark: Boolean,
  isDeviceMY: Boolean,
  myViewModel: MYViewModel,
  temaViewModel: TemaViewModel
) {
  /* drawer */
  val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
  val scope = rememberCoroutineScope()
  
  /* navegação */
  val navController = rememberNavController()
  var routeAtual: String by rememberSaveable { mutableStateOf(HomeRoute.toString()) }
  
  WimageTheme(darkTheme = isDark, deviceColors = isDeviceMY) {
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
        composable<ConfigRoute> { Config(drawerState, scope, temaViewModel, myViewModel) }
      }
    }
  }
}

class CarregouViewModel : ViewModel() {
  var carregou = mutableStateOf(false)
    private set
  
  suspend fun setCarregou(dataStoreUtil: DataStoreUtil) {
    dataStoreUtil.init()
    carregou.value = true
  }
}