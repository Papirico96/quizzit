package com.example.quizzit;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizzit.models.Block;
import com.example.quizzit.models.Question;
import com.example.quizzit.adapters.QuestionAdapter;



import java.util.Collections;
import java.util.List;

public class BlockDetailsActivity extends AppCompatActivity {
    private TextView titleTextView, descriptionTextView;
    private RecyclerView recyclerView;
    private EditText etQuestionText, etOption1, etOption2, etOption3, etOption4;
    private Button btnSaveQuestion;
    private RecyclerView questionsRecyclerView;
    private QuestionAdapter questionAdapter;
    private List<Question> questions = new ArrayList<>();

    private DatabaseHelper databaseHelper;
    private int blockId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_block_details);

        // Inicializar el DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Vincular vistas
        titleTextView = findViewById(R.id.block_title_detail);
        descriptionTextView = findViewById(R.id.block_description_detail);
        recyclerView = findViewById(R.id.recyclerViewQuestions);

        etQuestionText = findViewById(R.id.etQuestionText);
        etOption1 = findViewById(R.id.etOption1);
        etOption2 = findViewById(R.id.etOption2);
        etOption3 = findViewById(R.id.etOption3);
        etOption4 = findViewById(R.id.etOption4);
        btnSaveQuestion = findViewById(R.id.btnSaveQuestion);

        // Configurar RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Obtener blockId desde el Intent
        blockId = getIntent().getIntExtra("blockId", -1);

        if (blockId != -1) {
            loadBlockDetails(blockId);
        } else {
            Toast.makeText(this, "Error: Block ID no válido.", Toast.LENGTH_SHORT).show();
            finish(); // Finaliza la actividad si el ID no es válido
        }

        // Manejar el botón de guardar pregunta
        btnSaveQuestion.setOnClickListener(v -> saveQuestion());
    }

    private void loadBlockDetails(int blockId) {
        // Obtener detalles del bloque
        Block block = databaseHelper.getBlockById(blockId);
        if (block != null) {
            titleTextView.setText(block.getName());
            descriptionTextView.setText(block.getDescription());

            // Obtener las preguntas asociadas
            List<Question> questions = databaseHelper.getQuestionsForBlock(blockId);

            // Mezclar las preguntas (opcional)
            Collections.shuffle(questions);

            // Configurar adaptador del RecyclerView
            QuestionAdapter adapter = new QuestionAdapter(this, questions, question -> {
                // Lógica para editar pregunta (puedes implementar esto más adelante)
            }, question -> {
                // Lógica para eliminar pregunta
                databaseHelper.deleteQuestion(question.getId());
                loadBlockDetails(blockId); // Recargar datos después de eliminar
            });

            recyclerView.setAdapter(adapter);
        } else {
            Toast.makeText(this, "Error: Bloque no encontrado.", Toast.LENGTH_SHORT).show();
        }
    }


    private void saveQuestion() {
        String questionText = etQuestionText.getText().toString().trim();
        String option1 = etOption1.getText().toString().trim();
        String option2 = etOption2.getText().toString().trim();
        String option3 = etOption3.getText().toString().trim();
        String option4 = etOption4.getText().toString().trim();

        if (questionText.isEmpty() || option1.isEmpty() || option2.isEmpty() ||
                option3.isEmpty() || option4.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Guardar la nueva pregunta en la base de datos
        databaseHelper.addQuestion(blockId, questionText, option1, option2, option3, option4, 1); // Por defecto, la opción 1 es la correcta
        Toast.makeText(this, "Pregunta guardada.", Toast.LENGTH_SHORT).show();

        // Limpiar campos
        etQuestionText.setText("");
        etOption1.setText("");
        etOption2.setText("");
        etOption3.setText("");
        etOption4.setText("");

        // Recargar las preguntas en el bloque
        loadBlockDetails(blockId);
    }
}
