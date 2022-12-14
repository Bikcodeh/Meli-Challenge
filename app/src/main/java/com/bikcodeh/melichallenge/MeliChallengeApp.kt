package com.bikcodeh.melichallenge

import android.app.Application
import com.bikcodeh.melichallenge.data.BuildConfig as BuildConfigData
import com.bikcodeh.melichallenge.presentation.util.ReleaseTree
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MeliChallengeApp: Application() {
    override fun onCreate() {
        super.onCreate()
        setUpTimber()
        setUpAppCenter()
    }

    private fun setUpTimber() {
        if (BuildConfigData.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(ReleaseTree())
        }
    }

    private fun setUpAppCenter() {
        AppCenter.start(
            this,
            BuildConfigData.APP_CENTER_KEY,
            Analytics::class.java,
            Crashes::class.java
        )
        Crashes.setEnabled(true)
    }
}