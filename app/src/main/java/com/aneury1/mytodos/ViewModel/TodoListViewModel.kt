package com.aneury1.mytodos.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aneury1.mytodos.data.Todo
import com.aneury1.mytodos.data.TodoRepository
import com.aneury1.mytodos.utils.Routes
import com.aneury1.mytodos.utils.TodoListEvent
import com.aneury1.mytodos.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val repository : TodoRepository
): ViewModel() {

    val todo = repository.All()

    private val _uiEvent =  Channel<UiEvent>()

    val uiEvent = _uiEvent.receiveAsFlow()

    var deleteTodo : Todo? = null


    fun onEvent(event : TodoListEvent){
        when(event){
            is TodoListEvent.OnTodoClick->{
                sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_TODO+"?/todoId=${event.todo.id}"))
            }
            is TodoListEvent.OnDeleteTodo->{
                viewModelScope.launch {
                    deleteTodo = event.todo
                    repository.Delete(event.todo)
                    sendUiEvent(UiEvent.ShowSnackBar(
                        message = "Todo delete!!",
                        action  = "UNDO"
                    ))
                }
            }
            is TodoListEvent.OnAddTodoClick->{
                sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_TODO))
            }
            is TodoListEvent.OnDoneChange ->{
                 viewModelScope.launch {
                     repository.Insert(event.todo.copy(isDone = event.isDone))
                 }
            }
            is TodoListEvent.OnUndoDeleteClick->{
                  deleteTodo?.let {
                      viewModelScope.launch {
                          repository.Insert(deleteTodo!!)
                      }
                  }
            }
        }
    }
    private fun sendUiEvent(event: UiEvent){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}