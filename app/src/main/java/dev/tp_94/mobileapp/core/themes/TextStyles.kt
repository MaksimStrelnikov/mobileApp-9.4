package dev.tp_94.mobileapp.core.themes


import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import dev.tp_94.mobileapp.R

object TextStyles {
    private val regularColorCache = mutableMapOf<Color, TextStyle>()

    @Composable
    fun regular(color: Color = colorResource(R.color.middle_text)): TextStyle {
        return regularColorCache.getOrPut(color) {
            TextStyle(
                fontFamily = Fonts.robotoSlab,
                fontWeight = FontWeight.Normal,
                letterSpacing = 0.sp,
                fontSize = 14.sp,
                lineHeight = 14.sp,
                color = color
            )
        }
    }

    private val headerColorCache = mutableMapOf<Color, TextStyle>()

    @Composable
    fun header(color: Color = colorResource(R.color.middle_text)): TextStyle {
        return headerColorCache.getOrPut(color) {
            TextStyle(
                fontFamily = Fonts.robotoSlab,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.2.sp,
                fontSize = 20.sp,
                lineHeight = 20.sp,
                color = color
            )
        }
    }

    private val secondHeaderColorCache = mutableMapOf<Color, TextStyle>()

    @Composable
    fun secondHeader(color: Color = colorResource(R.color.middle_text)): TextStyle {
        return secondHeaderColorCache.getOrPut(color) {
            TextStyle(
                fontFamily = Fonts.robotoSlab,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.5.sp,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                color = color
            )
        }
    }

    private val buttonColorCache = mutableMapOf<Color, TextStyle>()

    @Composable
    fun button(color: Color = colorResource(R.color.middle_text)): TextStyle {
        return buttonColorCache.getOrPut(color) {
            TextStyle(
                fontFamily = Fonts.robotoSlab,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.2.sp,
                fontSize = 13.sp,
                lineHeight = 24.sp,
                color = color
            )
        }
    }
}