package com.aneury1.mytodos.DI

import android.app.Application
import androidx.room.Room
import com.aneury1.mytodos.data.TodoDatabase
import com.aneury1.mytodos.data.TodoRepository
import com.aneury1.mytodos.data.TodoRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase (app: Application): TodoDatabase{
        return Room.databaseBuilder(
            app,
            TodoDatabase::class.java,
            "tododb.db "
        ).build()
    }

    @Provides
    @Singleton
    fun provideRepository(db: TodoDatabase): TodoRepository{
       return TodoRepositoryImpl(db.Todo)
    }


}