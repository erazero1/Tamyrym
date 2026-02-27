package com.erazero1.utils

inline fun <T> T?.ifNull(defaultValue: () -> T): T = this ?: defaultValue()