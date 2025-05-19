package dev.tp_94.mobileapp.custom_order_settings.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import dev.tp_94.mobileapp.R
import dev.tp_94.mobileapp.core.themes.TextStyles

@Composable
fun LabeledCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    label: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Checkbox(
            checked = checked, onCheckedChange = onCheckedChange,
            colors = CheckboxColors(
                checkedCheckmarkColor = colorResource(R.color.background),
                uncheckedCheckmarkColor = colorResource(R.color.background),
                checkedBoxColor = colorResource(R.color.dark_accent),
                uncheckedBoxColor = colorResource(R.color.background),
                disabledCheckedBoxColor = colorResource(R.color.middle_text),
                disabledUncheckedBoxColor = colorResource(R.color.light_text),
                disabledIndeterminateBoxColor = colorResource(R.color.middle_text),
                checkedBorderColor = colorResource(R.color.dark_accent),
                uncheckedBorderColor = colorResource(R.color.dark_accent),
                disabledBorderColor = colorResource(R.color.middle_text),
                disabledUncheckedBorderColor = colorResource(R.color.middle_text),
                disabledIndeterminateBorderColor = colorResource(R.color.middle_text),
            ),
            modifier = modifier.padding(end = 4.dp),
        )
        Text(
            text = label,
            style = TextStyles.regular(colorResource(R.color.dark_text)),
        )
    }
}