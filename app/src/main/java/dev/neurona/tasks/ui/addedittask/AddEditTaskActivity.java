package dev.neurona.tasks.ui.addedittask;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.textfield.TextInputEditText;
import java.util.Calendar;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        ImageButton buttonDueDate = findViewById(R.id.button_due_date);

        viewModel = new ViewModelProvider(this, Injection.provideAddEditTaskViewModelFactory(this)).get(AddEditTaskViewModel.class);

        Intent intent = getIntent();
        Task task = (Task) intent.getSerializableExtra(EXTRA_TASK);

        if (task != null) {
            editTextTitle.setText(task.getTitle());
            editTextDescription.setText(task.getDescription());

            // Format the date as a string
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dueDateString = sdf.format(task.getDueDate());
            editTextDueDate.setText(dueDateString);
        }

        editTextDueDate.setOnClickListener(v -> showDatePickerDialog());
        buttonDueDate.setOnClickListener(v -> showDatePickerDialog());
        buttonSaveTask.setOnClickListener(v -> saveTask(task));
    }

    private void saveTask(Task task) {
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        String dueDateString = editTextDueDate.getText().toString();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dueDate = null;
        try {
            dueDate = sdf.parse(dueDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

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

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, selectedYear, selectedMonth, selectedDayOfMonth) -> {
            selectedMonth++; // Months are 0-indexed in the DatePicker
            String date = String.format("%04d-%02d-%02d", selectedYear, selectedMonth, selectedDayOfMonth);
            editTextDueDate.setText(date);
        }, year, month, day);

        datePickerDialog.show();
    }
}
