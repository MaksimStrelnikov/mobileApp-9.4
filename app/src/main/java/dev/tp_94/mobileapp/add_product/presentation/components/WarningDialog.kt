package dev.tp_94.mobileapp.add_product.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.tp_94.mobileapp.R
import dev.tp_94.mobileapp.core.themes.ActiveButton
import dev.tp_94.mobileapp.core.themes.DiscardButton
import dev.tp_94.mobileapp.core.themes.TextStyles

@Composable
fun WarningDialog(
    onDismiss: () -> Unit,
    onAccept: () -> Unit,
    title: String = "Предупреждение",
    text: String = "Вы уверены?"
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = title,
                style = TextStyles.header(colorResource(R.color.dark_text))
            )
        },
        text = {
            Text(
                text = text,
                style = TextStyles.regular(colorResource(R.color.dark_text))
            )
        },
        confirmButton = {
            DiscardButton(
                onClick = {
                    onAccept()
                    onDismiss()
                },
                modifier = Modifier
                    .height(48.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Подтвердить",
                    style = TextStyles.button(colorResource(R.color.dark_text))
                )
            }
        },
        dismissButton = {
            ActiveButton(
                onClick = onDismiss, // Просто закрываем без дополнительных действий
                modifier = Modifier
                    .height(48.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Отмена",
                    style = TextStyles.button(colorResource(R.color.light_background))
                )
            }
        },
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(12.dp).fillMaxWidth().border(
            width = 2.dp,
            color = colorResource(R.color.light_text),
            shape = RoundedCornerShape(8.dp)
        ),
        containerColor = colorResource(R.color.light_background)
    )
}

@Preview
@Composable
fun WarningDialogPreview() {
    WarningDialog(
        onDismiss = {},
        onAccept = {}
    )
}