package com.aneury1.mytodos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.aneury1.mytodos.UI.AppTheme
import com.aneury1.mytodos.Views.AddEditTodoScreen
import com.aneury1.mytodos.Views.TodoListScreen
import com.aneury1.mytodos.utils.Routes
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent{
            AppTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Routes.TODO_LIST){
                    composable(Routes.TODO_LIST){
                        TodoListScreen(
                            onNavigate = {
                                navController.navigate(it.route)
                            }
                        )
                    }
                    composable(
                          route= Routes.ADD_EDIT_TODO+"?todoId={todoId}",
                          arguments = listOf(
                              navArgument(name="todoId"){
                                  type = NavType.IntType
                                  defaultValue = -1
                              }
                          )
                    ){
                          AddEditTodoScreen(
                              onPopBackState = {
                                  navController.popBackStack()
                              }
                          )
                    }
                }
            }
        }

    }
}