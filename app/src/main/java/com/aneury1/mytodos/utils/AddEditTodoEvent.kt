package com.aneury1.mytodos.utils

sealed class AddEditTodoEvent{
    data class OnTitleChange(val title: String):AddEditTodoEvent()
    data class OnDescriptionChange(val description: String):AddEditTodoEvent()
    object OnSaveTodoClick:AddEditTodoEvent()
}
