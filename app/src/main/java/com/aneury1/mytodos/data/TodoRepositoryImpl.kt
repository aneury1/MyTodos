package com.aneury1.mytodos.data

import kotlinx.coroutines.flow.Flow

class TodoRepositoryImpl(private val dao :
                         TodoDAO ): TodoRepository {
    override suspend fun Insert(todo: Todo) {
        dao.Insert(todo)
    }

    override  fun All() : Flow<List<Todo>> {
       return dao.All()
    }

    override suspend fun Delete(todo: Todo) {
        return dao.Delete(todo)
    }

    override suspend fun getById(id: Int): Todo? {
        return dao.getById(id)
    }
}