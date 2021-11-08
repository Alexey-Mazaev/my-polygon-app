package com.example.mypolygonapp.data.reps

import kotlinx.coroutines.flow.StateFlow

interface StocksRepository {

    var stocksInfo: StateFlow<String>

    fun closeConnection()
}