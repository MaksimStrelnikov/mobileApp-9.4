package dev.tp_94.mobileapp.self_made_cake.presentation.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import dev.tp_94.mobileapp.R
import dev.tp_94.mobileapp.core.themes.ActiveButton
import dev.tp_94.mobileapp.core.themes.SquareBox
import dev.tp_94.mobileapp.core.themes.TextStyles

@Composable
fun ImageAddition(onAdd: (Uri?) -> Unit, imageUri: Uri? = null) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        onAdd(uri)
    }
    Box(
        modifier = Modifier
            .background(
                colorResource(R.color.light_background), shape = RoundedCornerShape(8.dp)
            )
            .width(360.dp)
            .wrapContentHeight()
            .padding(19.dp, 17.dp)
    ) {
        Column (
            modifier = Modifier
                .padding(20.dp, 0.dp),
            horizontalAlignment = Alignment.CenterHorizontally
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
                if (imageUri == null) {
                    val painter = rememberAsyncImagePainter(R.drawable.no_image)
                    Image(
                        painter = painter,
                        contentDescription = "Выбранное изображение",
                        modifier = Modifier
                            .size(40.dp)
                    )
                }
                imageUri?.let { uri ->
                    val painter = rememberAsyncImagePainter(uri)
                    Image(
                        painter = painter,
                        contentDescription = "Выбранное изображение",
                        modifier = Modifier
                            .size(200.dp)
                    )
                }
            }
            Spacer(Modifier.height(8.dp))
            ActiveButton(
                onClick = { launcher.launch("image/*") },
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    "Выбрать изображение",
                    style = TextStyles.button(colorResource(R.color.light_background))
                )
            }
        }
    }
}

@Composable
fun InteractableImage(
    imageUri: Uri?,
    imageOffset: Offset,
    onOffsetChanged: (Offset) -> Unit
) {
    val currentOffset by rememberUpdatedState(imageOffset)
    imageUri?.let { uri ->
        val painter = rememberAsyncImagePainter(uri)
        Image(
            painter = painter,
            contentDescription = "Выбранное изображение",
            modifier = Modifier
                .size(200.dp)
                .offset { IntOffset(currentOffset.x.toInt(), currentOffset.y.toInt()) }
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        onOffsetChanged(currentOffset + dragAmount)
                    }
                }
        )
    }
}