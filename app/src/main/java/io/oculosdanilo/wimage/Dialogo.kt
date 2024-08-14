package io.oculosdanilo.wimage

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import io.oculosdanilo.wimage.composable_icons.rememberInfo

@Composable
fun DialogoPermissao(
  onCancelar: () -> Unit,
  onConfirmar: () -> Unit
) {
  
  AlertDialog(
    title = { Text(stringResource(R.string.permDialogTitle)) },
    text = { Text(stringResource(R.string.permDialogConteudo)) },
    icon = { Icon(rememberInfo(), contentDescription = "info") },
    confirmButton = {
      Button(onClick = { onConfirmar() }) { Text(stringResource(R.string.permDialogOK)) }
    },
    dismissButton = {
      TextButton(onClick = { onCancelar() }) { Text(stringResource(R.string.permDialogFechar)) }
    },
    onDismissRequest = {
      onCancelar()
    }
  )
}