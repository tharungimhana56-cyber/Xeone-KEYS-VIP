package com.gimhana.xeonekey.license

import android.content.Context
import android.content.SharedPreferences

object LicenseManager {
    private const val PREF_NAME = "XeoneKey_Prefs"
    private const val KEY_IS_VIP = "is_vip_user"
    
    // Explicit VIP Key specified by user
    const val VIP_KEY_EXACT = "XEONEKEY"
    const val MASTER_ADMIN_PIN = "GIMHANA99" 

    fun isVipUser(context: Context): Boolean {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).getBoolean(KEY_IS_VIP, false)
    }

    fun activateVipWithKey(context: Context, key: String): Boolean {
        val cleanKey = key.trim().uppercase()
        if (cleanKey == VIP_KEY_EXACT || cleanKey == MASTER_ADMIN_PIN || (cleanKey.startsWith("XEONE-") && cleanKey.length == 10)) {
            context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit().putBoolean(KEY_IS_VIP, true).apply()
            return true
        }
        return false
    }
}
