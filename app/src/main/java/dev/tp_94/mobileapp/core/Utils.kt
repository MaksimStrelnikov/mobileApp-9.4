package dev.tp_94.mobileapp.core

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
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


fun Context.compressImageToMaxSize(uri: Uri, maxBytes: Int): ByteArray {
    var bitmap = decodeAndFixOrientation(uri, this)
    var quality = 100
    var stream: ByteArrayOutputStream
    var result: ByteArray

    do {
        stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream)
        result = stream.toByteArray()
        quality -= 5
    } while (result.size > maxBytes && quality > 5)

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

fun decodeAndFixOrientation(uri: Uri, context: Context): Bitmap {
    val inputStream = context.contentResolver.openInputStream(uri)
        ?: throw IllegalArgumentException("Can't open stream for URI: $uri")

    val bitmap = BitmapFactory.decodeStream(inputStream)

    val exifInputStream = context.contentResolver.openInputStream(uri)
        ?: return bitmap

    val exif = ExifInterface(exifInputStream)
    val orientation = exif.getAttributeInt(
        ExifInterface.TAG_ORIENTATION,
        ExifInterface.ORIENTATION_NORMAL
    )

    val matrix = Matrix()
    when (orientation) {
        ExifInterface.ORIENTATION_ROTATE_90 -> matrix.postRotate(90f)
        ExifInterface.ORIENTATION_ROTATE_180 -> matrix.postRotate(180f)
        ExifInterface.ORIENTATION_ROTATE_270 -> matrix.postRotate(270f)
    }

    return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
}


