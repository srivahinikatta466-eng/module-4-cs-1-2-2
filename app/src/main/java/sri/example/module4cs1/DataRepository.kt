// app/src/main/java/project/background/DataRepository.kt
package project.background

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class DataRepository private constructor(context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    suspend fun fetchDataFromApi(): String {
        return withContext(Dispatchers.IO) {
            try {
                // Simulate API call with delay
                delay(2000)

                // Mock data for demonstration
                val mockData = listOf(
                    "Sample Data 1 - ${System.currentTimeMillis()}",
                    "Sample Data 2 - ${System.currentTimeMillis()}",
                    "Sample Data 3 - ${System.currentTimeMillis()}",
                    "Sample Data 4 - ${System.currentTimeMillis()}",
                    "Sample Data 5 - ${System.currentTimeMillis()}"
                )

                mockData.random()

            } catch (e: Exception) {
                "Failed to fetch data: ${e.message}"
            }
        }
    }

    suspend fun getSavedData(): String {
        return withContext(Dispatchers.IO) {
            try {
                sharedPreferences.getString("saved_data", "No data available") ?: "No data available"
            } catch (e: Exception) {
                "Error reading saved data: ${e.message}"
            }
        }
    }

    suspend fun saveData(data: String) {
        withContext(Dispatchers.IO) {
            try {
                sharedPreferences.edit {
                    putString("saved_data", data)
                }
            } catch (e: Exception) {
                // Log error but don't throw
            }
        }
    }

    companion object {
        @Volatile
        private var instance: DataRepository? = null

        fun getInstance(context: Context): DataRepository {
            return instance ?: synchronized(this) {
                instance ?: DataRepository(context.applicationContext).also { instance = it }
            }
        }
    }
}