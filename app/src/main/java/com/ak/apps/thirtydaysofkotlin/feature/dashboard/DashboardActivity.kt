package com.ak.apps.thirtydaysofkotlin.feature.dashboard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.preference.PreferenceManager
import com.ak.apps.thirtydaysofkotlin.R
import com.ak.apps.thirtydaysofkotlin.constant.AppConstants
import com.ak.apps.thirtydaysofkotlin.databinding.ActivityDashboardBinding
import com.ak.apps.thirtydaysofkotlin.feature.creategame.CreateGameActivity

class DashboardActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard)

        binding.acbtnLetsPlay.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()

        PreferenceManager.getDefaultSharedPreferences(this)?.let {
            binding.actvGamesPlayed.text = getString(
                R.string.games_played,
                it.getInt(AppConstants.SharedPrefKeys.TOTAL_GAMES, 0)
            )

            binding.actvExperienceLvl.text = getString(
                R.string.level,
                (it.getInt(AppConstants.SharedPrefKeys.TOTAL_SCORE, 0) / 10) + 1
            )
        }
    }

    override fun onClick(v: View?) {
        startActivity(Intent(this, CreateGameActivity::class.java))
    }

}
