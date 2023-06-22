package com.example.doit.dataApp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

import com.example.doit.models.ToDoTasksModel;
@Dao
public interface TasksDao {
    @Delete
    void deleteTask(ToDoTasksModel toDoTasksModel);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTask(ToDoTasksModel toDoTasksModel);
    @Query("SELECT * From Tasks Where isDone==0")
    List<ToDoTasksModel> getTasks();
    @Query("SELECT * From Tasks Where isDone==1")
   List<ToDoTasksModel>getDoneTasks();
}
