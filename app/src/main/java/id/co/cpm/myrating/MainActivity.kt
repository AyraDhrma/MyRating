package id.co.cpm.myrating

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import id.co.cpm.myrating.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var toast: Toast? = null
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.runningText.setSelected(true)
        binding.notSatisfiedAnimView.setAnimation(R.raw.tidakpuas)
        binding.satisfiedAnimView.setAnimation(R.raw.puas)
        binding.verySatisfiedAnimView.setAnimation(R.raw.sangatpuas)

        listener()
    }

    @SuppressLint("ShowToast")
    private fun listener() {
        val intent = Intent(this@MainActivity, FinishRating::class.java)
        binding.notSatisfied.setOnClickListener {
            intent.putExtra(Constant.rating, Constant.notsatisfied)
            startActivity(intent)
        }
        binding.satisfied.setOnClickListener {
            intent.putExtra(Constant.rating, Constant.satisfied)
            startActivity(intent)
        }
        binding.verySatisfied.setOnClickListener {
            intent.putExtra(Constant.rating, Constant.verysatisfied)
            startActivity(intent)
        }

        var clicked = 3
        binding.ratingLabelView.setOnClickListener {
            clicked--
            toast?.cancel()
            toast =
                Toast.makeText(
                    this,
                    "$clicked tap more to configuration setting",
                    Toast.LENGTH_SHORT
                )
            toast?.show()
            if (clicked == 0) {
                clicked = 3
                toast?.cancel()
                val intent = Intent(this@MainActivity, ConfigSetting::class.java)
                startActivity(intent)
            }
        }
    }
}