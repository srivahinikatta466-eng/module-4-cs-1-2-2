// app/src/main/java/project/background/MainViewModel.kt
package project.background

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel : ViewModel() {

    private val _data = MutableLiveData<String>()
    val data: LiveData<String> = _data

    private val _lastUpdateTime = MutableLiveData<String>()
    val lastUpdateTime: LiveData<String> = _lastUpdateTime

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var dataRepository: DataRepository? = null

    fun initializeRepository(context: Context) {
        try {
            dataRepository = DataRepository.getInstance(context)
            loadSavedData()
        } catch (e: Exception) {
            _data.value = "Error initializing: ${e.message}"
        }
    }

    private fun loadSavedData() {
        val repo = dataRepository ?: return

        viewModelScope.launch {
            try {
                val savedData = withContext(Dispatchers.IO) {
                    repo.getSavedData()
                }
                _data.value = savedData
                updateLastUpdateTime()
            } catch (e: Exception) {
                _data.value = "Error loading saved data: ${e.message}"
            }
        }
    }

    fun refreshData() {
        val repo = dataRepository ?: run {
            _data.value = "Repository not initialized"
            return
        }

        viewModelScope.launch {
            _isLoading.value = true

            try {
                val newData = withContext(Dispatchers.IO) {
                    repo.fetchDataFromApi()
                }

                _data.value = newData

                withContext(Dispatchers.IO) {
                    repo.saveData(newData)
                }

                updateLastUpdateTime()

            } catch (e: Exception) {
                _data.value = "Error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun updateLastUpdateTime() {
        try {
            val dateFormat = SimpleDateFormat("HH:mm:ss dd/MM/yyyy", Locale.getDefault())
            _lastUpdateTime.value = dateFormat.format(Date())
        } catch (e: Exception) {
            _lastUpdateTime.value = "Error getting time"
        }
    }
}