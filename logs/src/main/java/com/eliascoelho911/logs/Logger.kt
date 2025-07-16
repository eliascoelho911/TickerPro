package com.eliascoelho911.logs

interface Logger {
    fun debug(message: String?, throwable: Throwable? = null)

    fun info(message: String?, throwable: Throwable? = null)

    fun warning(message: String?, throwable: Throwable? = null)

    fun error(message: String?, throwable: Throwable? = null)

    fun verbose(message: String?, throwable: Throwable? = null)
}