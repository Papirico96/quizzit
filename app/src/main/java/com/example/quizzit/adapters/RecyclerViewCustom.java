//package com.example.quizzit.adapters;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//
//
//import com.example.quizzit.R;
//import com.example.quizzit.models.Question;
//
//
//import java.util.List;
//
//public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {
//
//    private final List<Question> questions;
//    private final Context context;
//    private final EditListener editListener; // Listener para editar
//    private final DeleteListener deleteListener; // Listener para eliminar
//
//    public QuestionAdapter(Context context, List<Question> questions,
//                           EditListener editListener, DeleteListener deleteListener) {
//        this.context = context;
//        this.questions = questions;
//        this.editListener = editListener;
//        this.deleteListener = deleteListener;
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.item_question, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        Question question = questions.get(position);
//        holder.questionText.setText(question.getQuestionText());
//
//        // Configurar el botón Editar
//        holder.btnEdit.setOnClickListener(v -> editListener.onEdit(question));
//
//        // Configurar el botón Eliminar
//        holder.btnDelete.setOnClickListener(v -> deleteListener.onDelete(question));
//    }
//
//    @Override
//    public int getItemCount() {
//        return questions.size();
//    }
//
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//        TextView questionText;
//        Button btnEdit, btnDelete;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            questionText = itemView.findViewById(R.id.tv_question_text);
//            btnEdit = itemView.findViewById(R.id.btn_edit_question);
//            btnDelete = itemView.findViewById(R.id.btn_delete_question);
//        }
//    }
//
//    // Interfaces para manejar clics en editar y eliminar
//    public interface EditListener {
//        void onEdit(Question question);
//    }
//
//    public interface DeleteListener {
//        void onDelete(Question question);
//    }
//}