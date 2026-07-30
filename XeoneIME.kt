package com.gimhana.xeonekey.service

import android.inputmethodservice.InputMethodService
import android.view.View
import android.widget.*
import android.graphics.Color
import android.content.Context
import android.os.Vibrator
import com.gimhana.xeonekey.engine.FontTransformer
import com.gimhana.xeonekey.engine.SinhalaConverter
import com.gimhana.xeonekey.license.LicenseManager

class XeoneIME : InputMethodService() {
    private var currentStyle = FontTransformer.Style.NORMAL
    private var isSinhalaMode = false

    override fun onCreateInputView(): View {
        val isVip = LicenseManager.isVipUser(this)

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(Color.parseColor("#0D0221"))
            setPadding(10, 10, 10, 10)
        }

        val topBar = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL }
        
        val fontBtn = Button(this).apply {
            text = "Font: " + currentStyle.displayName
            setTextColor(Color.parseColor("#00FFCC"))
            setBackgroundColor(Color.parseColor("#190938"))
            setOnClickListener {
                val styles = FontTransformer.Style.values()
                val nextIdx = (currentStyle.ordinal + 1) % styles.size
                val candidateStyle = styles[nextIdx]

                if (candidateStyle.isPremium && !isVip) {
                    Toast.makeText(this@XeoneIME, "🔒 VIP Feature! Enter key XEONEKEY in app", Toast.LENGTH_SHORT).show()
                    currentStyle = FontTransformer.Style.NORMAL
                } else {
                    currentStyle = candidateStyle
                }
                text = "Font: " + currentStyle.displayName
            }
        }
        
        val langBtn = Button(this).apply {
            text = if(isSinhalaMode) "Lang: SIN" else "Lang: ENG"
            setTextColor(Color.parseColor("#FF007F"))
            setBackgroundColor(Color.parseColor("#190938"))
            setOnClickListener {
                isSinhalaMode = !isSinhalaMode
                text = if(isSinhalaMode) "Lang: SIN" else "Lang: ENG"
            }
        }

        topBar.addView(fontBtn)
        topBar.addView(langBtn)
        layout.addView(topBar)

        val keys = listOf("Q W E R T Y U I O P", "A S D F G H J K L", "Z X C V B N M")
        for (rowStr in keys) {
            val row = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL }
            for (k in rowStr.split(" ")) {
                val b = Button(this).apply {
                    text = k
                    setTextColor(Color.WHITE)
                    setBackgroundColor(Color.parseColor("#260F54"))
                    setOnClickListener {
                        (context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator).vibrate(15)
                        var output = k.lowercase()
                        if (isSinhalaMode) {
                            output = SinhalaConverter.convert(output)
                        } else {
                            output = FontTransformer.transform(output, currentStyle, isVip)
                        }
                        currentInputConnection?.commitText(output, 1)
                    }
                }
                row.addView(b)
            }
            layout.addView(row)
        }

        return layout
    }
}
