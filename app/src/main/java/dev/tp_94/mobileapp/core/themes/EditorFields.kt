package dev.tp_94.mobileapp.core.themes

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.tp_94.mobileapp.R
import dev.tp_94.mobileapp.login.presentation.components.PhoneVisualTransformation


@Composable
fun SpecialTextField(
    text: String,
    onChange: (String) -> Unit,
    defaultText: String,
    backgroundColor: Color = colorResource(R.color.dark_background),
    keyboardType: KeyboardType = KeyboardType.Text,
    singleLine: Boolean = true,
    height: Dp = 48.dp,
    inputFilter: (String) -> String = { it },
    visualTransformation: VisualTransformation = VisualTransformation.None,
    maxLength: Int? = null,
    contentAlignment: Alignment = Alignment.CenterStart,
    border: BorderStroke? = null
) {
    var localText by remember(text) {
        mutableStateOf(
            if (maxLength != null) inputFilter(text).take(maxLength)
            else inputFilter(text)
        )
    }

    LaunchedEffect(text) {
        val filtered = inputFilter(text)
        val limited = if (maxLength != null) filtered.take(maxLength) else filtered
        if (limited != localText) {
            localText = limited
        }
    }

    BasicTextField(
        value = localText,
        onValueChange = { newValue ->
            val filteredNewValue = inputFilter(newValue)
            localText = if (maxLength != null) filteredNewValue.take(maxLength) else filteredNewValue
            onChange(localText)
        },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        singleLine = singleLine,
        modifier = Modifier
            .background(backgroundColor, shape = RoundedCornerShape(8.dp))
            .then(if (border != null) Modifier.border(border, RoundedCornerShape(8.dp)) else Modifier)
            .fillMaxWidth()
            .height(height),
        textStyle = TextStyles.regular(colorResource(R.color.middle_text)),
        visualTransformation = visualTransformation,
        decorationBox =  { innerTextField ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp, 8.dp),
                contentAlignment = contentAlignment
            ) {
                if (localText.isEmpty()) {
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

@Composable
fun ValidatedTextField(
    text: String,
    onChange: (String) -> Unit,
    defaultText: String,
    backgroundColor: Color,
    keyboardType: KeyboardType = KeyboardType.Text,
    validator: (String) -> String? = { null },
    errorColor: Color = colorResource(R.color.accent),
    inputFilter: (String) -> String = { it },
    singleLine: Boolean = true,
    height: Dp = 48.dp,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    contentAlignment: Alignment = Alignment.CenterStart,
    maxLength: Int? = null
) {
    var error by remember { mutableStateOf("") }

    Column {
        SpecialTextField(
            text = text,
            onChange = {
                onChange(it)
                error = validator(it) ?: ""
            },
            defaultText = defaultText,
            backgroundColor = backgroundColor,
            keyboardType = keyboardType,
            singleLine = singleLine,
            height = height,
            visualTransformation = visualTransformation,
            contentAlignment = contentAlignment,
            maxLength = maxLength,
            inputFilter = inputFilter,
            border = if (error.isNotEmpty()) BorderStroke(2.dp, errorColor) else null
        )

        if (error.isNotEmpty()) {
            Text(
                error,
                style = TextStyles.regular(errorColor)
            )
        }
    }
}

@Composable
fun DescriptionEditor(
    text: String,
    onChange: (String) -> Unit,
    height: Dp = 70.dp,
    defaultText: String = "Описание",
    backgroundColor: Color = colorResource(R.color.dark_background),
    contentAlignment: Alignment = Alignment.TopStart,
    maxLength: Int? = 400
) {
    ValidatedTextField(
        text = text,
        onChange = onChange,
        defaultText = defaultText,
        backgroundColor = backgroundColor,
        keyboardType = KeyboardType.Text,
        singleLine = false,
        height = height,
        contentAlignment = contentAlignment,
        maxLength = maxLength,
        validator = {
            if (maxLength != null && it.length > maxLength) {
                "Максимальная длина описания - $maxLength символов"
            } else null
        }
    )
}

@Composable
fun NumberEditor(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    height: Dp = 48.dp,
    backgroundColor: Color = colorResource(R.color.dark_background),
    maxLength: Int? = 10,
    necessary: Boolean = false
) {
    ValidatedTextField(
        text = value,
        onChange = onValueChange,
        defaultText = label,
        backgroundColor = backgroundColor,
        keyboardType = KeyboardType.Number,
        singleLine = true,
        height = height,
        inputFilter = { it.filter { c -> c.isDigit() } },
        maxLength = maxLength,
        validator = {
            if (maxLength != null && it.length > maxLength) {
                "Максимальная длина - $maxLength символов"
            } else if (necessary && it.isEmpty()) "$label - обязательное поле" else null
        }
    )
}

@Composable
fun PriceEditor(
    text: String,
    onChange: (String) -> Unit,
    defaultText: String = "Цена изделия",
    backgroundColor: Color = colorResource(R.color.dark_background),
    maxLength: Int? = 10
) {
    ValidatedTextField(
        text = text,
        onChange = { newValue ->
            onChange(newValue)
        },
        defaultText = defaultText,
        backgroundColor = backgroundColor,
        keyboardType = KeyboardType.Number,
        inputFilter = { it.filter { c -> c.isDigit() } },
        maxLength = maxLength,
        validator = {
            if (maxLength != null && it.length > maxLength) {
                "Максимальная длина цены - $maxLength цифр"
            } else if (it.isEmpty()) "$defaultText - обязательное поле" else null
        }
    )
}

@Composable
fun WeightEditor(
    text: String,
    onChange: (String) -> Unit,
    defaultText: String = "Вес (в кг)",
    backgroundColor: Color = colorResource(R.color.dark_background),
    maxLength: Int? = 6
) {
    ValidatedTextField(
        text = text,
        onChange = onChange,
        defaultText = defaultText,
        backgroundColor = backgroundColor,
        keyboardType = KeyboardType.Decimal,
        height = 48.dp,
        singleLine = true,
        inputFilter = { newText ->
            val replaced = newText.replace(',', '.')
            val hasOneDot = replaced.count { it == '.' } <= 1
            val notOnlyDot = replaced != "."
            val filtered = replaced.filter { it.isDigit() || it == '.' }
            if (hasOneDot && notOnlyDot) filtered else text
        },
        maxLength = maxLength,
        validator = {
            if (maxLength != null && it.length > maxLength) {
                "Максимальная длина веса - $maxLength символов"
            } else if (it.isEmpty()) "$defaultText - обязательное поле" else null
        }
    )
}

@Composable
fun PhoneEditor(
    text: String,
    onChange: (String) -> Unit,
    backgroundColor: Color = colorResource(R.color.light_background)
) {
    ValidatedTextField(
        text = text,
        onChange = onChange,
        defaultText = "Номер телефона: +7 000 000-00-00",
        backgroundColor = backgroundColor,
        keyboardType = KeyboardType.Phone,
        inputFilter = { it.filter { c -> c.isDigit() } },
        visualTransformation = PhoneVisualTransformation("+7 000 000-00-00", '0'),
        maxLength = 10,
        validator = {
            if (it.length < 10) {
                "Номер должен содержать 10 цифр"
            } else if (it.length > 10) {
                "Максимальная длина номера - 10 цифр"
            } else null
        }
    )
}

@Composable
fun PasswordTextEditor(
    text: String,
    onChange: (String) -> Unit,
    backgroundColor: Color = colorResource(R.color.light_background),
    maxLength: Int? = 30
) {
    ValidatedTextField(
        text = text,
        onChange = onChange,
        defaultText = "Пароль",
        backgroundColor = backgroundColor,
        keyboardType = KeyboardType.Password,
        visualTransformation = PasswordVisualTransformation(),
        maxLength = maxLength,
        validator = {
            if (it.isEmpty()) {
                "Пароль не может быть пустым"
            } else if (maxLength != null && it.length > maxLength) {
                "Максимальная длина пароля - $maxLength символов"
            } else null
        }
    )
}

@Composable
fun EmailEditor(
    text: String,
    onChange: (String) -> Unit,
    backgroundColor: Color = colorResource(R.color.light_background),
    maxLength: Int? = 30
) {
    ValidatedTextField(
        text = text,
        onChange = onChange,
        defaultText = "Электронная почта",
        backgroundColor = backgroundColor,
        keyboardType = KeyboardType.Email,
        validator = {
            if (it.isNotEmpty() && !android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches()) {
                "Некорректный вид адреса"
            } else if (maxLength != null && it.length > maxLength) {
                "Максимальная длина email - $maxLength символов"
            } else null
        },
        maxLength = maxLength
    )
}

@Composable
fun NameEditor(
    text: String,
    onChange: (String) -> Unit,
    defaultText: String = "Имя",
    backgroundColor: Color = colorResource(R.color.light_background),
    maxLength: Int? = 30
) {
    ValidatedTextField(
        text = text,
        onChange = onChange,
        defaultText = defaultText,
        backgroundColor = backgroundColor,
        validator = {
            if (it.isEmpty()) "$defaultText - обязательное поле"
            else if (maxLength != null && it.length > maxLength) "Максимальная длина - $maxLength символов"
            else null
        },
        maxLength = maxLength
    )
}

@Composable
fun AddressEditor(
    text: String,
    onChange: (String) -> Unit,
    backgroundColor: Color = colorResource(R.color.light_background),
    maxLength: Int? = 100
) {
    ValidatedTextField(
        text = text,
        onChange = onChange,
        defaultText = "Адрес",
        backgroundColor = backgroundColor,
        validator = {
            if (it.isEmpty()) "Адрес - обязательное поле"
            else if (maxLength != null && it.length > maxLength) "Максимальная длина адреса - $maxLength символов"
            else null
        },
        maxLength = maxLength
    )
}

@Composable
fun FillingField(
    value: String,
    onValueChange: (String) -> Unit,
    backgroundColor: Color = colorResource(R.color.dark_background),
    maxLength: Int? = 40
) {
    ValidatedTextField(
        text = value,
        onChange = onValueChange,
        defaultText = "Начинка",
        backgroundColor = backgroundColor,
        keyboardType = KeyboardType.Text,
        singleLine = true,
        height = 48.dp,
        maxLength = maxLength,
        validator = {
            if (it.isEmpty()) {
                "Начинка не может быть пустой"
            } else if (maxLength != null && it.length > maxLength) {
                "Максимальная длина начинки - $maxLength символов"
            } else null
        }
    )
}