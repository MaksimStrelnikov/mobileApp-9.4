package dev.tp_94.mobileapp.core.themes


import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import dev.tp_94.mobileapp.R

object TextStyles {

    @Composable
    fun regular(
        color: Color = colorResource(R.color.middle_text),
        fontSize: TextUnit = 16.sp
    ): TextStyle {
        return TextStyle(
            fontFamily = Fonts.robotoSlab,
            fontWeight = FontWeight.Normal,
            letterSpacing = 0.sp,
            fontSize = fontSize,
            lineHeight = fontSize,
            color = color
        )
    }

    @Composable
    fun regularNoColor(fontSize: TextUnit = 16.sp): TextStyle {
        return TextStyle(
            fontFamily = Fonts.robotoSlab,
            fontWeight = FontWeight.Normal,
            letterSpacing = 0.sp,
            fontSize = fontSize,
            lineHeight = fontSize
        )

    }


    @Composable
    fun header(
        color: Color = colorResource(R.color.middle_text),
        fontSize: TextUnit = 24.sp
    ): TextStyle {

        return TextStyle(
            fontFamily = Fonts.robotoSlab,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 0.2.sp,
            fontSize = fontSize,
            lineHeight = fontSize,
            color = color
        )

    }


    @Composable
    fun secondHeader(
        color: Color = colorResource(R.color.middle_text),
        fontSize: TextUnit = 20.sp
    ): TextStyle {
        return TextStyle(
            fontFamily = Fonts.robotoSlab,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 0.5.sp,
            fontSize = fontSize,
            lineHeight = 24.sp,
            color = color
        )

    }


    @Composable
    fun button(
        color: Color = colorResource(R.color.middle_text),
        fontSize: TextUnit = 14.sp
    ): TextStyle {
        return TextStyle(
            fontFamily = Fonts.robotoSlab,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 0.2.sp,
            fontSize = fontSize,
            lineHeight = 24.sp,
            color = color
        )

    }
}