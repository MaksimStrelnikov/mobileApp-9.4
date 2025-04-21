package dev.tp_94.mobileapp.login.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import dev.tp_94.mobileapp.R
import dev.tp_94.mobileapp.core.themes.TextStyles

@Composable
fun PasswordTextEditor(onChange: (String) -> Unit, text: String) {
    var password by remember { mutableStateOf(text) }
    BasicTextField(
        value = password,
        onValueChange = {
            password = it
            onChange(it)
        },
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        singleLine = true,
        modifier = Modifier
            .background(
                colorResource(R.color.light_background),
                shape = RoundedCornerShape(8.dp)
            )
            .fillMaxWidth()
            .height(48.dp),
        textStyle = TextStyles.regular(colorResource(R.color.middle_text)),
        decorationBox = { innerTextField ->
            Box(
                Modifier.fillMaxWidth().padding(33.dp, 17.dp, 33.dp, 0.dp)
            ) {
                if (password.isEmpty()) {
                    Text(
                        "Пароль",
                        style = TextStyles.regular(colorResource(R.color.light_text)),
                    )
                }
                innerTextField()
            }
        }
    )
}