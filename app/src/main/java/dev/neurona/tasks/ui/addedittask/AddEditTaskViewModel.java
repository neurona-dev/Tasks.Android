package dev.neurona.tasks.ui.addedittask;

import androidx.lifecycle.ViewModel;

import dev.neurona.tasks.data.model.Task;
import dev.neurona.tasks.data.repository.TaskRepository;

public class AddEditTaskViewModel extends ViewModel {
    private TaskRepository taskRepository;

    public AddEditTaskViewModel(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void insertTask(Task task) {
        taskRepository.insertTask(task);
    }

    public void updateTask(Task task) {
        taskRepository.updateTask(task);
    }
}
