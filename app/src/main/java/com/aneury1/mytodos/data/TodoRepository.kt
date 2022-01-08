package com.aneury1.mytodos.data


import kotlinx.coroutines.flow.Flow

interface TodoRepository {

    suspend fun Insert(todo: Todo)

    fun All() : Flow<List<Todo>>


    suspend fun Delete(todo: Todo)

    suspend fun getById(id :Int ) : Todo?
}