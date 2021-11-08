package com.example.mypolygonapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import okhttp3.*

class MainViewModel: ViewModel() {
    private var webSocket: WebSocket

    private val webSocketListener = object : WebSocketListener() {

        override fun onMessage(webSocket: WebSocket, text: String) {
            super.onMessage(webSocket, text)
            Log.d(TAG, "Web socket receive message $text")
            _receivedMessage.postValue(text)
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            super.onFailure(webSocket, t, response)
            Log.d(TAG, "Web socket connection failed", t)
        }
    }

    init {
        val okHttpClient = OkHttpClient()
        val request = Request.Builder()
            .url(URL)
            .build()

        webSocket = okHttpClient.newWebSocket(request, webSocketListener)
    }

    private val _receivedMessage: MutableLiveData<String> = MutableLiveData()
    val receivedMessage: LiveData<String> = _receivedMessage

    override fun onCleared() {
        webSocket.close(1, "Activity cleared")
        super.onCleared()
    }

    fun sendMessage(message: String) {
        webSocket.send(message)
    }

    companion object {
        private const val URL = "ws://echo.websocket.org/websocket"
        private const val TAG = "VM_TAG"
    }
}