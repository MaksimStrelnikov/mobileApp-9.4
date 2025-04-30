package dev.tp_94.mobileapp.customersfeed.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import dev.tp_94.mobileapp.R
import dev.tp_94.mobileapp.core.themes.TextStyles

@Composable
fun SearchInput(
    text: String,
    defaultText: String,
    backgroundColor: Color,
    onChange: (String) -> Unit,
    onSearch: () -> Unit
) {
    var currentText by remember { mutableStateOf(text) }
    BasicTextField(
        value = currentText,
        onValueChange = {
            currentText = it
            onChange(it)
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearch()
            }
        ),
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
                Row {
                    if (currentText.isEmpty()) {
                        Text(
                            defaultText,
                            style = TextStyles.regular(colorResource(R.color.middle_text)),
                        )
                    }
                    innerTextField()
                    Image(
                        painter = painterResource(R.drawable.search),
                        contentDescription = "Поиск",
                        modifier = Modifier
                            .weight(0.1f)
                    )
                }
            }
        }
    )
}