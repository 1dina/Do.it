package dataApp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

import models.ToDoTasksModel;
@Dao
public interface TasksDao {
    @Delete
    void deleteTask(ToDoTasksModel toDoTasksModel);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTask(ToDoTasksModel toDoTasksModel);
    @Query("SELECT * From Tasks")
    List<ToDoTasksModel> getAll();
}
