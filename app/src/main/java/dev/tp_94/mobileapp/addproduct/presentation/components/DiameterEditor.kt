package dev.tp_94.mobileapp.addproduct.presentation.components

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import dev.tp_94.mobileapp.R
import dev.tp_94.mobileapp.core.themes.TextStyles

@Composable
fun DiameterEditor(
    text: String,
    onChange: (String) -> Unit,
    defaultText: String = "Диаметр изделия (в см)",
    backgroundColor: Color = colorResource(
        R.color.dark_background
    )
) {
    var diameter by remember { mutableStateOf(text) }

    BasicTextField(
        value = diameter,
        onValueChange = { it ->
            val filtered = it.filter { it.isDigit() }
            diameter = filtered
            onChange(filtered)
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true,
        modifier = Modifier
            .background(
                backgroundColor,
                shape = RoundedCornerShape(8.dp)
            )
            .fillMaxWidth()
            .height(48.dp),
        textStyle = TextStyles.regular(colorResource(R.color.middle_text)),
        decorationBox = { innerTextField ->
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(22.dp, 0.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                if (diameter.isEmpty()) {
                    Text(
                        defaultText,
                        style = TextStyles.regular(colorResource(R.color.light_text)),
                    )
                }
                innerTextField()
            }
        }
    )
}