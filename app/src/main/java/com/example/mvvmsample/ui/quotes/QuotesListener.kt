package com.example.mvvmsample.ui.quotes

import com.example.mvvmsample.data.db.entities.Quote

interface QuotesListener {
    fun onQuotesList(quotes: List<Quote>)
    fun noInternetConnection(message: String)
}