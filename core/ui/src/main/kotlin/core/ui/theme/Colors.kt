package core.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.ColorScheme as M3ColorScheme

internal val LocalColorScheme = staticCompositionLocalOf { lightColorScheme() }

internal fun lightColorScheme(): ColorScheme {
    return ColorScheme(
        primary = Color(0xFF2769B2),
        onPrimary = Color.White,
        primaryContainer = Color(0xFFD1E4FF),
        onPrimaryContainer = Color(0xFF001C3A),

        secondary = Color(0XFF545F71),
        onSecondary = Color.White,
        secondaryContainer = Color(0xFFD8E3F8),
        onSecondaryContainer = Color(0xFF3D4758),

        tertiary = Color(0xFF6D5DAE),
        onTertiary = Color.White,
        tertiaryContainer = Color(0xFFE6DEFF),
        onTertiaryContainer = Color(0xFF23005A),

        background = Color(0xFFE9F3FF),
        surface = Color.White,
        surfaceDim = Color(0xFFE4E8EE),
        surfaceBright = Color(0xFFF2F4F8),
        onSurface = Color(0xFF212121),
        onSurfaceVariant = Color(0xFF757575),

        outline = Color(0xFFC6C5D0),
        outlineCustom = Color(0xFF7B8794),

        error = Color(0xFFBA1A1A),
        onError = Color.White,
        errorContainer = Color(0xFFFFDAD6),
        onErrorContainer = Color(0xFF93000A),
        success = Color(0xFF4CAF50),
        menuIconRed = Color(0xFFDC143C),
        successPrimaryContainer = Color(0xFFC8E6C9),
        successOnPrimaryContainer = Color(0xFF1B5E20)
    )
}

internal fun darkColorScheme(): ColorScheme {
    return ColorScheme(
        primary = Color(0xFF5D9BFF),
        onPrimary = Color(0xFF121212),
        primaryContainer = Color(0xFF1A3A8F),
        onPrimaryContainer = Color(0xFFD6E2FF),

        secondary = Color(0xFFBCC7DC),
        onSecondary = Color(0xFF263141),
        secondaryContainer = Color(0xFF3D4758),
        onSecondaryContainer = Color(0xFFD8E3F8),

        tertiary = Color(0xFFDABDE2),
        onTertiary = Color(0xFF3D2946),
        tertiaryContainer = Color(0xFF553F5E),
        onTertiaryContainer = Color(0xFFF7D9FF),

        background = Color(0xFFE9F3FF),
        surface = Color(0xFF161B22),
        surfaceDim = Color(0xFF0D1117),
        surfaceBright = Color(0xFF1F242C),
        onSurface = Color(0xFFE1E2E9),
        onSurfaceVariant = Color(0xFFC3C6CF),

        outline = Color(0xFF8D9199),
        outlineCustom = Color(0xFF43474E),

        error = Color(0xFFFFB4AB),
        onError = Color(0xFF690005),
        errorContainer = Color(0xFF93000A),
        onErrorContainer = Color(0xFFFFDAD6),
        success = Color(0xFF6FCF97),
        menuIconRed = Color(0xFFE95656),
        successPrimaryContainer = Color(0xFF388E3C),
        successOnPrimaryContainer = Color(0xFFC8E6C9)
    )
}

@Immutable
data class ColorScheme(
    val primary: Color,
    val onPrimary: Color,
    val primaryContainer: Color,
    val onPrimaryContainer: Color,

    val secondary: Color,
    val onSecondary: Color,
    val secondaryContainer: Color,
    val onSecondaryContainer: Color,

    val tertiary: Color,
    val onTertiary: Color,
    val tertiaryContainer: Color,
    val onTertiaryContainer: Color,

    val background: Color,

    val surface: Color,
    val surfaceBright: Color,
    val surfaceDim: Color,
    val onSurface: Color,
    val onSurfaceVariant: Color,

    val success: Color,
    val error: Color,
    val onError: Color,
    val errorContainer: Color,
    val onErrorContainer: Color,

    val successPrimaryContainer: Color,
    val successOnPrimaryContainer: Color,

    val outlineCustom: Color,
    val outline: Color,
    val menuIconRed: Color,
)

internal fun ColorScheme.toM3ColorScheme(): M3ColorScheme {
    return M3ColorScheme(
        primary = primary,
        onPrimary = onPrimary,
        primaryContainer = primaryContainer,
        onPrimaryContainer = onPrimaryContainer,

        secondary = secondary,
        onSecondary = onSecondary,
        secondaryContainer = secondaryContainer,
        onSecondaryContainer = onSecondaryContainer,

        tertiary = tertiary,
        onTertiary = onTertiary,
        tertiaryContainer = tertiaryContainer,
        onTertiaryContainer = onTertiaryContainer,

        background = background,
        surface = surface,
        onSurface = onSurface,
        onSurfaceVariant = onSurfaceVariant,
        surfaceDim = surfaceDim,
        surfaceBright = surfaceBright,

        error = error,
        onError = onError,
        errorContainer = errorContainer,
        onErrorContainer = onErrorContainer,

        outline = outline,

        surfaceVariant = Color.Unspecified,
        inversePrimary = Color.Unspecified,
        surfaceTint = Color.Unspecified,
        inverseSurface = Color.Unspecified,
        inverseOnSurface = Color.Unspecified,
        outlineVariant = Color.Unspecified,
        scrim = Color.Unspecified,
        surfaceContainerLow = Color.Unspecified,
        surfaceContainerHigh = Color.Unspecified,
        surfaceContainerHighest = Color.Unspecified,
        surfaceContainerLowest = Color.Unspecified,
        surfaceContainer = Color.Unspecified,
        onBackground = Color.Unspecified,
    )
}


