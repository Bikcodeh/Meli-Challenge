package com.bikcodeh.melichallenge.util

import android.util.Log
import com.microsoft.appcenter.crashes.Crashes
import timber.log.Timber

class ReleaseTree : Timber.Tree() {

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (priority == Log.ERROR || priority == Log.WARN) {
            Crashes.trackError(t)
        }
    }
}