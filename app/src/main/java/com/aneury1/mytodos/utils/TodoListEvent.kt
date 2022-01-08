package com.aneury1.mytodos.utils

import com.aneury1.mytodos.data.Todo

sealed class TodoListEvent {
    data class OnDeleteTodo(val todo: Todo) : TodoListEvent()
    data class OnDoneChange(val todo: Todo, val isDone: Boolean): TodoListEvent()
    object OnUndoDeleteClick : TodoListEvent()
    data class OnTodoClick(val todo: Todo) : TodoListEvent()
    object OnAddTodoClick: TodoListEvent()
}