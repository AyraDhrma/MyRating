package id.co.cpm.myrating.view.config

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import id.co.cpm.myrating.datasource.ConfigModel
import id.co.cpm.myrating.R
import id.co.cpm.myrating.utils.Utils
import id.co.cpm.myrating.utils.Utils.fullScreen
import id.co.cpm.myrating.databinding.ActivityConfigSettingBinding

class ConfigSetting : AppCompatActivity() {

    private lateinit var binding: ActivityConfigSettingBinding
    private var toast: Toast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        window.decorView.apply {
            systemUiVisibility =
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN
        }
        super.onCreate(savedInstanceState)
        binding = ActivityConfigSettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkDataNull()
        binding.saveButton.setOnClickListener {
            Utils.saveConfig(
                this,
                ConfigModel(
                    binding.serverName.text.toString(),
                    binding.username.text.toString(),
                    binding.counter.text.toString()
                )
            )
            toast?.cancel()
            toast = Toast.makeText(this, "Save Successful", Toast.LENGTH_SHORT)
            toast?.show()
        }
        binding.cancelButton.setOnClickListener {
            this.finish()
        }
    }

    override fun onResume() {
        super.onResume()
        this.fullScreen(ContextCompat.getColor(applicationContext, R.color.purple_700))
    }

    private fun checkDataNull() {
        val data = Utils.loadConfig(this)
        data?.apply {
            binding.serverName.setText(data.servername)
            binding.username.setText(data.username)
            binding.counter.setText(data.counter)
        }
    }
}