package com.tsu.itindr.data

import kotlin.Exception

class Exception:Exception() {
    override val message: String?
        get() = super.message
}