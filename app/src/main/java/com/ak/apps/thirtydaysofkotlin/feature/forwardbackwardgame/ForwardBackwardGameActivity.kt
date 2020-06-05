package com.ak.apps.thirtydaysofkotlin.feature.forwardbackwardgame

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ak.apps.thirtydaysofkotlin.R
import com.ak.apps.thirtydaysofkotlin.constant.AppConstants
import com.ak.apps.thirtydaysofkotlin.databinding.ActivityForwardBackwardGameBinding
import com.ak.apps.thirtydaysofkotlin.feature.finishgame.FinishGameActivity
import com.ak.apps.thirtydaysofkotlin.model.Problem
import com.ak.apps.thirtydaysofkotlin.model.ProblemSet

class ForwardBackwardGameActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityForwardBackwardGameBinding

    private var questionNo = 0
    private var problemSet = mutableListOf<Problem>()
    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this, R.layout.activity_forward_backward_game
        )

        setSupportActionBar(binding.tbGame)
        val actionBar = supportActionBar
        actionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic_left_arrow)
            it.setDisplayShowTitleEnabled(false)
        }

        problemSet = (intent.getSerializableExtra("ProblemSet") as ProblemSet).problemSet

        Log.e("ProblemSet", problemSet.toString())

        binding.actvScore.text = getString(R.string.score, score)

        populateProblem(questionNo)

        binding.actvOpt1.setOnClickListener(this)
        binding.actvOpt2.setOnClickListener(this)
        binding.actvOpt3.setOnClickListener(this)
        binding.actvOpt4.setOnClickListener(this)
    }

    private fun populateProblem(questionNo: Int) {
        binding.actvQuesNo.text = getString(R.string.question_no, questionNo + 1)
        binding.actvFwBwNumber.text = problemSet[questionNo].question
        binding.actvOpt1.text = problemSet[questionNo].options[0]
        binding.actvOpt2.text = problemSet[questionNo].options[1]
        binding.actvOpt3.text = problemSet[questionNo].options[2]
        binding.actvOpt4.text = problemSet[questionNo].options[3]
    }

    override fun onClick(v: View?) {
        if (v?.tag == problemSet[questionNo].answer.toString()) {
            Toast.makeText(this, "Correct", Toast.LENGTH_LONG).show()
            score++
            binding.actvScore.text = getString(R.string.score, score)
        } else {
            Toast.makeText(this, "Wrong", Toast.LENGTH_LONG).show()
        }

        questionNo++

        if (questionNo < problemSet.size) {
            populateProblem(questionNo)
        } else {
            startActivity(Intent(this, FinishGameActivity::class.java)
                .putExtra(AppConstants.IntentKeys.SCORE, score))
            finish()
        }
    }

}
