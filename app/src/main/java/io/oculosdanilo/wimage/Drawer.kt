package io.oculosdanilo.wimage

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIcons
import io.oculosdanilo.wimage.composable_icons.rememberHome
import io.oculosdanilo.wimage.composable_icons.rememberHomeFilled
import io.oculosdanilo.wimage.composable_icons.rememberSettings
import io.oculosdanilo.wimage.composable_icons.rememberSettingsFilled
import java.time.Year

@Composable
fun Drawer(onNavegarPraHome: () -> Unit, onNavegarPraConfig: () -> Unit, routeAtual: String) {
  val ctx = LocalContext.current
  val intentRep = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/oculosdanilo/wimage"))
  val intentDanilo = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/oculosdanilo"))
  val navigationBarHeight: Dp =
    (WindowInsets.navigationBars.getBottom(LocalDensity.current) / LocalDensity.current.density).dp
  
  ModalDrawerSheet(windowInsets = WindowInsets(0, 0, 0, 0)) {
    Header(ctx)
    
    Spacer(Modifier.height(75.dp))
    
    NavigationDrawerItem(
      label = { Text(stringResource(R.string.home)) },
      icon = {
        Icon(
          if (routeAtual == "$HomeRoute") {
            rememberHomeFilled()
          } else {
            rememberHome()
          },
          contentDescription = stringResource(R.string.home)
        )
      },
      selected = routeAtual == "$HomeRoute",
      onClick = onNavegarPraHome,
      modifier = Modifier.padding(horizontal = 12.dp)
    )
    NavigationDrawerItem(
      label = { Text(stringResource(R.string.config)) },
      icon = {
        Icon(
          if (routeAtual == "$ConfigRoute") {
            rememberSettingsFilled()
          } else {
            rememberSettings()
          },
          contentDescription = stringResource(R.string.config)
        )
      },
      selected = routeAtual == "$ConfigRoute",
      onClick = onNavegarPraConfig,
      modifier = Modifier.padding(horizontal = 12.dp)
    )
    
    HorizontalDivider(Modifier.padding(horizontal = 28.dp, vertical = 8.dp))
    
    NavigationDrawerItem(
      label = { Text(stringResource(R.string.github_rep)) }, icon = {
        Icon(painterResource(R.drawable.ic_github_repo), stringResource(R.string.github_rep))
      },
      selected = false,
      onClick = { ctx.startActivity(intentRep) },
      modifier = Modifier.padding(horizontal = 12.dp)
    )
    NavigationDrawerItem(
      label = { Text(stringResource(R.string.github_danilo)) },
      icon = { FaIcon(FaIcons.Github, tint = MaterialTheme.colorScheme.onSurfaceVariant) },
      selected = false,
      onClick = { ctx.startActivity(intentDanilo) },
      modifier = Modifier.padding(horizontal = 12.dp)
    )
    
    Spacer(Modifier.weight(1.0f))
    
    Column(Modifier.padding(horizontal = 28.dp, vertical = navigationBarHeight + 10.dp)) {
      Text(
        "© ${Year.now()} oculosdanilo",
        Modifier.alpha(0.5f),
        color = MaterialTheme.colorScheme.onSurfaceVariant
      )
      Text(
        stringResource(R.string.copyright),
        Modifier.alpha(0.5f),
        color = MaterialTheme.colorScheme.onSurfaceVariant
      )
    }
  }
}

@Composable
private fun Header(ctx: Context) {
  Box {
    Image(
      painterResource(R.drawable.danilo),
      "Danilo",
      modifier = Modifier
        .fillMaxWidth()
        .height(150.dp + (WindowInsets.statusBars.getTop(Density(ctx)) / Density(ctx).density).dp)
        .blur(75.dp),
      contentScale = ContentScale.Crop
    )
    Image(
      painterResource(R.drawable.danilo),
      "Danilo",
      Modifier
        .align(Alignment.BottomStart)
        .offset(25.dp, 50.dp)
        .size(100.dp)
        .clip(CircleShape)
        .border(1.dp, Color.White, CircleShape)
    )
  }
}