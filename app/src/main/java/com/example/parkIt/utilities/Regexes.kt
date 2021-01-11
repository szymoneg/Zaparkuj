package com.example.parkIt.utilities

class Regexes {
    private val mailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9-]+\\.[a-zA-Z.]{2,18}".toRegex()
    private val passwordPattern = "[a-zA-Z0-9?.*@\$!%#&]{8,32}".toRegex() //znaki dostepne ?.*@$!%#&


    fun checkMail(mail: String): Boolean {
        return mailPattern.matches(mail);
    }

    fun checkPassword(password: String): Boolean{
        return passwordPattern.matches(password);
    }
}