package dev.tp_94.mobileapp.orderpayment.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.tp_94.mobileapp.R
import dev.tp_94.mobileapp.core.themes.TextStyles

@Composable
fun CardInputField(
    number: String,
    expiration: String,
    cvcCode: String,
    onNumberChange: (String) -> Unit,
    onExpirationChange: (String) -> Unit,
    onCvcCodeChange: (String) -> Unit
) {
    var numberState by remember { mutableStateOf(number) }
    var expirationState by remember { mutableStateOf(expiration) }
    var cvcState by remember { mutableStateOf(cvcCode) }

    val numberFocus = remember { FocusRequester() }
    val expirationFocus = remember { FocusRequester() }
    val cvcFocus = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    Column(modifier = Modifier.fillMaxWidth()) {
        CardField(
            value = numberState,
            onValueChange = {
                if (it.length <= 16) {
                    numberState = it
                    onNumberChange(it)
                    if (it.length == 16) expirationFocus.requestFocus()
                }
            },
            hint = "Номер карты",
            visualTransformation = CardNumberVisualTransformation("0000 0000 0000 0000", '0'),
            imeAction = ImeAction.Next,
            focusRequester = numberFocus
        )
        Row(Modifier.fillMaxWidth()) {
            CardField(
                value = expirationState,
                onValueChange = {
                    if (it.length <= 4) {
                        expirationState = it
                        onExpirationChange(it)
                        if (it.length == 4) cvcFocus.requestFocus()
                    }
                },
                hint = "ММ/ГГ",
                visualTransformation = CardNumberVisualTransformation("00/00", '0'),
                imeAction = ImeAction.Next,
                modifier = Modifier.weight(1f),
                focusRequester = expirationFocus
            )
            Spacer(modifier = Modifier.width(8.dp))
            CardField(
                value = cvcState,
                onValueChange = {
                    if (it.length <= 3) {
                        cvcState = it
                        onCvcCodeChange(it)
                        if (it.length == 3) {
                            focusManager.clearFocus()
                        }
                    }
                },
                hint = "CVC/CVV",
                visualTransformation = CardNumberVisualTransformation("000", '0'),
                imeAction = ImeAction.Done,
                modifier = Modifier.weight(1f),
                focusRequester = cvcFocus
            )
        }
    }
}

@Preview
@Composable
fun PreviewCardInputField() {
    CardInputField(
        number = "",
        expiration = "",
        cvcCode = "",
        onNumberChange = {  },
        onExpirationChange = {  },
        onCvcCodeChange = {  }
    )
}

@Composable
private fun CardField(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    visualTransformation: VisualTransformation,
    imeAction: ImeAction,
    modifier: Modifier = Modifier,
    focusRequester: FocusRequester
) {
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    Box(
        modifier = modifier
            .focusRequester(focusRequester)
            .padding(vertical = 8.dp)
            .background(
                color = colorResource(R.color.dark_background),
                shape = RoundedCornerShape(8.dp)
            )
            .height(48.dp)
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        if (value.isEmpty() && !isFocused) {
            Text(
                text = hint,
                style = TextStyles.regular(colorResource(R.color.light_text))
            )
        }
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            visualTransformation = visualTransformation,
            textStyle = TextStyles.regular(colorResource(R.color.dark_text)),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = imeAction),
            interactionSource = interactionSource,
            modifier = Modifier.fillMaxWidth(),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Next) },
                onDone = { focusManager.clearFocus() }
            )
        )
    }
}

class CardNumberVisualTransformation(val mask: String, val maskNumber: Char) :
    VisualTransformation {

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

        return TransformedText(annotatedString, CardNumberOffsetMapper(mask, maskNumber))
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CardNumberVisualTransformation) return false
        if (mask != other.mask) return false
        if (maskNumber != other.maskNumber) return false
        return true
    }

    override fun hashCode(): Int {
        return mask.hashCode()
    }
}

private class CardNumberOffsetMapper(val mask: String, val numberChar: Char) : OffsetMapping {

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