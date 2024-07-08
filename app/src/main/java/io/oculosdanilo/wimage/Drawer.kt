package io.oculosdanilo.wimage

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIcons

@Composable
fun Drawer() {
  val ctx = LocalContext.current
  val intentRep = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/oculosdanilo/wimage"))
  val intentDanilo = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/oculosdanilo"))
  
  ModalDrawerSheet(
    windowInsets = WindowInsets(0, 0, 0, 0)
  ) {
    Header(ctx)
    
    Spacer(Modifier.height(75.dp))
    
    NavigationDrawerItem(
      label = { Text(stringResource(R.string.home)) }, icon = {
        Icon(
          painter =,
          contentDescription =
        )
      }, selected = true, onClick = { /*TODO*/ })
    NavigationDrawerItem(label = { Text(stringResource(R.string.config)) }, selected = false, onClick = { /*TODO*/ })
    
    HorizontalDivider()
    
    NavigationDrawerItem(
      label = { Text(stringResource(R.string.github_rep)) },
      icon = {
        Icon(
          painterResource(R.drawable.ic_github_repo),
          stringResource(R.string.github_rep),
        )
      },
      selected = false,
      onClick = { ctx.startActivity(intentRep) },
      modifier = Modifier.padding(horizontal = 12.dp)
    )
    NavigationDrawerItem(
      label = { Text(stringResource(R.string.github_danilo)) },
      icon = { FaIcon(FaIcons.Github, tint = MaterialTheme.colorScheme.onSurface) },
      selected = false,
      onClick = { ctx.startActivity(intentDanilo) },
      modifier = Modifier.padding(horizontal = 12.dp)
    )
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
      painterResource(R.drawable.danilo), "Danilo",
      Modifier
        .align(Alignment.BottomStart)
        .offset(25.dp, 50.dp)
        .size(100.dp)
        .clip(CircleShape)
        .border(1.dp, Color.White, CircleShape)
    )
  }
}