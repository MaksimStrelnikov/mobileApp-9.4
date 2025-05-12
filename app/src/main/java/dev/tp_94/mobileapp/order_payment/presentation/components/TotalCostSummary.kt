package dev.tp_94.mobileapp.order_payment.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import dev.tp_94.mobileapp.core.models.CakeGeneral
import dev.tp_94.mobileapp.core.models.Confectioner
import dev.tp_94.mobileapp.core.models.Order
import dev.tp_94.mobileapp.core.themes.TextStyles

@Composable
fun TotalCostSummary(
    pool: List<CakeGeneral>
) {
    var cost = 0L
    pool.forEach {
        cost += it.price
    }
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "$cost ₽",
            style = TextStyles.header(color = colorResource(R.color.dark_text), fontSize = 32.sp),
        )
        Spacer(Modifier.height(26.dp))
        pool.forEach {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = it.name,
                    style = TextStyles.secondHeader(color = colorResource(R.color.middle_text)),
                    modifier = Modifier
                        .weight(1f)
                )
                Text(
                    text = "${it.price} ₽",
                    style = TextStyles.secondHeader(color = colorResource(R.color.middle_text))
                )
            }
        }
    }
}

@Composable
fun TotalCost(
    pool: Order
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "${pool.price} ₽",
            style = TextStyles.header(color = colorResource(R.color.dark_text), fontSize = 32.sp),
        )
        Spacer(Modifier.height(26.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = pool.cake.name,
                style = TextStyles.secondHeader(color = colorResource(R.color.middle_text)),
                modifier = Modifier
                    .weight(1f)
            )
            Text(
                text = "${pool.price} ₽",
                style = TextStyles.secondHeader(color = colorResource(R.color.middle_text))
            )
        }
    }
}

@Preview
@Composable
fun PreviewTotalCostSummary() {
    TotalCostSummary(
        pool = arrayListOf(
            CakeGeneral(
                price = 1233,
                name = "TODO()",
                description = "TODO()",
                diameter = 3f,
                weight = 3f,
                preparation = 3,
                confectioner = Confectioner(
                    id = 1,
                    name = "TODO()",
                    phoneNumber = "TODO()",
                    email = "TODO()",
                    description = "TODO()",
                    address = "TODO()"
                ),
            ),
            CakeGeneral(
                price = 12373,
                name = "Tdfgsghgf()",
                description = "TODO()",
                diameter = 3f,
                weight = 3f,
                preparation = 3,
                confectioner = Confectioner(
                    id = 1,
                    name = "TODO()",
                    phoneNumber = "TODO()",
                    email = "TODO()",
                    description = "TODO()",
                    address = "TODO()"
                )
            )
        )
    )
}
