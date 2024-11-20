package com.example.quizzit;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizzit.data.QuestionRepository;
import com.example.quizzit.models.Question;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExamModeActivity extends AppCompatActivity {

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
            String[] options = {
                    currentQuestion.getOption1(),
                    currentQuestion.getOption2(),
                    currentQuestion.getOption3(),
                    currentQuestion.getOption4()
            };
            for (int i = 0; i < options.length; i++) {
                RadioButton radioButton = new RadioButton(this);
                radioButton.setText(options[i]);
                radioButton.setId(i + 1); // IDs únicos
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