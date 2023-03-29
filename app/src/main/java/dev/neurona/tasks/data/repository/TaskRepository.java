package dev.neurona.tasks.data.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import dev.neurona.tasks.data.dao.TaskDao;
import dev.neurona.tasks.data.model.Task;
import dev.neurona.tasks.utils.ExecutorProvider;

public class TaskRepository {

    private TaskDao taskDao;
    private ExecutorProvider executorProvider;

    public TaskRepository(TaskDao taskDao) {
        this.taskDao = taskDao;
        this.executorProvider = ExecutorProvider.getInstance();
    }

    public LiveData<List<Task>> getAllTasks() {
        return taskDao.getAllTasks();
    }

    public void insertTask(Task task) {
        executorProvider.getExecutor().execute(() -> taskDao.insertTask(task));
    }

    public void updateTask(Task task) {
        executorProvider.getExecutor().execute(() -> taskDao.updateTask(task));
    }

    public void deleteTask(Task task) {
        executorProvider.getExecutor().execute(() -> taskDao.deleteTask(task));
    }
}
