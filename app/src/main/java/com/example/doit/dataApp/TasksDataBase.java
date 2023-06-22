package com.example.doit.dataApp;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.doit.models.ToDoTasksModel;

@Database(entities = {ToDoTasksModel.class},version = 1)
public abstract class TasksDataBase extends RoomDatabase {
 abstract public TasksDao tasksDao();
}
