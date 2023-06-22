package com.example.doit.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Tasks")
public class ToDoTasksModel {
    @PrimaryKey (autoGenerate = true)
    int id;
    boolean isDone ;
    String toDoTaskBody;
   public ToDoTasksModel(boolean isDone, String toDoTaskBody){
      this.isDone =isDone;
      this.toDoTaskBody=toDoTaskBody;
    }
    public ToDoTasksModel( String toDoTaskBody){
        this.toDoTaskBody=toDoTaskBody;
    }
    public ToDoTasksModel() {

    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public String getToDoTaskBody() {
        return toDoTaskBody;
    }

    public void setToDoTaskBody(String toDoTaskBody) {
        this.toDoTaskBody = toDoTaskBody;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
