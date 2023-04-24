package com.kevin.habitroops.ui.introscreen

import android.content.Intent
import android.graphics.Color.alpha
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.kevin.habitroops.MainActivity
import com.kevin.habitroops.R
import com.kevin.habitroops.data.models.IntroView
import com.kevin.habitroops.databinding.ActivityIntroBinding

class IntroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIntroBinding
    private lateinit var introView: List<IntroView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        addToIntroView()

        binding.viewPager2.adapter = ViewPagerAdapter(introView)
        binding.viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        binding.circleIndicator.setViewPager(binding.viewPager2)

        binding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                if (position == 2) {
                    animationButton()
                }
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }
        })
    }

    private fun animationButton() {
        binding.btnStartApp.visibility = View.VISIBLE

        binding.btnStartApp.animate().apply {
            duration = 1400
            alpha(1f)

            binding.btnStartApp.setOnClickListener {
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }.start()
    }

    private fun addToIntroView() {
        //Create some items that you want to add to your viewpager

        introView = listOf(
            IntroView("Welcome to Habit Tracker!", R.drawable.ic_tea),
            IntroView("This app is designed to keep track of your habits, " +
                    "whether it's a good one, or a bad one.", R.drawable.ic_fastfood),
            IntroView("Good luck! Tap on the button below to get started with using the app!", R.drawable.ic_smoking2),
        )
    }
}
