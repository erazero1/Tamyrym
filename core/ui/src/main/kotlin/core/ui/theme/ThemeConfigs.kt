package core.ui.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.erazero1.utils.ifNull

enum class ColorSchemeMode {
    LIGHT,
    DARK,
    SYSTEM,
}

enum class TypographyMode(val type: String) {
    COMPACT("compact"),
    DEFAULT("default"),
    EXPANDED("expanded");

    override fun toString(): String {
        return type
    }

    companion object {
        fun findState(type: String?): TypographyMode {
            return values()
                .find { mode -> mode.type == type }
                .ifNull { DEFAULT }
        }
    }
}


class ThemeSettings {
    var colorSchemeMode by mutableStateOf(ColorSchemeMode.SYSTEM)
    var typographyMode by mutableStateOf(TypographyMode.DEFAULT)
}