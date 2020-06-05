package com.ak.apps.thirtydaysofkotlin.model

import java.io.Serializable

data class Problem(
    var question: String = "",
    var options: List<String> = mutableListOf(),
    var answer: Int = 0
) : Serializable