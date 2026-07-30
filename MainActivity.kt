package com.gimhana.xeonekey.ui

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.*
import android.graphics.Color
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import com.gimhana.xeonekey.license.LicenseManager

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(40, 60, 40, 40)
            setBackgroundColor(Color.parseColor("#0D0221"))
            gravity = Gravity.CENTER_HORIZONTAL
        }

        val title = TextView(this).apply {
            text = "XEONE KEY PRO"
            textSize = 28f
            setTextColor(Color.parseColor("#00FFCC"))
            gravity = Gravity.CENTER
            setPadding(0, 0, 0, 10)
        }

        val subtitle = TextView(this).apply {
            text = "Developer: Gimhana"
            textSize = 16f
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
            setPadding(0, 0, 0, 40)
        }

        val vipStatus = TextView(this).apply {
            text = if (LicenseManager.isVipUser(this@MainActivity)) "STATUS: ⭐ VIP ACTIVATED" else "STATUS: 🔒 FREE VERSION"
            textSize = 18f
            setTextColor(if (LicenseManager.isVipUser(this@MainActivity)) Color.GREEN else Color.RED)
            gravity = Gravity.CENTER
            setPadding(0, 0, 0, 30)
        }

        val keyInput = EditText(this).apply {
            hint = "Enter VIP Key (e.g. XEONEKEY)"
            setHintTextColor(Color.GRAY)
            setTextColor(Color.WHITE)
            setBackgroundColor(Color.parseColor("#190938"))
            setPadding(30, 30, 30, 30)
            gravity = Gravity.CENTER
        }

        val btnActivate = Button(this).apply {
            text = "UNLOCK VIP FEATURES"
            setBackgroundColor(Color.parseColor("#FF007F"))
            setTextColor(Color.WHITE)
            setPadding(0, 20, 0, 20)
            setOnClickListener {
                val enteredKey = keyInput.text.toString()
                if (LicenseManager.activateVipWithKey(this@MainActivity, enteredKey)) {
                    Toast.makeText(this@MainActivity, "🎉 VIP Activated!", Toast.LENGTH_LONG).show()
                    vipStatus.text = "STATUS: ⭐ VIP ACTIVATED"
                    vipStatus.setTextColor(Color.GREEN)
                } else {
                    Toast.makeText(this@MainActivity, "❌ Invalid Key! Enter XEONEKEY", Toast.LENGTH_LONG).show()
                }
            }
        }

        val btnEnable = Button(this).apply {
            text = "1. Enable Keyboard in Settings"
            setBackgroundColor(Color.parseColor("#260F54"))
            setTextColor(Color.WHITE)
            setOnClickListener { startActivity(Intent(Settings.ACTION_INPUT_METHOD_SETTINGS)) }
        }

        val infoTxt = TextView(this).apply {
            text = "\n🔑 VIP Key: XEONEKEY\nDeveloper: Gimhana"
            setTextColor(Color.LTGRAY)
            textSize = 14f
            gravity = Gravity.CENTER
        }

        layout.addView(title)
        layout.addView(subtitle)
        layout.addView(vipStatus)
        layout.addView(keyInput)
        
        val space = View(this).apply { layoutParams = LinearLayout.LayoutParams(1, 40) }
        layout.addView(space)
        layout.addView(btnActivate)
        
        val space2 = View(this).apply { layoutParams = LinearLayout.LayoutParams(1, 40) }
        layout.addView(space2)
        layout.addView(btnEnable)
        layout.addView(infoTxt)

        setContentView(layout)
    }
}
