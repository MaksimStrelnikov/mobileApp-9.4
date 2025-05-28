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
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import dev.tp_94.mobileapp.R


@Composable
fun SpecialTextField(
    modifier: Modifier = Modifier,
    text: String,
    onChange: (String) -> Unit,
    defaultText: String,
    backgroundColor: Color = colorResource(R.color.dark_background),
    keyboardType: KeyboardType = KeyboardType.Text,
    singleLine: Boolean = true,
    inputFilter: (String) -> String = { it },
    visualTransformation: VisualTransformation = VisualTransformation.None,
    maxLength: Int? = null,
    contentAlignment: Alignment = Alignment.CenterStart,
    border: BorderStroke? = null
) {
    var localText by remember { mutableStateOf(processText(text, inputFilter, maxLength)) }

    LaunchedEffect(text) {
        val processed = processText(text, inputFilter, maxLength)
        if (processed != localText) localText = processed
    }

    BasicTextField(
        value = localText,
        onValueChange = { newValue ->
            val processed = processText(newValue, inputFilter, maxLength)
            localText = processed
            onChange(processed)
        },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        singleLine = singleLine,
        modifier = modifier.fillMaxWidth()
            .height(48.dp)
            .background(backgroundColor, shape = RoundedCornerShape(8.dp))
            .then(if (border != null) Modifier.border(border, RoundedCornerShape(8.dp))
            else Modifier),
        textStyle = TextStyles.regular(colorResource(R.color.middle_text)),
        visualTransformation = visualTransformation,
        decorationBox =  { innerTextField ->
            Box(
                modifier = Modifier
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


private fun processText(text: String, filter: (String) -> String, maxLength: Int?): String {
    return filter(text).let { if (maxLength != null) it.take(maxLength) else it }
}

@Composable
fun ValidatedTextField(
    modifier: Modifier = Modifier,
    text: String,
    onChange: (String) -> Unit,
    defaultText: String,
    backgroundColor: Color,
    keyboardType: KeyboardType = KeyboardType.Text,
    validator: (String) -> String? = { null },
    errorColor: Color = colorResource(R.color.accent),
    inputFilter: (String) -> String = { it },
    singleLine: Boolean = true,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    contentAlignment: Alignment = Alignment.CenterStart,
    maxLength: Int? = null,
) {
    var error by remember { mutableStateOf("") }
    var hiddenError by remember { mutableStateOf("") }
    var hasFocus by remember { mutableStateOf(false) }
    var isTouched by remember { mutableStateOf(false) }


    Column {
        SpecialTextField(
            text = text,
            onChange = {
                onChange(it)
                hiddenError = validator(it) ?: ""
            },
            defaultText = defaultText,
            backgroundColor = backgroundColor,
            keyboardType = keyboardType,
            singleLine = singleLine,
            modifier = modifier
                .onFocusChanged { focusState ->
                    val wasFocused = hasFocus
                    hasFocus = focusState.isFocused

                    if (wasFocused && !hasFocus) {
                        isTouched = true
                        error = hiddenError
                    } else if (!wasFocused && hasFocus) {
                        error = ""
                    }
                },
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
    modifier: Modifier = Modifier,
    text: String,
    onChange: (String) -> Unit,
    defaultText: String = "Описание",
    backgroundColor: Color = colorResource(R.color.dark_background),
    contentAlignment: Alignment = Alignment.TopStart,
    maxLength: Int? = 400
) {
    SpecialTextField(
        text = text,
        onChange = onChange,
        defaultText = defaultText,
        backgroundColor = backgroundColor,
        keyboardType = KeyboardType.Text,
        singleLine = false,
        contentAlignment = contentAlignment,
        maxLength = maxLength,
        modifier = modifier,
    )
}

@Composable
fun NumberEditor(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    backgroundColor: Color = colorResource(R.color.dark_background),
    maxLength: Int? = 10,
    necessary: Boolean = false,
) {
    ValidatedTextField(
        text = value,
        onChange = onValueChange,
        defaultText = label,
        backgroundColor = backgroundColor,
        keyboardType = KeyboardType.Number,
        singleLine = true,
        modifier = modifier,
        inputFilter = { it.filter { c -> c.isDigit() } },
        maxLength = maxLength,
        validator = {
            if (necessary && it.isEmpty()) "$label - обязательное поле" else null
        }
    )
}

@Composable
fun FloatNumberEditor(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    backgroundColor: Color = colorResource(R.color.dark_background),
    necessary: Boolean = false,
) {
    ValidatedTextField(
        text = value,
        onChange = onValueChange,
        defaultText = label,
        backgroundColor = backgroundColor,
        keyboardType = KeyboardType.Decimal,
        modifier = modifier,
        singleLine = true,
        inputFilter = { newText ->
            val replaced = newText.replace(',', '.')
            val hasOneDot = replaced.count { it == '.' } <= 1
            val notOnlyDot = replaced != "."
            val filtered = replaced.filter { it.isDigit() || it == '.' }
            if (hasOneDot && notOnlyDot) filtered else value
        },
        maxLength = if (value.isNotEmpty() && value[value.length - 1] == '.') 3 else 4,
        validator = {
            if (it.isEmpty() && necessary) "$label - обязательное поле" else null
        }
    )
}

@Composable
fun PriceEditor(
    modifier: Modifier = Modifier,
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
        modifier = modifier,
        inputFilter = { it.filter { c -> c.isDigit() } },
        maxLength = maxLength,
        validator = {
            if (it.isEmpty()) "$defaultText - обязательное поле" else null
        }
    )
}

@Composable
fun WeightEditor(
    modifier: Modifier = Modifier,
    text: String,
    onChange: (String) -> Unit,
    defaultText: String = "Вес (в кг)",
    backgroundColor: Color = colorResource(R.color.dark_background)
) {
    FloatNumberEditor(
        modifier = modifier,
        value = text,
        onValueChange = onChange,
        label = defaultText,
        backgroundColor = backgroundColor
    )
}

@Composable
fun PasswordTextEditor(
    modifier: Modifier = Modifier,
    text: String,
    onChange: (String) -> Unit,
    backgroundColor: Color = colorResource(R.color.light_background),
    maxLength: Int? = 30
) {
    ValidatedTextField(
        modifier = modifier,
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
            } else null
        }
    )
}

@Composable
fun EmailEditor(
    modifier: Modifier = Modifier,
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
        modifier = modifier,
        validator = {
            if (it.isNotEmpty() && !android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches()) {
                "Некорректный вид для электронной почты"
            } else null
        },
        maxLength = maxLength
    )
}

@Composable
fun NameEditor(
    modifier: Modifier = Modifier,
    text: String,
    onChange: (String) -> Unit,
    defaultText: String = "Имя",
    backgroundColor: Color = colorResource(R.color.light_background),
    maxLength: Int? = 30
) {
    ValidatedTextField(
        modifier = modifier,
        text = text,
        onChange = onChange,
        defaultText = defaultText,
        backgroundColor = backgroundColor,
        validator = {
            if (it.isEmpty()) "$defaultText - обязательное поле"
            else null
        },
        maxLength = maxLength
    )
}

@Composable
fun AddressEditor(
    modifier: Modifier = Modifier,
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
        modifier = modifier,
        validator = {
            if (it.isEmpty()) "Адрес - обязательное поле"
            else null
        },
        maxLength = maxLength
    )
}

@Composable
fun FillingField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    backgroundColor: Color = colorResource(R.color.dark_background),
    maxLength: Int? = 40
) {
    SpecialTextField(
        text = value,
        onChange = onValueChange,
        defaultText = "Начинка",
        backgroundColor = backgroundColor,
        keyboardType = KeyboardType.Text,
        singleLine = true,
        modifier = modifier,
        maxLength = maxLength
    )
}

@Composable
fun PhoneEditor(
    modifier: Modifier = Modifier,
    text: String,
    onChange: (String) -> Unit,
    backgroundColor: Color = colorResource(R.color.light_background),
) {
    ValidatedTextField(
        modifier = modifier,
        text = text,
        onChange = onChange,
        defaultText = "Номер телефона",
        backgroundColor = backgroundColor,
        keyboardType = KeyboardType.Phone,
        inputFilter = { it.filter { c -> c.isDigit() } },
        visualTransformation = PhoneVisualTransformation("+7 000 000-00-00", '0'),
        maxLength = 10,
        validator = {
            if (it.isEmpty()) "Номер телефона - обязательное поле"
            else if (it.length < 10) "Номер телефона должен состоять из 10 цифр"
            else null
        }
    )
}

class PhoneVisualTransformation(val mask: String, val maskNumber: Char) : VisualTransformation {

    private val maxLength = mask.count { it == maskNumber }

    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed = if (text.length > maxLength) text.take(maxLength) else text

        val annotatedString = buildAnnotatedString {
            if (trimmed.isEmpty()) return@buildAnnotatedString

            var maskIndex = 0
            var textIndex = 0
            while (textIndex < trimmed.length && maskIndex < mask.length) {
                if (mask[maskIndex] != maskNumber) {
                    val nextDigitIndex = mask.indexOf(maskNumber, maskIndex)
                    append(mask.substring(maskIndex, nextDigitIndex))
                    maskIndex = nextDigitIndex
                }
                append(trimmed[textIndex++])
                maskIndex++
            }
        }

        return TransformedText(annotatedString, PhoneOffsetMapper(mask, maskNumber))
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PhoneVisualTransformation) return false
        if (mask != other.mask) return false
        if (maskNumber != other.maskNumber) return false
        return true
    }

    override fun hashCode(): Int {
        return mask.hashCode()
    }
}

private class PhoneOffsetMapper(val mask: String, val numberChar: Char) : OffsetMapping {

    override fun originalToTransformed(offset: Int): Int {
        var noneDigitCount = 0
        var i = 0
        while (i < offset + noneDigitCount) {
            if (mask[i++] != numberChar) noneDigitCount++
        }
        return offset + noneDigitCount
    }

    override fun transformedToOriginal(offset: Int): Int =
        offset - mask.take(offset).count { it != numberChar }
}
