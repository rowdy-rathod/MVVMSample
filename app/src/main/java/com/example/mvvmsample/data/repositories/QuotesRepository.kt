package com.example.mvvmsample.data.repositories

import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mvvmsample.data.db.AppDatabase
import com.example.mvvmsample.data.db.entities.Quote
import com.example.mvvmsample.data.network.MyApi
import com.example.mvvmsample.data.network.SafeApiRequest
import com.example.mvvmsample.data.network.responses.QuotesResponse
import com.example.mvvmsample.data.preferences.SharedPreferenceHelper
import com.example.mvvmsample.util.Coroutines
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class QuotesRepository(
    private val myApi: MyApi,
    private val db: AppDatabase,
    private val preferences: SharedPreferenceHelper
) : SafeApiRequest() {
    private val quotes = MutableLiveData<List<Quote>>()
    private val MINIMUM_INTERVAL: Int = 6

    init {
        quotes.observeForever {
            try {
                saveQuotes(it)
            } catch (e: Exception) {
                e.message
            }
        }
    }

    suspend fun getQuotes(): LiveData<List<Quote>> {
        return withContext(Dispatchers.IO) {
            fetchQuotes()
            db.getQuoteDao().getQuotesList()
        }
    }

    private suspend fun fetchQuotes() {
        val lastSavedAt = preferences.getPreference("lastSaveDate", 1) as CharSequence?
        if (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                lastSavedAt == null || isFetchNeeded(LocalDateTime.parse(lastSavedAt))
            } else {
                TODO("VERSION.SDK_INT < O")
            }
        ) {
            val response = apiRequest {
                myApi.getQuotes()
            }
            quotes.postValue(response.quotes)
        }
    }

    private fun isFetchNeeded(savedAt: LocalDateTime): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ChronoUnit.HOURS.between(savedAt, LocalDateTime.now()) > MINIMUM_INTERVAL
        } else {
            TODO("VERSION.SDK_INT < O")
        }
    }

    private fun saveQuotes(quotes: List<Quote>) {

        Coroutines.io {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                preferences.saveString("lastSaveDate", LocalDateTime.now().toString())
            }
            db.getQuoteDao().saveAllQuote(quotes)
        }
    }

    // if we want to call api directly without save in local database
    suspend fun getList(): QuotesResponse {
        return apiRequest { myApi.getQuotes() }
    }

}