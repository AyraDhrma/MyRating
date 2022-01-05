package id.co.cpm.myrating.view.finish

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.co.cpm.myrating.databinding.ActivityFinishRatingBinding
import android.os.Handler
import android.os.Looper
import androidx.core.content.ContextCompat
import id.co.cpm.myrating.utils.Constant
import id.co.cpm.myrating.R
import id.co.cpm.myrating.utils.Utils
import id.co.cpm.myrating.utils.Utils.fullScreen


class FinishRating : AppCompatActivity() {

    lateinit var binding: ActivityFinishRatingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishRatingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val rating = intent.getStringExtra(Constant.rating).toString()

        when (rating) {
            Constant.notsatisfied -> {
                binding.ratingAnimView.setAnimation(R.raw.tidakpuas)
                binding.ratingAnimView.playAnimation()
                binding.ratingResultLabelView.text = resources.getString(R.string.tidak_puas)
            }
            Constant.satisfied -> {
                binding.ratingAnimView.setAnimation(R.raw.puas)
                binding.ratingAnimView.playAnimation()
                binding.ratingResultLabelView.text = resources.getString(R.string.puas)
            }
            Constant.verysatisfied -> {
                binding.ratingAnimView.setAnimation(R.raw.sangatpuas)
                binding.ratingAnimView.playAnimation()
                binding.ratingResultLabelView.text = resources.getString(R.string.sangat_puas)
            }
        }

        Utils.countDownRedirect(binding.redirect, 6000)
        Handler(Looper.getMainLooper()).postDelayed({
            this.finish()
        }, 5000)
    }

    override fun onResume() {
        super.onResume()
        this.fullScreen(ContextCompat.getColor(applicationContext, R.color.purple_700))
    }
}