package com.androidAssignment2.util

class NameParser {
    companion object {
        fun getName(email: String): String {
            val indexOfPoint = email.indexOf('.')
            val indexOfSign = email.indexOf('@')
            val name = email.substring(0, indexOfPoint).lowercase().run {
                replaceFirstChar { it.uppercaseChar() }
            }
            val surname = email.substring(indexOfPoint + 1, indexOfSign).run {
                replaceFirstChar { it.uppercaseChar() }
            }
            return "$name $surname"
        }

        fun validEmail(email: String) =
            email.matches("\\S+\\.\\S+@+[A-Za-z]+\\.[A-Za-z]+".toRegex())
    }
}