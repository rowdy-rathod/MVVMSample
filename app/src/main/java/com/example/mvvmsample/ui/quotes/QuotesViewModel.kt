package com.example.mvvmsample.ui.quotes

import androidx.lifecycle.ViewModel
import com.example.mvvmsample.data.repositories.QuotesRepository
import com.example.mvvmsample.util.Coroutines
import com.example.mvvmsample.util.NoInternetException
import com.example.mvvmsample.util.lazyDeferred
import java.io.IOException

class QuotesViewModel(private val repository: QuotesRepository) : ViewModel() {


    val quote by lazyDeferred {
        repository.getQuotes()
    }

//    init {
//        Coroutines.main {
//            getQuotesList()
//        }
//    }
//
//
//    //    =========================Addedd Code  By me ==================================
//    var quotesListener: QuotesListener? = null
//
//    suspend fun getQuotesList() {
//        Coroutines.main {
//            try {
//                val response = repository.getList()
//                quotesListener?.onQuotesList(response.quotes)
//            } catch (e: IOException) {
//                quotesListener?.onQuotesList(emptyList())
//            } catch (e: NoInternetException) {
//                quotesListener?.noInternetConnection(e.message!!)
//            }
//        }
//    }
}