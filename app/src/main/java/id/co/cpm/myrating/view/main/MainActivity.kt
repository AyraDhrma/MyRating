package id.co.cpm.myrating.view.main

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.MediaController
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import id.co.cpm.myrating.utils.Constant
import id.co.cpm.myrating.R
import id.co.cpm.myrating.utils.Utils.fullScreen
import id.co.cpm.myrating.databinding.ActivityMainBinding
import id.co.cpm.myrating.datasource.DataVideo
import id.co.cpm.myrating.datasource.RequestModel
import id.co.cpm.myrating.utils.Status
import id.co.cpm.myrating.utils.Utils
import id.co.cpm.myrating.utils.Utils.hideLoading
import id.co.cpm.myrating.utils.Utils.showLoading
import id.co.cpm.myrating.view.config.ConfigSetting
import id.co.cpm.myrating.view.finish.FinishRating

class MainActivity : AppCompatActivity() {

    private var toast: Toast? = null
    lateinit var binding: ActivityMainBinding
    private var mediaController: MediaController? = null
    private lateinit var listVideoOrImage: ArrayList<DataVideo>
    private lateinit var mainViewModel: MainViewModel
    private lateinit var mainViewModelFactory: MainViewModelFactory
    var position = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModelFactory =
            MainViewModelFactory("http://${Utils.loadConfig(this@MainActivity).servername}")
        mainViewModel = ViewModelProvider(this, mainViewModelFactory)[MainViewModel::class.java]

        fethApiInformation()

        binding.runningText.isSelected = true
        binding.notSatisfiedAnimView.setAnimation(R.raw.tidakpuas)
        binding.verySatisfiedAnimView.setAnimation(R.raw.sangatpuas)
        listener()
    }

    private fun fethApiInformation() {
        mainViewModel.getInformation(
            RequestModel(
                Constant.apikey,
                ""
            )
        ).observe(this, {
            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        binding.progressCircular.showLoading()
                    }
                    Status.SUCCESS -> {
                        binding.progressCircular.hideLoading()
                        if (resource.data?.rc == "0000") {
                            var runningTextValue = ""
                            for (i in resource.data.dataRunning.indices) {
                                runningTextValue += "     ${resource.data.dataRunning[i].rtTeks}"
                            }
                            binding.runningText.text = runningTextValue

                            listVideoOrImage = resource.data.dataVideo
                            showVideoOrImage(0)
                        }
                    }
                    Status.ERROR -> {
                        binding.progressCircular.hideLoading()
                        Toast.makeText(this@MainActivity, "Koneksi Bermasalah", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        })
    }

    private fun showVideoOrImage(i: Int) {
        position = i
        Log.d("OkHttpClient", "showVideoOrImage: position $i")
        if (position < listVideoOrImage.size) {
            when (listVideoOrImage[position].iNFTipe) {
                "1" -> {
                    showImage(listVideoOrImage[position].iNFNama)
                }
                "3" -> {
                    showVideo(listVideoOrImage[position].iNFNama)
                }
            }
        } else {
            showVideoOrImage(0)
        }
    }

    private fun showVideo(iNFNama: String) {
        //mediaController = MediaController(this)
        //mediaController?.setAnchorView(binding.videoMain)
        binding.imageMain.visibility = View.GONE
        binding.videoMain.visibility = View.VISIBLE
        binding.videoMain.apply {
            setOnPreparedListener { mediaPlayer ->
//                val videoRatio = mediaPlayer.videoWidth / mediaPlayer.videoHeight.toFloat()
//                val screenRatio = this.width / this.height.toFloat()
//                val scaleX = videoRatio / screenRatio
//                if (scaleX >= 1f) {
//                    this.scaleX = scaleX
//                } else {
//                    this.scaleY = 1f / scaleX
//                }
            }
            //setMediaController(mediaController)
            setVideoURI(Uri.parse("http://${Utils.loadConfig(this@MainActivity).servername}$iNFNama"))
            start()
            setOnCompletionListener {
                position++
                showVideoOrImage(position)
            }
            setOnErrorListener { _, _, _ ->
                Toast.makeText(this@MainActivity, "Error playing video", Toast.LENGTH_SHORT).show()
                false
            }
        }
    }

    private fun showImage(image: String) {
        Handler(Looper.getMainLooper()).postDelayed({
            binding.videoMain.visibility = View.GONE
            binding.imageMain.visibility = View.VISIBLE
            Picasso.get()
                .load("http://${Utils.loadConfig(this@MainActivity).servername}${image}")
                .into(binding.imageMain)
            position++
            showVideoOrImage(position)
        }, 10000)
    }

    @SuppressLint("ShowToast")
    private fun listener() {
        binding.notSatisfied.setOnClickListener {
            saveRating("0", Constant.notsatisfied)
        }
        binding.verySatisfied.setOnClickListener {
            saveRating("1", Constant.verysatisfied)
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

    private fun saveRating(rating: String, type: String) {
        val intent = Intent(this@MainActivity, FinishRating::class.java)
        mainViewModel.saveRating(
            RequestModel(
                Constant.apikey,
                Utils.base64Encode(
                    "$rating|${Utils.loadConfig(this@MainActivity).counter}|${
                        Utils.loadConfig(
                            this@MainActivity
                        ).username
                    }"
                )
            )
        ).observe(this, {
            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        binding.progressCircular.showLoading()
                    }
                    Status.SUCCESS -> {
                        binding.progressCircular.hideLoading()
                        if (resource.data?.rc == "0000") {
                            intent.putExtra(Constant.rating, type)
                            startActivity(intent)
                        }
                    }
                    Status.ERROR -> {
                        binding.progressCircular.hideLoading()
                        Toast.makeText(this@MainActivity, "Koneksi Bermasalah", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        })
    }

    var stopPosition = 0

    override fun onPause() {
        super.onPause()
        onPauseVideoView()
    }

    override fun onResume() {
        super.onResume()
        this.fullScreen(ContextCompat.getColor(applicationContext, R.color.purple_700))
        onResumeVideoView()
    }

    private fun onPauseVideoView() {
        stopPosition = binding.videoMain.currentPosition
        binding.videoMain.pause()
    }

    private fun onResumeVideoView() {
        binding.videoMain.seekTo(stopPosition)
        binding.videoMain.start()
    }
}