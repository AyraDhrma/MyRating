package id.co.cpm.myrating

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import id.co.cpm.myrating.databinding.ActivityConfigSettingBinding

class ConfigSetting : AppCompatActivity() {

    private lateinit var binding: ActivityConfigSettingBinding
    private var toast: Toast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
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

    private fun checkDataNull() {
        val data = Utils.loadConfig(this)
        data?.apply {
            binding.serverName.setText(data.servername)
            binding.username.setText(data.username)
            binding.counter.setText(data.counter)
        }
    }
}