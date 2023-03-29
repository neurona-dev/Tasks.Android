package dev.neurona.tasks.ui.addedittask;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;

import dev.neurona.tasks.R;
import dev.neurona.tasks.data.model.Task;
import dev.neurona.tasks.utils.Injection;

public class AddEditTaskActivity extends AppCompatActivity {

    public static final String EXTRA_TASK = "dev.neurona.tasks.EXTRA_TASK";

    private TextInputEditText editTextTitle;
    private TextInputEditText editTextDescription;
    private TextInputEditText editTextDueDate;
    private AddEditTaskViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_task);

        editTextTitle = findViewById(R.id.edit_text_title);
        editTextDescription = findViewById(R.id.edit_text_description);
        editTextDueDate = findViewById(R.id.edit_text_due_date);
        Button buttonSaveTask = findViewById(R.id.button_save_task);

        viewModel = new ViewModelProvider(this, Injection.provideAddEditTaskViewModelFactory(this)).get(AddEditTaskViewModel.class);

        Intent intent = getIntent();
        Task task = (Task) intent.getSerializableExtra(EXTRA_TASK);

        if (task != null) {
            editTextTitle.setText(task.getTitle());
            editTextDescription.setText(task.getDescription());
            editTextDueDate.setText(task.getDueDate());
        }

        buttonSaveTask.setOnClickListener(v -> saveTask(task));
    }

    private void saveTask(Task task) {
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        String dueDate = editTextDueDate.getText().toString();

        if (task == null) {
            viewModel.insertTask(new Task(title, description, dueDate));
        } else {
            task.setTitle(title);
            task.setDescription(description);
            task.setDueDate(dueDate);
            viewModel.updateTask(task);
        }

        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
