package com.example.quizzit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizzit.data.BlockRepository;
import com.example.quizzit.data.QuestionRepository;
import com.example.quizzit.models.Question;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CreateBlock extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_block);

        EditText edtName = findViewById(R.id.edtBlockName);
        EditText edtDescription = findViewById(R.id.edtBlockDescription);
        Button btnSave = findViewById(R.id.btnSaveBlock);

        BlockRepository blockRepo = new BlockRepository(this);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString();
                String description = edtDescription.getText().toString();

                if (!name.isEmpty()) {
                    blockRepo.insertBlock(name, description);
                    Toast.makeText(CreateBlock.this, "Bloque guardado", Toast.LENGTH_SHORT).show();
                    finish(); // Vuelve a la actividad anterior
                } else {
                    Toast.makeText(CreateBlock.this, "El nombre no puede estar vacío", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static class ExamModeActivity extends AppCompatActivity {

        private TextView questionTextView;
        private RadioGroup optionsRadioGroup;
        private Button nextButton;
        private List<Question> questions = new ArrayList<>();
        private int currentQuestionIndex = 0;
        private int score = 0;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_exam_mode);

            // Vincular elementos del layout
            questionTextView = findViewById(R.id.questionTextView);
            optionsRadioGroup = findViewById(R.id.optionsRadioGroup);
            nextButton = findViewById(R.id.nextButton);

            // Cargar preguntas
            loadQuestions();

            // Mostrar la primera pregunta
            showQuestion();

            // Manejar la navegación
            nextButton.setOnClickListener(v -> handleNextQuestion());
        }

        private void loadQuestions() {
            // Cargar preguntas desde el repositorio
            QuestionRepository questionRepository = new QuestionRepository(this);
            questions = questionRepository.getQuestionsForExamMode();

            // Seleccionar 10 preguntas aleatorias
            if (questions.size() > 10) {
                Collections.shuffle(questions);
                questions = questions.subList(0, 10);
            }
        }

        private void showQuestion() {
            if (currentQuestionIndex >= 0 && currentQuestionIndex < questions.size()) {
                Question currentQuestion = questions.get(currentQuestionIndex);

                // Configurar el texto de la pregunta
                questionTextView.setText(currentQuestion.getQuestionText());

                // Llenar las opciones en el RadioGroup
                optionsRadioGroup.removeAllViews();
                for (int i = 1; i <= 4; i++) {
                    String option = currentQuestion.getOption(i); // Obtener opción con el método getOption()
                    RadioButton radioButton = new RadioButton(this);
                    radioButton.setText(option);
                    radioButton.setId(i); // IDs únicos para cada opción
                    optionsRadioGroup.addView(radioButton);
                }
            }
        }

        private void handleNextQuestion() {
            // Validar respuesta seleccionada
            int selectedOptionId = optionsRadioGroup.getCheckedRadioButtonId();
            if (selectedOptionId == -1) {
                Toast.makeText(this, "Por favor selecciona una respuesta", Toast.LENGTH_SHORT).show();
                return;
            }

            Question currentQuestion = questions.get(currentQuestionIndex);
            if (selectedOptionId == currentQuestion.getCorrectOption()) {
                score++;
            }

            // Avanzar a la siguiente pregunta
            currentQuestionIndex++;
            if (currentQuestionIndex < questions.size()) {
                showQuestion();
            } else {
                // Mostrar resultados al final
                showResults();
            }
        }

        private void showResults() {
            Intent intent = new Intent(this, ResultActivity.class);
            intent.putExtra("SCORE", score);
            intent.putExtra("TOTAL_QUESTIONS", questions.size());
            startActivity(intent);
            finish();
        }
    }
}