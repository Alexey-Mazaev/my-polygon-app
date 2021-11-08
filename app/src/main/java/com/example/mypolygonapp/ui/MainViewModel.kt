package com.example.mypolygonapp.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypolygonapp.data.reps.StocksRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import okhttp3.*

class MainViewModel(
    private val stocksRepository: StocksRepository,
) : ViewModel() {

    private val _receivedMessage: MutableLiveData<String> = MutableLiveData()
    val receivedMessage: LiveData<String> = _receivedMessage

    init {
        viewModelScope.launch {
            stocksRepository.stocksInfo
                .onEach { result ->
                    Log.d(TAG, "received result: $result")
                    _receivedMessage.postValue(result)
                }
                .catch { t -> Log.d(TAG, "something went wrong", t) }
                .collect()
        }
    }

    override fun onCleared() {
        super.onCleared()
        stocksRepository.closeConnection()
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}