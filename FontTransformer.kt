package com.gimhana.xeonekey.engine

object FontTransformer {
    enum class Style(val isPremium: Boolean, val displayName: String) {
        NORMAL(false, "Normal"),
        SCRIPT_CURSIVE(true, "𝓒𝓾𝓻𝓼𝓲𝓿𝓮 (VIP)"),
        DOUBLE_STRUCK(true, "𝔻𝕠𝕦𝕓𝕝𝕖 (VIP)"),
        GOTHIC(true, "𝕲𝖔𝖙𝖍𝖎𝖈 (VIP)"),
        CIRCLED(true, "Ⓒⓘⓡⓒⓛⓔⓓ (VIP)")
    }

    fun transform(text: String, style: Style, isVip: Boolean): String {
        if (style.isPremium && !isVip) return text
        if (style == Style.NORMAL) return text
        val sb = StringBuilder()
        for (ch in text) {
            val codePoint = ch.code
            val converted = when (style) {
                Style.SCRIPT_CURSIVE -> when (ch) {
                    in 'a'..'z' -> String(Character.toChars(0x1D4EA + (codePoint - 'a'.code)))
                    in 'A'..'Z' -> String(Character.toChars(0x1D4D0 + (codePoint - 'A'.code)))
                    else -> ch.toString()
                }
                Style.DOUBLE_STRUCK -> when (ch) {
                    in 'a'..'z' -> String(Character.toChars(0x1D552 + (codePoint - 'a'.code)))
                    in 'A'..'Z' -> String(Character.toChars(0x1D538 + (codePoint - 'A'.code)))
                    else -> ch.toString()
                }
                Style.CIRCLED -> when (ch) {
                    in 'a'..'z' -> String(Character.toChars(0x24D0 + (codePoint - 'a'.code)))
                    in 'A'..'Z' -> String(Character.toChars(0x24B6 + (codePoint - 'A'.code)))
                    else -> ch.toString()
                }
                Style.GOTHIC -> when (ch) {
                    in 'a'..'z' -> String(Character.toChars(0x1D586 + (codePoint - 'a'.code)))
                    in 'A'..'Z' -> String(Character.toChars(0x1D56C + (codePoint - 'A'.code)))
                    else -> ch.toString()
                }
                else -> ch.toString()
            }
            sb.append(converted)
        }
        return sb.toString()
    }
}
