package core.ui.theme

import LocalTypography
import Typography
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import compactTypography
import defaultTypography
import expandedTypography
import toM3Typography


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
            typography = typography.toM3Typography(),
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
