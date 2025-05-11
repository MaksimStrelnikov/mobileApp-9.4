package dev.tp_94.mobileapp.customers_feed.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.tp_94.mobileapp.R
import dev.tp_94.mobileapp.core.themes.TextStyles
import dev.tp_94.mobileapp.customers_feed.presentation.Sorting

@Composable
fun <T> SortSelector(
    currentSort: T,
    options: List<T>,
    onSortSelected: (T) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp, start = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Сортировка",
                    textAlign = TextAlign.Start,
                    style = TextStyles.regular(colorResource(R.color.dark_text)),
                    modifier = Modifier
                        .padding(end = 12.dp)
                )
                Text(
                    text = currentSort.toString(),
                    style = TextStyles.regular(colorResource(R.color.dark_text)),
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .weight(1f, true)
                )
                IconButton(
                    onClick = { expanded = true }
                ) {
                    Icon(
                        if (expanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                        null,
                        tint = colorResource(R.color.dark_text)
                    )
                }
            }
            Box(
                modifier = Modifier
                    .align(alignment = Alignment.End)
            ) {
                DropdownMenu(
                    modifier = Modifier
                        .background(color = colorResource(R.color.light_background)),
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    options.forEach {
                        DropdownMenuItem(
                            text = { Text(text = it.toString(), style = TextStyles.regular()) },
                            onClick = {
                                onSortSelected(it)
                                expanded = false
                            },
                            colors = MenuDefaults.itemColors(
                                textColor = colorResource(R.color.dark_text),
                                leadingIconColor = colorResource(R.color.dark_background),
                                trailingIconColor = colorResource(R.color.light_background),
                            )
                        )
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun PreviewSortSelector() {
    SortSelector(
        currentSort = Sorting.BY_CAKES_UP,
        options = Sorting.entries
    ) { }
}
