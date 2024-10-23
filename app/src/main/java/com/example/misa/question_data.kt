package com.example.misa

data class question_data(
    var question: String = " ",
    var option1: String = " ",
    var option2: String = " ",
    var option3: String = " ",
    var option4: String = " ",
    var option1Points: Int = 0, // Points for option 1
    var option2Points: Int = 0, // Points for option 2
    var option3Points: Int = 0, // Points for option 3
    var option4Points: Int = 0, // Points for option 4
    var selectedOption: Int = 0 // To store which option was selected
)
