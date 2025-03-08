package com.example.bharatnewsxpress


import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.bharatnewsxpress.databinding.ActivityDetailsBinding
import com.squareup.picasso.Picasso

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val article = intent.getParcelableExtra<Article>("ARTICLE")

        article?.let {
            binding.Detailsauthor.text = it.author ?: "Unknown Author"
            binding.DetailsTitle.text = it.title ?: "No Title Available"
            binding.DetailsName.text = it.source?.name ?: "Unknown Source"
            binding.DetailsDate.text = it.publishedAt
            binding.DetailsImage.let { imageView ->
                Picasso.get().load(it.urlToImage).into(imageView)
            }
            binding.DetailsDesc.text = it.description
            binding.DetailReadMoreButton.setOnClickListener {
                val popUpAnimation = AnimationUtils.loadAnimation(this, R.anim.button_pop_up)
                // Start the animation
                it.startAnimation(popUpAnimation)
                // Add vibration feedback
                val vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator
                if (vibrator.hasVibrator()) {
                    vibrator.vibrate(
                        VibrationEffect.createOneShot(
                            50,
                            VibrationEffect.DEFAULT_AMPLITUDE
                        )
                    )
                }
                article.url?.let { url ->
                    openWebPage(url)
                } ?: run {
                    showToast("URL Not Found")
                }
            }

        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun openWebPage(url: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        browserIntent.setPackage("com.android.chrome") // Default browser package name
        try {
            startActivity(browserIntent)
        } catch (e: Exception) {
            showToast("No browser available to open the link.")
            e.printStackTrace()
        }
    }


}