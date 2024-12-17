package com.example.quizzit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizzit.R;
import com.example.quizzit.models.Question;

import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {

    private final List<Question> questions;
    private final Context context;
    private final EditListener editListener;
    private final DeleteListener deleteListener;

    // Constructor del adaptador
    public QuestionAdapter(Context context, List<Question> questions, EditListener editListener, DeleteListener deleteListener) {
        this.context = context;
        this.questions = questions;
        this.editListener = editListener;
        this.deleteListener = deleteListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflar el layout para cada ítem
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_question, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Obtener la pregunta actual
        Question question = questions.get(position);

        // Mostrar el texto de la pregunta
        holder.questionTextView.setText(question.getQuestionText());

        // Mostrar las opciones de la pregunta
        holder.option1TextView.setText(question.getOption1());
        holder.option2TextView.setText(question.getOption2());
        holder.option3TextView.setText(question.getOption3());
        holder.option4TextView.setText(question.getOption4());

        // Configurar el botón de editar
        holder.editButton.setOnClickListener(v -> editListener.onEdit(question));

        // Configurar el botón de eliminar
        holder.deleteButton.setOnClickListener(v -> deleteListener.onDelete(question));
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    // ViewHolder interno para el adaptador
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView questionTextView, option1TextView, option2TextView, option3TextView, option4TextView;
        Button editButton, deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // Inicializar las vistas
            questionTextView = itemView.findViewById(R.id.questionTextView);  // TextView para el texto de la pregunta
            option1TextView = itemView.findViewById(R.id.option1TextView);    // TextView para opción 1
            option2TextView = itemView.findViewById(R.id.option2TextView);    // TextView para opción 2
            option3TextView = itemView.findViewById(R.id.option3TextView);    // TextView para opción 3
            option4TextView = itemView.findViewById(R.id.option4TextView);    // TextView para opción 4
            editButton = itemView.findViewById(R.id.editButton);              // Botón para editar
            deleteButton = itemView.findViewById(R.id.deleteButton);          // Botón para eliminar
        }
    }

    // Interfaces para manejar las acciones de editar y eliminar
    public interface EditListener {
        void onEdit(Question question);
    }

    public interface DeleteListener {
        void onDelete(Question question);
    }
}
