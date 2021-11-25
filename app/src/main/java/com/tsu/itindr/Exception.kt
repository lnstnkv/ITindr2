package com.tsu.itindr

import kotlin.Exception

class Exception:Exception() {
    override val message: String?
        get() = super.message
}