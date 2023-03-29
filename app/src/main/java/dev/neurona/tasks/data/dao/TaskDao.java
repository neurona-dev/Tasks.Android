package dev.neurona.tasks.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import dev.neurona.tasks.data.model.Task;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM task_table")
    LiveData<List<Task>> getAllTasks();

    @Insert
    long insertTask(Task task);

    @Update
    int updateTask(Task task);

    @Delete
    int deleteTask(Task task);
}
