package dev.tp_94.mobileapp.selfmadecake.presentation.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import dev.tp_94.mobileapp.R
import dev.tp_94.mobileapp.core.themes.ActiveButton
import dev.tp_94.mobileapp.core.themes.TextStyles

@Composable
fun ImageAddition(onAdd: (Uri?) -> Unit) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        onAdd(uri)
    }

    ActiveButton(onClick = { launcher.launch("image/*") }) {
        Text(
            "Выбрать изображение",
            style = TextStyles.button(colorResource(R.color.light_background))
        )
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