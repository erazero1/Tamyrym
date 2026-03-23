package core.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import core.presentation.R

internal val LocalTypography = staticCompositionLocalOf { defaultTypography() }

private val manropeFontFamily = FontFamily(
    Font(R.font.manrope)
)
private val DefaultTextStyle = TextStyle(
    fontFamily = manropeFontFamily,
)

fun compactTypography(): Typography {
    return Typography(
        headlineLarge = DefaultTextStyle.copy(fontSize = 28.sp, fontWeight = FontWeight.SemiBold),
        headlineMedium = DefaultTextStyle.copy(fontSize = 24.sp, fontWeight = FontWeight.SemiBold),
        headlineSmall = DefaultTextStyle.copy(fontSize = 20.sp, fontWeight = FontWeight.Medium),

        titleLarge = DefaultTextStyle.copy(fontSize = 18.sp, fontWeight = FontWeight.SemiBold),
        titleMedium = DefaultTextStyle.copy(fontSize = 14.sp, fontWeight = FontWeight.Medium),
        titleSmall = DefaultTextStyle.copy(fontSize = 12.sp, fontWeight = FontWeight.Medium),

        bodyLarge = DefaultTextStyle.copy(fontSize = 14.sp, fontWeight = FontWeight.Normal),
        bodyMedium = DefaultTextStyle.copy(fontSize = 12.sp, fontWeight = FontWeight.Normal),
        bodySmall = DefaultTextStyle.copy(fontSize = 10.sp, fontWeight = FontWeight.Normal),

        labelLarge = DefaultTextStyle.copy(fontSize = 12.sp, fontWeight = FontWeight.Medium),
        labelSmall = DefaultTextStyle.copy(fontSize = 9.sp, fontWeight = FontWeight.Normal)
    )
}

fun defaultTypography(): Typography {
    return Typography(
        displayLarge = DefaultTextStyle.copy(fontSize = 57.sp, fontWeight = FontWeight.Normal),
        displayMedium = DefaultTextStyle.copy(fontSize = 45.sp, fontWeight = FontWeight.Normal),
        displaySmall = DefaultTextStyle.copy(fontSize = 36.sp, fontWeight = FontWeight.Normal),

        headlineLarge = DefaultTextStyle.copy(fontSize = 32.sp, fontWeight = FontWeight.SemiBold),
        headlineMedium = DefaultTextStyle.copy(fontSize = 28.sp, fontWeight = FontWeight.SemiBold),
        headlineSmall = DefaultTextStyle.copy(fontSize = 24.sp, fontWeight = FontWeight.Medium),

        titleLarge = DefaultTextStyle.copy(fontSize = 22.sp, fontWeight = FontWeight.SemiBold),
        titleMedium = DefaultTextStyle.copy(fontSize = 16.sp, fontWeight = FontWeight.Medium),
        titleSmall = DefaultTextStyle.copy(fontSize = 14.sp, fontWeight = FontWeight.Medium),

        bodyLarge = DefaultTextStyle.copy(fontSize = 16.sp, fontWeight = FontWeight.Normal),
        bodyMedium = DefaultTextStyle.copy(fontSize = 14.sp, fontWeight = FontWeight.Normal),
        bodySmall = DefaultTextStyle.copy(fontSize = 12.sp, fontWeight = FontWeight.Normal),

        labelLarge = DefaultTextStyle.copy(fontSize = 14.sp, fontWeight = FontWeight.Medium),
        labelMedium = DefaultTextStyle.copy(fontSize = 12.sp, fontWeight = FontWeight.Medium),
        labelSmall = DefaultTextStyle.copy(fontSize = 11.sp, fontWeight = FontWeight.Normal)
    )
}

fun expandedTypography(): Typography {
    return Typography(
        headlineLarge = DefaultTextStyle.copy(fontSize = 40.sp, fontWeight = FontWeight.Bold),
        headlineMedium = DefaultTextStyle.copy(fontSize = 34.sp, fontWeight = FontWeight.SemiBold),
        headlineSmall = DefaultTextStyle.copy(fontSize = 30.sp, fontWeight = FontWeight.Medium),

        titleLarge = DefaultTextStyle.copy(fontSize = 28.sp, fontWeight = FontWeight.SemiBold),
        titleMedium = DefaultTextStyle.copy(fontSize = 22.sp, fontWeight = FontWeight.Medium),
        titleSmall = DefaultTextStyle.copy(fontSize = 18.sp, fontWeight = FontWeight.Medium),

        bodyLarge = DefaultTextStyle.copy(fontSize = 20.sp, fontWeight = FontWeight.Normal),
        bodyMedium = DefaultTextStyle.copy(fontSize = 18.sp, fontWeight = FontWeight.Normal),
        bodySmall = DefaultTextStyle.copy(fontSize = 16.sp, fontWeight = FontWeight.Normal),

        labelLarge = DefaultTextStyle.copy(fontSize = 18.sp, fontWeight = FontWeight.Medium),
        labelSmall = DefaultTextStyle.copy(fontSize = 14.sp, fontWeight = FontWeight.Normal)
    )
}