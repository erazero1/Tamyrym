package core.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    typographyMode: TypographyMode = TypographyMode.DEFAULT,
    colors: ColorScheme = if (darkTheme) darkColorScheme() else lightColorScheme(),
    typography: Typography = when (typographyMode) {
        TypographyMode.COMPACT -> compactTypography()
        TypographyMode.DEFAULT -> defaultTypography()
        TypographyMode.EXPANDED -> expandedTypography()
    },
    shapes: Shapes = shapes(),
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalColorScheme provides colors,
        LocalShapes provides shapes,
        LocalTypography provides typography,
    ) {
        MaterialTheme(
            colorScheme = colors.toM3ColorScheme(),
            typography = typography,
            shapes = shapes.toM3Shapes(),
            content = content,
        )
    }
}

object AppTheme {
    val colors: ColorScheme
        @[Composable ReadOnlyComposable] get() = LocalColorScheme.current

    val typography: Typography
        @[Composable ReadOnlyComposable] get() = LocalTypography.current

    val shapes: Shapes
        @[Composable ReadOnlyComposable] get() = LocalShapes.current
}
