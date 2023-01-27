package com.example.androidAssignment2.util

class NameParser {
    companion object {
        fun getName(email: String): String {
            val email = email
            val indexOfPoint = email.indexOf('.')
            val indexOfSign = email.indexOf('@')
            var name = email.substring(0, indexOfPoint).lowercase()
            name = name.replaceFirstChar { it.uppercaseChar() }
            var thurname = email.substring(indexOfPoint + 1, indexOfSign)
            thurname = thurname.replaceFirstChar { it.uppercaseChar() }
            return "$name $thurname"
        }
    }
}