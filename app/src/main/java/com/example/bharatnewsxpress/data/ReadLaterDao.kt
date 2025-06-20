package com.example.bharatnewsxpress.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface ReadLaterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: ArticleEntity)
    @Delete
    suspend fun delete(article: ArticleEntity)

    @Query("Select * from read_later_articles Order by rowid DESC")
    fun getAll(): Flow<List<ArticleEntity>>

}