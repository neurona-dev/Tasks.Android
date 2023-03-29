package dev.neurona.tasks.utils;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import dev.neurona.tasks.data.dao.TaskDao;
import dev.neurona.tasks.data.database.AppDatabase;
import dev.neurona.tasks.data.repository.TaskRepository;
import dev.neurona.tasks.ui.addedittask.AddEditTaskViewModel;
import dev.neurona.tasks.ui.main.MainViewModel;

public class Injection {

    public static TaskRepository provideTaskRepository(Context context) {
        TaskDao taskDao = AppDatabase.getInstance(context).taskDao();
        return new TaskRepository(taskDao);
    }

    public static ViewModelProvider.Factory provideMainViewModelFactory(Context context) {
        return new ViewModelProvider.Factory() {
            @NonNull
            @SuppressWarnings("unchecked")
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                if (modelClass.isAssignableFrom(MainViewModel.class)) {
                    TaskRepository taskRepository = provideTaskRepository(context);
                    return (T) new MainViewModel(taskRepository);
                }
                throw new IllegalArgumentException("Unknown ViewModel class");
            }
        };
    }

    public static ViewModelProvider.Factory provideAddEditTaskViewModelFactory(Context context) {
        return new ViewModelProvider.Factory() {
            @NonNull
            @SuppressWarnings("unchecked")
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                if (modelClass.isAssignableFrom(AddEditTaskViewModel.class)) {
                    TaskRepository taskRepository = provideTaskRepository(context);
                    return (T) new AddEditTaskViewModel(taskRepository);
                }
                throw new IllegalArgumentException("Unknown ViewModel class");
            }
        };
    }
}
