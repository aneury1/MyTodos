package com.aneury1.mytodos.ViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aneury1.mytodos.data.Todo
import com.aneury1.mytodos.data.TodoRepository
import com.aneury1.mytodos.utils.AddEditTodoEvent
import com.aneury1.mytodos.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditTodoViewModel @Inject constructor(
    private val repository: TodoRepository,
    val savedStateHandle: SavedStateHandle
): ViewModel() {
    var todo by mutableStateOf<Todo?>(null)
        private set
    var title by mutableStateOf<String?>("")
        private set
    var description by mutableStateOf<String?>("")
        private set

    private val _uiEvent =  Channel<UiEvent>()

    val uiEvent = _uiEvent.receiveAsFlow()

    init{
        val todoId = savedStateHandle.get<Int>("todoId")!!
        if(todoId != -1){
            viewModelScope.launch {
            repository.getById(todoId)?.let { todo->
                   title = todo.title
                   description  = todo.description
                   this@AddEditTodoViewModel.todo = todo
                }
            }
        }
    }
    private fun sendUiEvent(event: UiEvent){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
    fun onEvent(event: AddEditTodoEvent){
        when(event){
            is AddEditTodoEvent.OnTitleChange->{
                title = event.title
            }
            is AddEditTodoEvent.OnDescriptionChange->{
                description = event.description
            }
            is AddEditTodoEvent.OnSaveTodoClick->{
                viewModelScope.launch {
                    var saveTitle = title !!
                    title = title!!
                    if(title!!.isBlank()){
                        sendUiEvent(UiEvent.ShowSnackBar(
                            message = "title can't be empty",
                            action = " "
                        ))
                        return@launch
                    }
                    repository.Insert(
                        Todo(
                            title = saveTitle,
                            description = description,
                            isDone = todo?.isDone ?: false
                        )
                    )
                    sendUiEvent(UiEvent.PopBackStack)
                }
            }
        }
    }
}