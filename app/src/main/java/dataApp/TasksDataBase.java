package dataApp;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import models.ToDoTasksModel;

@Database(entities = {ToDoTasksModel.class},version = 1)
public abstract class TasksDataBase extends RoomDatabase {
 abstract public TasksDao tasksDao();
}
