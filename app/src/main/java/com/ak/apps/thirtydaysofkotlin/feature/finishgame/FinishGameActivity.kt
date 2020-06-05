package com.ak.apps.thirtydaysofkotlin.feature.finishgame

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.preference.PreferenceManager
import com.ak.apps.thirtydaysofkotlin.R
import com.ak.apps.thirtydaysofkotlin.constant.AppConstants
import com.ak.apps.thirtydaysofkotlin.databinding.ActivityFinishGameBinding
import com.ak.apps.thirtydaysofkotlin.feature.creategame.CreateGameActivity
import com.ak.apps.thirtydaysofkotlin.feature.dashboard.DashboardActivity

class FinishGameActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityFinishGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            }
            statusBarColor = Color.TRANSPARENT
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_finish_game)

        val score = intent.getIntExtra(AppConstants.IntentKeys.SCORE, 0)
        binding.actvScore.text = getString(R.string.score, score)

        with(PreferenceManager.getDefaultSharedPreferences(this)) {
            edit().putInt(
                AppConstants.SharedPrefKeys.TOTAL_SCORE,
                getInt(AppConstants.SharedPrefKeys.TOTAL_SCORE, 0) + score
            ).commit()
        }

        binding.acbtnPlayAgain.setOnClickListener(this)
        binding.acbtnGoHome.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.acbtn_play_again) {
            startActivity(Intent(this, CreateGameActivity::class.java))
            finish()
        } else if (v?.id == R.id.acbtn_go_home) {
            startActivity(
                Intent(this, DashboardActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            )
        }
    }

}