package dev.tp_94.mobileapp.withdrawal.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.tp_94.mobileapp.R
import dev.tp_94.mobileapp.core.themes.TextStyles

@Composable
fun WithdrawalSum(
    canWithdraw: String,
    inProcess: String,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text("Можно вывести",
            style = TextStyles.secondHeader(colorResource(R.color.dark_text)))
        Spacer(Modifier.height(16.dp))
        Text(
            "$canWithdraw₽",
            style = TextStyles.header(colorResource(R.color.dark_text), fontSize = 32.sp),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(Modifier.height(16.dp))
        Text("В процессе: $inProcess₽",
            style = TextStyles.secondHeader(colorResource(R.color.light_text)))
    }
}

@Preview(showBackground = true)
@Composable
fun WithdrawalSumPreview() {
    WithdrawalSum("1000", "500")
}