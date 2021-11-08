package com.example.mypolygonapp.data.reps

import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import okhttp3.*
import okio.ByteString

class StocksRepositoryImpl(
    okHttpClient: OkHttpClient,
): StocksRepository {

    private val _stocksInfo = MutableStateFlow("initial value")

    override var stocksInfo: StateFlow<String> = _stocksInfo

    private val webSocket: WebSocket

    private val webSocketListener = object : WebSocketListener() {

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            super.onClosed(webSocket, code, reason)
        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
            super.onClosing(webSocket, code, reason)
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            super.onFailure(webSocket, t, response)

            Log.d(DEBUG_TAG, "Web socket failure", t)
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            super.onMessage(webSocket, text)

            Log.d(DEBUG_TAG, "Received text response from the socket: $text")
            _stocksInfo.tryEmit(text)
        }

        override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
            super.onMessage(webSocket, bytes)
        }

        override fun onOpen(webSocket: WebSocket, response: Response) {
            super.onOpen(webSocket, response)

            Log.d(DEBUG_TAG, "Connection opened")

            val authAction = "{\"action\":\"auth\",\"params\":\"$API_KEY\"}"
            webSocket.send(authAction)
        }
    }

    init {
        val request = Request.Builder()
            .url(URL)
            .build()

        webSocket = okHttpClient.newWebSocket(request, webSocketListener)
    }

    override fun closeConnection() {
        webSocket.close(1, "Need to close connection")
    }

    companion object {
        private const val URL = "wss://socket.polygon.io/stocks"
        private const val API_KEY = "quack-quack" //todo - use real api-key when it needs

        private const val DEBUG_TAG = "StockSocketApi"
    }
}