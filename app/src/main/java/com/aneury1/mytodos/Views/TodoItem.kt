package com.aneury1.mytodos.Views

import android.widget.Space
import androidx.compose.foundation.layout.*
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aneury1.mytodos.data.Todo
import com.aneury1.mytodos.utils.TodoListEvent

@Composable
fun TodoItem(
    todo: Todo,
    onEvent:(TodoListEvent)->Unit,
    modifier: Modifier = Modifier
){
    Row(
        modifier= modifier,
        verticalAlignment = Alignment.CenterVertically
    ){
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = todo.title,
                    fontSize = 20.sp
                )
                Spacer(
                    modifier = Modifier.width(8.dp)
                )
                IconButton(
                    onClick = {
                        onEvent(TodoListEvent.OnDeleteTodo(todo))
                    }){
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete todo")
                }
            }
            todo.description.let {
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = it.toString())
            }
        }
        Column {
            Checkbox(
                checked = todo.isDone,
                onCheckedChange = {
                    onEvent(TodoListEvent.OnDoneChange(todo = todo, isDone = it))
                }
            )
        }
    }
}