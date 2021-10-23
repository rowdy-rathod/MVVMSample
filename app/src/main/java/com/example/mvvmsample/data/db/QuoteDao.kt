package com.example.mvvmsample.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mvvmsample.data.db.entities.Quote

@Dao
interface QuoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAllQuote(quote: List<Quote>)

    @Query("SELECT * FROM Quote")
    fun getQuotesList(): LiveData<List<Quote>>
}