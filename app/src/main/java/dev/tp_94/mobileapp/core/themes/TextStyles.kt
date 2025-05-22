package dev.tp_94.mobileapp.core.themes


import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import dev.tp_94.mobileapp.R

object TextStyles {

    @Composable
    fun regular(
        color: Color = colorResource(R.color.middle_text),
        fontSize: TextUnit = 16.sp,
        fontWeight: FontWeight = FontWeight.Normal,
        textDecoration: TextDecoration = TextDecoration.None
    ): TextStyle {
        return TextStyle(
            fontFamily = Fonts.robotoSlab,
            fontWeight = fontWeight,
            letterSpacing = 0.sp,
            fontSize = fontSize,
            lineHeight = fontSize,
            color = color,
            textDecoration = textDecoration
        )
    }

    @Composable
    fun regularNoColor(
        fontSize: TextUnit = 16.sp,
        fontWeight: FontWeight = FontWeight.Normal,
        textDecoration: TextDecoration = TextDecoration.None
    ): TextStyle {
        return regular(Color.Unspecified, fontSize, fontWeight, textDecoration)
    }


    @Composable
    fun header(
        color: Color = colorResource(R.color.middle_text),
        fontSize: TextUnit = 24.sp,
        fontWeight: FontWeight = FontWeight.SemiBold,
        textDecoration: TextDecoration = TextDecoration.None
    ): TextStyle {

        return TextStyle(
            fontFamily = Fonts.robotoSlab,
            fontWeight = fontWeight,
            letterSpacing = 0.2.sp,
            fontSize = fontSize,
            lineHeight = fontSize,
            color = color,
            textDecoration = textDecoration
        )

    }


    @Composable
    fun secondHeader(
        color: Color = colorResource(R.color.middle_text),
        fontSize: TextUnit = 20.sp,
        fontWeight: FontWeight = FontWeight.SemiBold,
        textDecoration: TextDecoration = TextDecoration.None
    ): TextStyle {
        return TextStyle(
            fontFamily = Fonts.robotoSlab,
            fontWeight = fontWeight,
            letterSpacing = 0.5.sp,
            fontSize = fontSize,
            lineHeight = 24.sp,
            color = color,
            textDecoration = textDecoration
        )
    }


    @Composable
    fun button(
        color: Color = colorResource(R.color.middle_text),
        fontSize: TextUnit = 15.sp,
        fontWeight: FontWeight = FontWeight.SemiBold,
        textDecoration: TextDecoration = TextDecoration.None
    ): TextStyle {
        return TextStyle(
            fontFamily = Fonts.robotoSlab,
            fontWeight = fontWeight,
            letterSpacing = 0.2.sp,
            fontSize = fontSize,
            lineHeight = 24.sp,
            color = color,
            textDecoration = textDecoration
        )

    }
}