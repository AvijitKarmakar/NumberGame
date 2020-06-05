package com.ak.apps.thirtydaysofkotlin.model

import com.ak.apps.thirtydaysofkotlin.model.Problem
import java.io.Serializable

data class ProblemSet(
    val problemSet: MutableList<Problem> = mutableListOf()
) : Serializable