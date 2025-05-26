package dev.tp_94.mobileapp.self_made_cake_generator.presentation.components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import dev.tp_94.mobileapp.R
import dev.tp_94.mobileapp.core.themes.SquareBox

@Composable
fun GeneratedImage(
    modifier: Modifier = Modifier,
    imageUrl: String?
) {
    Box(
        modifier = Modifier
            .background(
                colorResource(R.color.light_background), shape = RoundedCornerShape(8.dp)
            )
            .width(360.dp)
            .wrapContentHeight()
            .padding(19.dp, 17.dp)
    ) {
        SquareBox(
            modifier = Modifier
                .background(
                    color = colorResource(R.color.background),
                    shape = RoundedCornerShape(8.dp)
                )
                .border(
                    width = 4.dp,
                    color = colorResource(R.color.dark_background),
                    shape = RoundedCornerShape(8.dp),
                ),
            contentAlignment = Alignment.Center
        ) {
            if (imageUrl == null) {
                val painter = rememberAsyncImagePainter(R.drawable.no_image)
                Image(
                    painter = painter,
                    contentDescription = "Сгенерированное изображение",
                    modifier = Modifier
                        .size(40.dp)
                )
            }
            imageUrl?.let { uri ->
                val painter = rememberAsyncImagePainter(uri)
                Image(
                    painter = painter,
                    contentDescription = "Сгенерированное изображение",
                    modifier = Modifier
                        .size(200.dp)
                )
            }
        }
    }
}