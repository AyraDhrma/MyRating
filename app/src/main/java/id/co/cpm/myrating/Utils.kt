package id.co.cpm.myrating

import android.content.Context
import android.content.SharedPreferences
import android.os.CountDownTimer
import android.view.View
import android.widget.TextView

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

}
