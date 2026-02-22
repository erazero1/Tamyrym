import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Typography as M3Typography

internal val LocalTypography = staticCompositionLocalOf { defaultTypography() }

private val DefaultTextStyle = TextStyle(
    fontFamily = FontFamily.Default,
)

data class Typography(
    val h1: TextStyle,
    val h2: TextStyle,
    val h3: TextStyle,
    val paragraph: TextStyle,
    val listItem: TextStyle,
    val hint: TextStyle,
)

internal fun compactTypography(): Typography {
    return Typography(
        h1 = DefaultTextStyle.copy(
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold,
        ),
        h2 = DefaultTextStyle.copy(
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
        ),
        h3 = DefaultTextStyle.copy(
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
        ),
        paragraph = DefaultTextStyle.copy(
            fontSize = 15.sp,
            fontWeight = FontWeight.Normal,
        ),
        listItem = DefaultTextStyle.copy(
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
        ),
        hint = DefaultTextStyle.copy(
            fontSize = 13.sp,
            fontWeight = FontWeight.Normal,
        ),
    )
}

internal fun defaultTypography(): Typography {
    return Typography(
        h1 = DefaultTextStyle.copy(
            fontSize = 28.sp,
            fontWeight = FontWeight.SemiBold,
        ),
        h2 = DefaultTextStyle.copy(
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
        ),
        h3 = DefaultTextStyle.copy(
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
        ),
        paragraph = DefaultTextStyle.copy(
            fontSize = 17.sp,
            fontWeight = FontWeight.Normal,
        ),
        listItem = DefaultTextStyle.copy(
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
        ),
        hint = DefaultTextStyle.copy(
            fontSize = 15.sp,
            fontWeight = FontWeight.Normal,
        ),
    )
}

internal fun expandedTypography(): Typography {
    return Typography(
        h1 = DefaultTextStyle.copy(
            fontSize = 34.sp,
            fontWeight = FontWeight.Bold,
        ),
        h2 = DefaultTextStyle.copy(
            fontSize = 30.sp,
            fontWeight = FontWeight.SemiBold,
        ),
        h3 = DefaultTextStyle.copy(
            fontSize = 26.sp,
            fontWeight = FontWeight.Medium,
        ),
        paragraph = DefaultTextStyle.copy(
            fontSize = 22.sp,
            fontWeight = FontWeight.Normal,
        ),
        listItem = DefaultTextStyle.copy(
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium,
        ),
        hint = DefaultTextStyle.copy(
            fontSize = 19.sp,
            fontWeight = FontWeight.Normal,
        ),
    )
}


internal fun Typography.toM3Typography(): M3Typography {
    return M3Typography(
        headlineLarge = DefaultTextStyle.copy(
            fontSize = 28.sp,
            fontWeight = FontWeight.SemiBold,
        ),
        headlineMedium = DefaultTextStyle.copy(
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
        ),
        headlineSmall = DefaultTextStyle.copy(
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
        ),
        titleLarge = DefaultTextStyle.copy(
            fontSize = 17.sp,
            fontWeight = FontWeight.Normal,
        ),
        titleMedium = DefaultTextStyle.copy(
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
        ),
        titleSmall = DefaultTextStyle.copy(
            fontSize = 15.sp,
            fontWeight = FontWeight.Normal,
        ),
    )
}
