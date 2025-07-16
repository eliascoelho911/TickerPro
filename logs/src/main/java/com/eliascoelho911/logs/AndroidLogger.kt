package com.eliascoelho911.logs

import android.util.Log

private const val TAG = "EbookReader"

class AndroidLogger(private val tag: String) : Logger {

    override fun debug(message: String?, throwable: Throwable?) {
        Log.d(TAG, "$tag -> $message", throwable)
    }

    override fun error(message: String?, throwable: Throwable?) {
        Log.e(TAG, "$tag -> $message", throwable)
    }

    override fun info(message: String?, throwable: Throwable?) {
        Log.i(TAG, "$tag -> $message", throwable)
    }

    override fun verbose(message: String?, throwable: Throwable?) {
        Log.v(TAG, "$tag -> $message", throwable)
    }

    override fun warning(message: String?, throwable: Throwable?) {
        Log.w(TAG, "$tag -> $message", throwable)
    }
}