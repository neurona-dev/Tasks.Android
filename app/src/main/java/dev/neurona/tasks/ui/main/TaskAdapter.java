package dev.neurona.tasks.ui.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;

import dev.neurona.tasks.R;
import dev.neurona.tasks.data.model.Task;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> taskList;
    private OnItemClickListener onItemClickListener;

    public TaskAdapter(List<Task> taskList, OnItemClickListener onItemClickListener) {
        this.taskList = taskList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_list_item, parent, false);
        return new TaskViewHolder(itemView, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.textViewTitle.setText(task.getTitle());
        holder.textViewDescription.setText(task.getDescription());

        // Format the date as a string
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dueDateString = sdf.format(task.getDueDate());
        holder.textViewDueDate.setText(dueDateString);

        holder.checkBoxCompleted.setChecked(task.isCompleted());
        holder.checkBoxCompleted.setOnCheckedChangeListener((buttonView, isChecked) -> {
            int currentPosition = holder.getAdapterPosition();
            if (onItemClickListener != null && currentPosition != RecyclerView.NO_POSITION) {
                onItemClickListener.onCheckedChanged(currentPosition, isChecked);
            }
        });

        holder.buttonDelete.setOnClickListener(v -> {
            int currentPosition = holder.getAdapterPosition();
            if (onItemClickListener != null && currentPosition != RecyclerView.NO_POSITION) {
                onItemClickListener.onDeleteClick(currentPosition);
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public Task getTaskAt(int position) {
        return taskList.get(position);
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle;
        TextView textViewDescription;
        TextView textViewDueDate;
        CheckBox checkBoxCompleted;
        ImageButton buttonDelete;

        TaskViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
            textViewDueDate = itemView.findViewById(R.id.text_view_due_date);
            checkBoxCompleted = itemView.findViewById(R.id.check_box_completed);
            buttonDelete = itemView.findViewById(R.id.button_delete);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (onItemClickListener != null && position != RecyclerView.NO_POSITION) {
                    onItemClickListener.onItemClick(position);
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onDeleteClick(int position);
        void onCheckedChanged(int position, boolean isChecked);
    }
}
