package com.aneury1.mytodos.UI

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(

   primary = purple200,
   primaryVariant = purple700,
    secondary = teal200

)

private val LightColorPalette = darkColors(

    primary = purple200,
    primaryVariant = purple700,
    secondary = teal200

)



@Composable
fun AppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() ()-> Unit){
    val colors = if(darkTheme){
        DarkColorPalette
    }else{
        LightColorPalette
    }
    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}