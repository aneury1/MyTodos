package com.aneury1.mytodos.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun Insert(todo: Todo)

    @Query("SELECT * FROM todo")
    fun All() : Flow<List<Todo>>

    @Delete
    suspend fun Delete(todo: Todo)

    @Query("Select * from todo where id= :id ")
    suspend fun getById(id :Int ) : Todo?
}