package core.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.ColorScheme as M3ColorScheme

internal val LocalColorScheme = staticCompositionLocalOf { lightColorScheme() }

internal fun lightColorScheme(): ColorScheme {
    return ColorScheme(
        primary = Color(0xFF386A20),
        onPrimary = Color.White,
        primaryContainer = Color(0xFFB7F397),
        onPrimaryContainer = Color(0xFF042100),

        secondary = Color(0xFF55624C),
        onSecondary = Color.White,
        secondaryContainer = Color(0xFFD8E7CB),
        onSecondaryContainer = Color(0xFF131F0D),

        tertiary = Color(0xFF006874),
        onTertiary = Color.White,
        tertiaryContainer = Color(0xFF97F0FF),
        onTertiaryContainer = Color(0xFF001F24),

        background = Color(0xFFFDFDF5),
        surface = Color(0xFFFDFDF5),
        surfaceDim = Color(0xFFDEDFDA),
        surfaceBright = Color(0xFFFFFFFF),
        onSurface = Color(0xFF1A1C18),
        onSurfaceVariant = Color(0xFF43483E),

        outline = Color(0xFF74796D),
        outlineCustom = Color(0xFF8D9286),

        error = Color(0xFFBA1A1A),
        onError = Color.White,
        errorContainer = Color(0xFFFFDAD6),
        onErrorContainer = Color(0xFF410002),

        success = Color(0xFF4CAF50),
        menuIconRed = Color(0xFFDC143C),
        successPrimaryContainer = Color(0xFFC8E6C9),
        successOnPrimaryContainer = Color(0xFF1B5E20)
    )
}

internal fun darkColorScheme(): ColorScheme {
    return ColorScheme(
        primary = Color(0xFF9CD67D),
        onPrimary = Color(0xFF0C3900),
        primaryContainer = Color(0xFF1F5107),
        onPrimaryContainer = Color(0xFFB7F397),

        secondary = Color(0xFFBCCBB0),
        onSecondary = Color(0xFF283420),
        secondaryContainer = Color(0xFF3E4A35),
        onSecondaryContainer = Color(0xFFD8E7CB),

        tertiary = Color(0xFF4FD8EB),
        onTertiary = Color(0xFF00363D),
        tertiaryContainer = Color(0xFF004F58),
        onTertiaryContainer = Color(0xFF97F0FF),

        background = Color(0xFF1A1C18),
        surface = Color(0xFF1A1C18),
        surfaceDim = Color(0xFF121410),
        surfaceBright = Color(0xFF3A3C35),
        onSurface = Color(0xFFE3E3DC),
        onSurfaceVariant = Color(0xFFC3C8BB),

        outline = Color(0xFF8D9286),
        outlineCustom = Color(0xFF43483E),

        error = Color(0xFFFFB4AB),
        onError = Color(0xFF690005),
        errorContainer = Color(0xFF93000A),
        onErrorContainer = Color(0xFFFFDAD6),

        success = Color(0xFF81C784),
        menuIconRed = Color(0xFFE95656),
        successPrimaryContainer = Color(0xFF1B5E20),
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


