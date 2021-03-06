package com.example.parkIt.utilities

class Regexes {
    private val mailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9-]+\\.[a-zA-Z.]{2,18}".toRegex()
    private val passwordPattern = "[a-zA-Z0-9?.*@\$!%#&]{8,32}".toRegex() //znaki dostepne ?.*@$!%#&
    private val usernamePatter = "[a-zA-Z0-9]{3,18}".toRegex()
    private val namePatter = "[a-zA-Z]{2,32}".toRegex()


    fun checkMail(mail: String): Boolean {
        return mailPattern.matches(mail);
    }

    fun checkPassword(password: String): Boolean{
        return passwordPattern.matches(password);
    }

    fun checkUsername(username: String): Boolean{
        return usernamePatter.matches(username)
    }
}