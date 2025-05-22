package dev.tp_94.mobileapp.order_view.presentation.components

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import dev.tp_94.mobileapp.R
import dev.tp_94.mobileapp.core.themes.TextStyles

@Composable
fun PhoneText(
    phoneNumber: String,
    color: Color = colorResource(R.color.dark_text),
    fontSize: TextUnit = 16.sp
) {
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current

    Text(
        text = phoneNumber,
        style = TextStyles.regular(
            color = color,
            fontWeight = FontWeight.SemiBold,
            fontSize = fontSize,
            textDecoration = TextDecoration.Underline
        ),
        modifier = Modifier
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        val intent = Intent(Intent.ACTION_DIAL).apply {
                            data = Uri.parse("tel:$phoneNumber")
                        }
                        context.startActivity(intent)
                    },
                    onLongPress = {
                        clipboardManager.setText(AnnotatedString(phoneNumber))
                        Toast.makeText(context, "Скопировано в буфер обмена", Toast.LENGTH_SHORT).show()
                    }
                )
            }
    )
}
