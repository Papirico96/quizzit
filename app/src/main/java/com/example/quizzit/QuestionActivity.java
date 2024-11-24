package com.example.quizzit;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizzit.adapters.QuestionAdapter;
import com.example.quizzit.data.QuestionRepository;
import com.example.quizzit.models.Question;

public class QuestionActivity extends AppCompatActivity {

    private QuestionRepository questionRepo; // Maneja la base de datos
    private RecyclerView recyclerView; // Muestra la lista de preguntas
    private QuestionAdapter adapter; // Adaptador del RecyclerView
    private int blockId; // ID del bloque actual para asociar preguntas

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        // Recuperar el ID del bloque desde el Intent
        blockId = getIntent().getIntExtra("BLOCK_ID", -1);
        if (blockId == -1) {
            Toast.makeText(this, "No se seleccionó ningún bloque.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Inicializar el repositorio y vistas
        questionRepo = new QuestionRepository(this);
        recyclerView = findViewById(R.id.recycler_view_questions);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Cargar preguntas del bloque actual
        loadQuestions();

        // Configurar botón para guardar preguntas
        Button btnSaveQuestion = findViewById(R.id.btn_save_question);
        btnSaveQuestion.setOnClickListener(v -> saveQuestion());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Infla el menú desde XML
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation_menu, menu);  // Asegúrate de que navigation_menu esté en res/menu
        return true;
    }

    private void loadQuestions() {
        // Obtener preguntas asociadas al bloque
        List<Question> questions = questionRepo.getQuestionsByBlock(blockId);
        // Configurar el adaptador del RecyclerView
        adapter = new QuestionAdapter(this, questions, this::showEditDialog, this::deleteQuestion);
        recyclerView.setAdapter(adapter);
    }

    private void saveQuestion() {
        // Recuperar datos de los campos
        EditText etQuestionText = findViewById(R.id.questionTextView);
        EditText etOption1 = findViewById(R.id.option1TextView);
        EditText etOption2 = findViewById(R.id.option2TextView);
        EditText etOption3 = findViewById(R.id.option3TextView);
        EditText etOption4 = findViewById(R.id.option4TextView);

        String questionText = etQuestionText.getText().toString();
        String option1 = etOption1.getText().toString();
        String option2 = etOption2.getText().toString();
        String option3 = etOption3.getText().toString();
        String option4 = etOption4.getText().toString();

        // Validar campos
        if (questionText.isEmpty() || option1.isEmpty() || option2.isEmpty() || option3.isEmpty() || option4.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Crear la pregunta con la opción correcta como la primera opción
        int originalCorrectOption = 1;
        List<String> options = new ArrayList<>();
        options.add(option1);
        options.add(option2);
        options.add(option3);
        options.add(option4);

        // Hacer un shuffle de las opciones
        Collections.shuffle(options);

        // La opción correcta es la que está en la primera posición después del shuffle
        int correctOption = options.indexOf(option1) + 1;  // La opción correcta será la original option1, que ahora será en alguna posición aleatoria

        // Insertar la pregunta en la base de datos
        insertQuestion(blockId, questionText, option1, option2, option3, option4, correctOption);

        // Notificar al usuario
        Toast.makeText(this, "Pregunta guardada correctamente.", Toast.LENGTH_SHORT).show();

        // Limpiar campos
        etQuestionText.setText("");
        etOption1.setText("");
        etOption2.setText("");
        etOption3.setText("");
        etOption4.setText("");

        // Recargar preguntas en la lista
        loadQuestions();
    }

    private void insertQuestion(int blockId, String questionText, String option1, String option2, String option3, String option4, int correctOption) {
        Question newQuestion = new Question(0, blockId, questionText, option1, option2, option3, option4, correctOption);
        questionRepo.insertQuestion(
                blockId,
                newQuestion.getQuestionText(),
                newQuestion.getOption1(),
                newQuestion.getOption2(),
                newQuestion.getOption3(),
                newQuestion.getOption4(),
                newQuestion.getCorrectOption()
        ); // Llamada al repositorio corregida
    }
    private void showEditDialog(Question question) {
        // Crear un diálogo personalizado
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_edit_question, null);
        builder.setView(dialogView);

        EditText etQuestionText = dialogView.findViewById(R.id.et_edit_question_text);
        EditText etOption1 = dialogView.findViewById(R.id.et_edit_option_1);
        EditText etOption2 = dialogView.findViewById(R.id.et_edit_option_2);
        EditText etOption3 = dialogView.findViewById(R.id.et_edit_option_3);
        EditText etOption4 = dialogView.findViewById(R.id.et_edit_option_4);

        // Prellenar los datos de la pregunta
        etQuestionText.setText(question.getQuestionText());
        etOption1.setText(question.getOption1());
        etOption2.setText(question.getOption2());
        etOption3.setText(question.getOption3());
        etOption4.setText(question.getOption4());

        // Configurar botones
        builder.setPositiveButton("Guardar", (dialog, which) -> {
            // Actualizar los datos de la pregunta
            question.setQuestionText(etQuestionText.getText().toString());
            question.setOption1(etOption1.getText().toString());
            question.setOption2(etOption2.getText().toString());
            question.setOption3(etOption3.getText().toString());
            question.setOption4(etOption4.getText().toString());

            if (etQuestionText.getText().toString().isEmpty() ||
                    etOption1.getText().toString().isEmpty() ||
                    etOption2.getText().toString().isEmpty() ||
                    etOption3.getText().toString().isEmpty() ||
                    etOption4.getText().toString().isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Actualizar en la base de datos
            questionRepo.updateQuestion(question);
            loadQuestions(); // Recargar preguntas
            Toast.makeText(this, "Pregunta actualizada correctamente.", Toast.LENGTH_SHORT).show();
        });

        builder.setNegativeButton("Cancelar", null);
        builder.create().show();
    }

    private void deleteQuestion(Question question) {
        questionRepo.deleteQuestion(question.getId()); // Eliminar de la base de datos
        loadQuestions(); // Recargar preguntas
        Toast.makeText(this, "Pregunta eliminada correctamente.", Toast.LENGTH_SHORT).show();
    }

    // Inflar el menú
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.createBlock:
                // Acción cuando se selecciona "Crear Bloque"
                return true;
            case R.id.createQuestion:
                // Acción cuando se selecciona "Crear Pregunta"
                return true;
            case R.id.testMode:
                // Acción cuando se selecciona "Modo Prueba"
                return true;
            case R.id.examMode:
                // Acción cuando se selecciona "Modo Examen"
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}


