// app/src/main/java/project/background/BackgroundApplication.kt
package project.background

import android.app.Application
import android.util.Log
import androidx.work.Configuration
import androidx.work.WorkManager

class BackgroundApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        try {
            // Set up crash handler
            val defaultHandler = Thread.getDefaultUncaughtExceptionHandler()
            Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
                Log.e("BackgroundApplication", "Uncaught exception", throwable)
                defaultHandler?.uncaughtException(thread, throwable)
            }

            // Initialize WorkManager
            val configuration = Configuration.Builder()
                .setMinimumLoggingLevel(Log.DEBUG)
                .build()

            WorkManager.initialize(this, configuration)

        } catch (e: Exception) {
            Log.e("BackgroundApplication", "Error in onCreate", e)
        }
    }
}