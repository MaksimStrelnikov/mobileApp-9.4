package dev.tp_94.mobileapp.add_product.presentation.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import dev.tp_94.mobileapp.R
import dev.tp_94.mobileapp.core.themes.ActiveButton
import dev.tp_94.mobileapp.core.themes.SquareBox
import dev.tp_94.mobileapp.core.themes.TextStyles

@Composable
fun CakeImageAddition(onAdd: (Uri?) -> Unit, imageUri: Uri? = null) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        onAdd(uri)
    }
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
        Spacer(modifier = Modifier.height(8.dp))
        ActiveButton(
            onClick = { launcher.launch("image/*") },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth().height(48.dp)
        ) {
            Text(
                "Выбрать изображение",
                style = TextStyles.button(colorResource(R.color.light_background))
            )
        }
    }
}

@Preview
@Composable
fun CakeImageAdditionPreview() {
    CakeImageAddition(onAdd = {})
}