package dev.neurona.tasks.ui.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import dev.neurona.tasks.R;
import dev.neurona.tasks.data.model.Task;
import dev.neurona.tasks.ui.addedittask.AddEditTaskActivity;
import dev.neurona.tasks.utils.Injection;

public class MainActivity extends AppCompatActivity implements TaskAdapter.OnItemClickListener {

    private List<Task> taskList;
    private TaskAdapter adapter;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton buttonAddTask = findViewById(R.id.fab_add_task);
        RecyclerView recyclerViewTasks = findViewById(R.id.recycler_view);

        viewModel = new ViewModelProvider(this, Injection.provideMainViewModelFactory(this)).get(MainViewModel.class);

        taskList = new ArrayList<>();
        adapter = new TaskAdapter(taskList, this);

        recyclerViewTasks.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewTasks.setAdapter(adapter);

        viewModel.getAllTasks().observe(this, tasks -> {
            taskList.clear();
            taskList.addAll(tasks);
            adapter.notifyDataSetChanged();
        });

        buttonAddTask.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEditTaskActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onItemClick(int position) {
        Task task = taskList.get(position);
        Intent intent = new Intent(MainActivity.this, AddEditTaskActivity.class);
        intent.putExtra(AddEditTaskActivity.EXTRA_TASK, task);
        startActivity(intent);
    }

    @Override
    public void onDeleteClick(int position) {
        showDeleteConfirmationDialog(position);
    }

    private void showDeleteConfirmationDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_dialog_msg)
                .setTitle(R.string.delete_dialog_title)
                .setPositiveButton(R.string.delete, (dialog, id) -> {
                    // User clicked the "Delete" button, so delete the task.
                    viewModel.deleteTask(adapter.getTaskAt(position));
                })
                .setNegativeButton(R.string.cancel, (dialog, id) -> {
                    // User clicked the "Cancel" button, so dismiss the dialog and continue editing the task.
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
