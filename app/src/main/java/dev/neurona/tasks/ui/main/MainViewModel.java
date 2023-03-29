package dev.neurona.tasks.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import dev.neurona.tasks.data.model.Task;
import dev.neurona.tasks.data.repository.TaskRepository;

public class MainViewModel extends ViewModel {
    private TaskRepository taskRepository;
    private LiveData<List<Task>> allTasks;

    public MainViewModel(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
        allTasks = taskRepository.getAllTasks();
    }

    public LiveData<List<Task>> getAllTasks() {
        return allTasks;
    }

    public void deleteTask(Task task) {
        this.taskRepository.deleteTask(task);
    }
}
