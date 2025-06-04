package dev.tp_94.mobileapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import com.yandex.metrica.YandexMetrica
import com.yandex.metrica.YandexMetricaConfig

@HiltAndroidApp
class CakerApp : Application() {

    override fun onCreate() {
        super.onCreate()

        initAppMetrica()
    }

    private fun initAppMetrica() {
        val config = YandexMetricaConfig.newConfigBuilder("458a0a7f-85c0-43eb-8660-34d7144e00cd")
            .withLogs()
            .withCrashReporting(true)
            .build()

        YandexMetrica.activate(applicationContext, config)
        YandexMetrica.enableActivityAutoTracking(this)
    }
}