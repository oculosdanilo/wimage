package io.oculosdanilo.wimage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.oculosdanilo.wimage.composable_icons.rememberFolder
import io.oculosdanilo.wimage.composable_icons.rememberImage
import io.oculosdanilo.wimage.composable_icons.rememberInfo

@Composable
fun DialogoPermissao(
  onCancelar: () -> Unit,
  onConfirmar: () -> Unit
) {
  
  AlertDialog(
    title = { Text(stringResource(R.string.permDialogTitle)) },
    text = {
      Column {
        Text(stringResource(R.string.permDialogConteudo))
        Spacer(Modifier.height(16.dp))
        
        HorizontalDivider()
        
        Spacer(Modifier.height(16.dp))
        Row(Modifier.fillMaxWidth()) {
          Icon(rememberImage(), contentDescription = "")
          Spacer(Modifier.width(16.dp))
          Column(Modifier.fillMaxWidth()) {
            Text(stringResource(R.string.permDialogPerm1Title), style = TextStyle(fontWeight = FontWeight.Bold))
            Text(stringResource(R.string.permDialogPerm1Desc))
          }
        }
        Spacer(Modifier.height(16.dp))
        Row(Modifier.fillMaxWidth()) {
          Icon(rememberFolder(), contentDescription = "")
          Spacer(Modifier.width(16.dp))
          Column(Modifier.fillMaxWidth()) {
            Text(stringResource(R.string.permDialogPerm2Title), style = TextStyle(fontWeight = FontWeight.Bold))
            Text(stringResource(R.string.permDialogPerm2Desc))
          }
        }
        Spacer(Modifier.height(16.dp))
        
        HorizontalDivider()
      }
    },
    icon = { Icon(rememberInfo(), contentDescription = "info") },
    confirmButton = {
      Button(onClick = { onConfirmar() }, colors = ButtonDefaults.buttonColors()) {
        Text(stringResource(R.string.permDialogOK))
      }
    },
    dismissButton = {
      TextButton(onClick = { onCancelar() }) { Text(stringResource(R.string.permDialogFechar)) }
    },
    onDismissRequest = {
      onCancelar()
    }
  )
}