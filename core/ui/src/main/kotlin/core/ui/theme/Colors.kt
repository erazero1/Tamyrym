package core.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.ColorScheme as M3ColorScheme

internal val LocalColorScheme = staticCompositionLocalOf { lightColorScheme() }

internal fun lightColorScheme(): ColorScheme {
    return ColorScheme(
        primary = Color(0xFF1B5E20), // Deep Forest Green
        onPrimary = Color.White,
        primaryContainer = Color(0xFFC8E6C9),
        onPrimaryContainer = Color(0xFF002105),

        secondary = Color(0xFF00BCD4), // Electric Cyan (AI)
        onSecondary = Color.White,
        secondaryContainer = Color(0xFFB2EBF2),
        onSecondaryContainer = Color(0xFF002025),

        tertiary = Color(0xFF5D4037), // Earth Brown
        onTertiary = Color.White,
        tertiaryContainer = Color(0xFFD7CCC8),
        onTertiaryContainer = Color(0xFF1D0D08),

        background = Color(0xFFF9F7F2), // Warm Ivory / Sand
        surface = Color.White,
        surfaceDim = Color(0xFFEFEBE9),
        surfaceBright = Color(0xFFFCFAF7),
        onSurface = Color(0xFF1B1B1B),
        onSurfaceVariant = Color(0xFF4E443C),

        outline = Color(0xFF85736E),
        outlineCustom = Color(0xFFD7CCC8),

        error = Color(0xFFBA1A1A),
        onError = Color.White,
        errorContainer = Color(0xFFFFDAD6),
        onErrorContainer = Color(0xFF410002),
        success = Color(0xFF2E7D32),
        menuIconRed = Color(0xFFDC143C),
        successPrimaryContainer = Color(0xFFC8E6C9),
        successOnPrimaryContainer = Color(0xFF1B5E20),
        link = Color(0xFF00838F),
        male = Color(0xFF2A6FDB),
        female = Color(0xFFD64F8E)
    )
}

internal fun darkColorScheme(): ColorScheme {
    return ColorScheme(
        primary = Color(0xFF81C784),
        onPrimary = Color(0xFF00390A),
        primaryContainer = Color(0xFF005313),
        onPrimaryContainer = Color(0xFF9DF49E),

        secondary = Color(0xFF4DD0E1),
        onSecondary = Color(0xFF00363D),
        secondaryContainer = Color(0xFF004E58),
        onSecondaryContainer = Color(0xFFB2EBF2),

        tertiary = Color(0xFFD7CCC8),
        onTertiary = Color(0xFF2F150D),
        tertiaryContainer = Color(0xFF452B22),
        onTertiaryContainer = Color(0xFFFFDBCE),

        background = Color(0xFF1B1B1B), // Dark background
        surface = Color(0xFF262626),
        surfaceDim = Color(0xFF121212),
        surfaceBright = Color(0xFF323232),
        onSurface = Color(0xFFF9F7F2),
        onSurfaceVariant = Color(0xFFD7C4B7),

        outline = Color(0xFF9F8D84),
        outlineCustom = Color(0xFF53433C),

        error = Color(0xFFFFB4AB),
        onError = Color(0xFF690005),
        errorContainer = Color(0xFF93000A),
        onErrorContainer = Color(0xFFFFDAD6),
        success = Color(0xFF81C784),
        menuIconRed = Color(0xFFE95656),
        successPrimaryContainer = Color(0xFF1B5E20),
        successOnPrimaryContainer = Color(0xFFC8E6C9),
        link = Color(0xFF00838F),
        male = Color(0xFF2A6FDB),
        female = Color(0xFFD64F8E)
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
    val link: Color,
    val male: Color,
    val female: Color,
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
        // Остальные поля Material 3
        surfaceVariant = surfaceDim,
        inversePrimary = primaryContainer,
        surfaceTint = primary,
        inverseSurface = onSurface,
        inverseOnSurface = surface,
        outlineVariant = outlineCustom,
        scrim = Color.Black,
        surfaceContainerLow = surfaceDim,
        surfaceContainerHigh = surfaceBright,
        surfaceContainerHighest = surfaceBright,
        surfaceContainerLowest = surfaceDim,
        surfaceContainer = surface,
        onBackground = onSurface,
    )
}