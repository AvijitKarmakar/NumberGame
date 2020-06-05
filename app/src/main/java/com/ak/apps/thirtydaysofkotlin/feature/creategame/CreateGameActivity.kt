package com.ak.apps.thirtydaysofkotlin.feature.creategame

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.ak.apps.thirtydaysofkotlin.R
import com.ak.apps.thirtydaysofkotlin.constant.AppConstants
import com.ak.apps.thirtydaysofkotlin.feature.forwardbackwardgame.ForwardBackwardGameActivity
import com.ak.apps.thirtydaysofkotlin.model.Problem
import com.ak.apps.thirtydaysofkotlin.model.ProblemSet
import kotlin.random.Random

class CreateGameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_game)

        createUpwardDownwardGame()
    }

    private fun createUpwardDownwardGame() {
        val generatedTenQuestionNos = List(10) { Random.nextInt(1, 3) }

        val problemSet = mutableListOf<Problem>()
        val upwardDownwardGames = resources.getStringArray(R.array.upward_downward_games)

        for (questionNo in generatedTenQuestionNos) {
            val rawQuestion = upwardDownwardGames[questionNo - 1]

            val problem = Problem()
            var question = ""
            val options = mutableListOf<String>()
            var rightOption = 0

            when (questionNo) {
                1 -> {
                    val knownNumber = Random.nextInt(1, 21)
                    question = String.format(rawQuestion, knownNumber)

                    val answer = knownNumber + 1

                    var startingOption = Random.nextInt(answer - 3, answer + 1)

                    for (i in 1..4) {
                        options.add(startingOption.toString())
                        if (answer == startingOption) {
                            rightOption = i
                        }
                        startingOption++
                    }

                }

                2 -> {
                    val knownNumber = Random.nextInt(1, 21)
                    question = String.format(rawQuestion, knownNumber)

                    val answer = knownNumber - 1

                    var startingOption = Random.nextInt(answer - 3, answer + 1)

                    for (i in 1..4) {
                        options.add(startingOption.toString())
                        if (answer == startingOption) {
                            rightOption = i
                        }
                        startingOption++
                    }

                }
            }
            problem.question = question
            problem.options = options
            problem.answer = rightOption
            problemSet.add(problem)
        }

        val problemSetObj =
            ProblemSet(problemSet)

        Handler().postDelayed({
            with(PreferenceManager.getDefaultSharedPreferences(this)) {
                edit().putInt(
                    AppConstants.SharedPrefKeys.TOTAL_GAMES,
                    getInt(AppConstants.SharedPrefKeys.TOTAL_GAMES, 0) + 1
                ).commit()
            }

            startActivity(
                Intent(this, ForwardBackwardGameActivity::class.java)
                    .putExtra(AppConstants.IntentKeys.PROBLEM_SET, problemSetObj)
            )
            finish()
        }, AppConstants.Misc.THOUSAND_MILLIS)
    }

}
