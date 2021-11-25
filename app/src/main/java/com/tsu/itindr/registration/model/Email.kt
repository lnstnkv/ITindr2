package com.tsu.itindr.registration.model

class Email {
    fun checkEmail(email: String): Boolean {
        val regex = Regex("""[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Z|a-z]{2,}""".trimIndent())
        return regex.matches(email)
    }
}