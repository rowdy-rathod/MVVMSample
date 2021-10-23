package com.example.mvvmsample.data.network.responses

import com.example.mvvmsample.data.db.entities.Quote

data class QuotesResponse(
    val isSuccessful: Boolean?,
    val quotes: List<Quote>,
)