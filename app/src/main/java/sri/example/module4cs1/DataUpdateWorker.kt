// app/src/main/java/project/background/DataUpdateWorker.kt
package project.background

import android.content.Context
import android.util.Log
import androidx.work.*

class DataUpdateWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            Log.d("DataUpdateWorker", "Work started")

            // Get repository instance
            val repository = DataRepository.getInstance(applicationContext)

            // Simulate progress updates
            setProgress(workDataOf("PROGRESS" to "Starting data update..."))
            kotlinx.coroutines.delay(1000)

            setProgress(workDataOf("PROGRESS" to "Fetching data..."))
            kotlinx.coroutines.delay(1000)

            // Perform the actual data update
            val newData = repository.fetchDataFromApi()

            setProgress(workDataOf("PROGRESS" to "Saving data..."))
            kotlinx.coroutines.delay(500)

            // Save the data
            repository.saveData(newData)

            setProgress(workDataOf("PROGRESS" to "Update completed!"))

            Log.d("DataUpdateWorker", "Work completed successfully: $newData")
            Result.success()

        } catch (e: Exception) {
            Log.e("DataUpdateWorker", "Work failed: ${e.message}")
            e.printStackTrace()
            Result.retry()
        }
    }
}