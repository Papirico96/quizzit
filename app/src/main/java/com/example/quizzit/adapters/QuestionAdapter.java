package com.example.quizzit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.NonNull;

import com.example.quizzit.R;
import com.example.quizzit.models.Question;

import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {

    private final List<Question> questions;
    private final Context context;
    private final EditListener editListener; // Listener para editar
    private final DeleteListener deleteListener; // Listener para eliminar

    public QuestionAdapter(Context context, List<Question> questions,
                           EditListener editListener, DeleteListener deleteListener) {
        this.context = context;
        this.questions = questions;
        this.editListener = editListener;
        this.deleteListener = deleteListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflar el layout para cada ítem de la lista
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_question, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Obtener la pregunta en la posición correspondiente
        Question question = questions.get(position);
        holder.questionTextView.setText(question.getQuestionText());

        // Aquí se muestran las opciones de respuesta
        holder.option1TextView.setText(question.getOption(1));
        holder.option2TextView.setText(question.getOption(2));
        holder.option3TextView.setText(question.getOption(3));
        holder.option4TextView.setText(question.getOption(4));

        // Acción para editar la pregunta
        holder.editButton.setOnClickListener(v -> editListener.onEdit(question));

        // Acción para eliminar la pregunta
        holder.deleteButton.setOnClickListener(v -> deleteListener.onDelete(question));
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Declarar los elementos de la vista
        TextView questionTextView;
        TextView option1TextView, option2TextView, option3TextView, option4TextView;
        Button editButton, deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Inicializar los elementos de la vista
            questionTextView = itemView.findViewById(R.id.questionTextView);
            option1TextView = itemView.findViewById(R.id.option1TextView);
            option2TextView = itemView.findViewById(R.id.option2TextView);
            option3TextView = itemView.findViewById(R.id.option3TextView);
            option4TextView = itemView.findViewById(R.id.option4TextView);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }

    // Interfaces para manejar clics en editar y eliminar
    public interface EditListener {
        void onEdit(Question question);
    }

    public interface DeleteListener {
        void onDelete(Question question);
    }
}
