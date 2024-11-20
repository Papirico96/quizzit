package com.example.quizzit;

import android.os.Bundle;
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

public class PracticeModeActivity extends AppCompatActivity {

    private TextView questionTextView;
    private RadioGroup optionsRadioGroup;
    private List<Question> questions = new ArrayList<>();
    private int currentQuestionIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_mode);

        // Vincular elementos del layout
        questionTextView = findViewById(R.id.questionTextView);
        optionsRadioGroup = findViewById(R.id.optionsRadioGroup);

        // Cargar preguntas
        loadQuestions();

        // Mostrar la primera pregunta
        showQuestion();

        // Manejar selección de opciones
        optionsRadioGroup.setOnCheckedChangeListener((group, checkedId) -> handleOptionSelection(checkedId));
    }

    private void loadQuestions() {
        // Cargar preguntas desde el repositorio
        QuestionRepository questionRepository = new QuestionRepository(this);
        questions = questionRepository.getQuestionsForPracticeMode();

        // Mezclar las preguntas para la práctica
        Collections.shuffle(questions);
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

    private void handleOptionSelection(int selectedOptionId) {
        Question currentQuestion = questions.get(currentQuestionIndex);

        if (selectedOptionId == currentQuestion.getCorrectOption()) {
            Toast.makeText(this, "¡Correcto!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Incorrecto. La respuesta correcta es: " +
                    currentQuestion.getOption(currentQuestion.getCorrectOption()), Toast.LENGTH_SHORT).show();
        }

        // Avanzar automáticamente a la siguiente pregunta
        currentQuestionIndex++;
        if (currentQuestionIndex < questions.size()) {
            showQuestion();
        } else {
            Toast.makeText(this, "Has completado todas las preguntas de práctica.", Toast.LENGTH_LONG).show();
            finish(); // Cierra la actividad al finalizar
        }
    }
}