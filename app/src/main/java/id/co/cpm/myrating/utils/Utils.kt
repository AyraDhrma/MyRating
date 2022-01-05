package id.co.cpm.myrating.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import android.os.CountDownTimer
import android.util.Base64
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import id.co.cpm.myrating.datasource.ConfigModel
import java.nio.charset.StandardCharsets


object Utils {

    fun countDownRedirect(textView: TextView, time: Long) {
        object : CountDownTimer(time, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                textView.text = "Back to main page on..." + millisUntilFinished / 1000
            }

            override fun onFinish() {
            }
        }.start()
    }

    fun saveConfig(context: Context, data: ConfigModel) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(Constant.servername, data.servername)
        editor.putString(Constant.username, data.username)
        editor.putString(Constant.counter, data.counter)
        editor.apply()
    }

    fun loadConfig(context: Context): ConfigModel {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE)
        return ConfigModel(
            sharedPreferences.getString(Constant.servername, "").toString(),
            sharedPreferences.getString(Constant.username, "").toString(),
            sharedPreferences.getString(Constant.counter, "").toString(),
        )
    }

    fun Activity.fullScreen(colorNav: Int) {
        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            }
            statusBarColor = Color.TRANSPARENT
            navigationBarColor = colorNav
        }
    }

    fun View.showLoading() {
        this.visibility = View.VISIBLE
    }

    fun View.hideLoading() {
        this.visibility = View.GONE
    }

    fun base64Encode(stringToEncode: String): String {
        return Base64.encodeToString(
            stringToEncode.toByteArray(StandardCharsets.UTF_8),
            Base64.NO_WRAP or Base64.URL_SAFE
        )
    }

}
