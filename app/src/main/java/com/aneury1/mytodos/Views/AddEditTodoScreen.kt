package com.aneury1.mytodos.Views

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aneury1.mytodos.ViewModel.AddEditTodoViewModel
import com.aneury1.mytodos.utils.AddEditTodoEvent
import com.aneury1.mytodos.utils.UiEvent
import kotlinx.coroutines.flow.collect

@Composable
fun AddEditTodoScreen(
    onPopBackState: () -> Unit,
    viewModel: AddEditTodoViewModel = hiltViewModel()
){
    val scaffoldState  = rememberScaffoldState()
    LaunchedEffect(key1=true){
        viewModel.uiEvent.collect {  event->
            when(event){
                is UiEvent.PopBackStack -> onPopBackState()
                is UiEvent.ShowSnackBar ->{
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel =  event.action
                    )
                }
                else ->{
                    Unit
                }
            }
        }
    }
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        floatingActionButton ={ FloatingActionButton(
            onClick = {
                viewModel.onEvent(AddEditTodoEvent.OnSaveTodoClick)
            }
        ){
            Icon(imageVector = Icons.Default.Add,
                contentDescription ="Add enw ")
        }
        }
    ){
        Column(
            modifier = Modifier.fillMaxSize()
        ){
            TextField(
                value = viewModel.title?:"empty",
                onValueChange = {
                    viewModel.onEvent(AddEditTodoEvent.OnTitleChange(it))
                },
                placeholder = {
                    Text(text="title")
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier= Modifier.height(8.dp))
            TextField(
                value = viewModel.description?:"empty",
                onValueChange = {
                    viewModel.onEvent(AddEditTodoEvent.OnDescriptionChange(it))
                },
                placeholder = {
                    Text(text="description")
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = false,
                maxLines = 5
            )
        }
    }
}