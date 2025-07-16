package com.eliascoelho911.logs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Stable
import androidx.compose.runtime.staticCompositionLocalOf

val LocalComposeLogger = staticCompositionLocalOf<ComposeLogger> {
    throw IllegalStateException("No ComposeLogger provided")
}

@Composable
fun LoggerScope(
    tag: String,
    content: @Composable () -> Unit
) {
    val loggerScope = ComposeLogger(tag)

    CompositionLocalProvider(LocalComposeLogger provides loggerScope) {
        content()
    }
}

@Stable
class ComposeLogger(tag: String) : Logger {
    private val logger = AndroidLogger(tag)

    override fun debug(message: String?, throwable: Throwable?) {
        logger.debug(message, throwable)
    }

    override fun info(message: String?, throwable: Throwable?) {
        logger.info(message, throwable)
    }

    override fun warning(message: String?, throwable: Throwable?) {
        logger.warning(message, throwable)
    }

    override fun error(message: String?, throwable: Throwable?) {
        logger.error(message, throwable)
    }

    override fun verbose(message: String?, throwable: Throwable?) {
        logger.verbose(message, throwable)
    }
}

@Composable
fun DebugLog(message: String, throwable: Throwable? = null) {
    LocalComposeLogger.current.debug(message, throwable)
}

@Composable
fun InfoLog(message: String, throwable: Throwable? = null) {
    LocalComposeLogger.current.info(message, throwable)
}

@Composable
fun WarningLog(message: String, throwable: Throwable? = null) {
    LocalComposeLogger.current.warning(message, throwable)
}

@Composable
fun ErrorLog(message: String, throwable: Throwable? = null) {
    LocalComposeLogger.current.error(message, throwable)
}

@Composable
fun VerboseLog(message: String, throwable: Throwable? = null) {
    LocalComposeLogger.current.verbose(message, throwable)
}