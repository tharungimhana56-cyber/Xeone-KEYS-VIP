package com.gimhana.xeonekey.engine

object SinhalaConverter {
    private val mapping = mapOf(
        "mama" to "මම", "oyaa" to "ඔයා", "sinhala" to "සිංහල",
        "gimhana" to "ගිම්හාන", "xeone" to "සියෝන්"
    )

    fun convert(input: String): String {
        var res = input
        mapping.forEach { (k, v) ->
            res = res.replace(k, v, ignoreCase = true)
        }
        return res
    }
}
