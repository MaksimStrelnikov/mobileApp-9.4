package dev.tp_94.mobileapp.core

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.ui.graphics.Color
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream

fun Color.darken(factor: Float = 0.75f): Color {
    return Color(
        (red * factor * 255).toInt(),
        (green * factor * 255).toInt(),
        (blue * factor * 255).toInt()
    )
}

fun Context.uriToRequestBody(uri: Uri, mimeType: String): RequestBody {
    val inputStream = contentResolver.openInputStream(uri)
        ?: throw IllegalArgumentException("Can't open input stream for URI: $uri")
    return inputStream.readBytes().toRequestBody(mimeType.toMediaTypeOrNull())
}

fun Context.compressImageToMaxSize(uri: Uri, maxBytes: Int): ByteArray {
    val inputStream = contentResolver.openInputStream(uri)
        ?: throw IllegalArgumentException("Can't open stream for URI: $uri")

    var bitmap = BitmapFactory.decodeStream(inputStream)
    var quality = 100
    var stream: ByteArrayOutputStream
    var result: ByteArray

    do {
        stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream)
        result = stream.toByteArray()
        quality -= 5
    } while (result.size > maxBytes && quality > 5)

    // Дополнительно: уменьшаем размер изображения, если не помогает
    while (result.size > maxBytes) {
        bitmap = Bitmap.createScaledBitmap(
            bitmap,
            (bitmap.width * 0.9).toInt(),
            (bitmap.height * 0.9).toInt(),
            true
        )
        stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream)
        result = stream.toByteArray()
    }

    return result
}

