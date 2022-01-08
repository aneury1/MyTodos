package com.aneury1.mytodos.Views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.aneury1.mytodos.ViewModel.TodoListViewModel
import com.aneury1.mytodos.utils.TodoListEvent
import com.aneury1.mytodos.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect

@Composable
fun TodoListScreen(
    onNavigate:(UiEvent.Navigate) -> Unit,
    viewModel: TodoListViewModel = hiltViewModel()
)
{
    val todos =  viewModel.todo.collectAsState(initial = emptyList())

    var scaffoldState  = rememberScaffoldState()

    LaunchedEffect(key1= true){
        viewModel.uiEvent.collect{ event->
            when(event){
                is UiEvent.ShowSnackBar->{
                   val result =   scaffoldState.snackbarHostState.showSnackbar(
                         message = event.message)

                    if(result==SnackbarResult.ActionPerformed){
                        viewModel.onEvent(TodoListEvent.OnUndoDeleteClick)
                    }
                }
                is UiEvent.Navigate -> onNavigate(event)
                else -> Unit
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton ={ FloatingActionButton(
            onClick = {
                viewModel.onEvent(TodoListEvent.OnAddTodoClick)
            }
        ){
            Icon(imageVector = Icons.Default.Add,
                 contentDescription ="Add enw ")
        }
        }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(todos.value){ todo->
                TodoItem(todo,
                    onEvent = viewModel::onEvent,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            viewModel.onEvent(TodoListEvent.OnTodoClick(todo))
                        }
                    )
            }
        }
    }

}