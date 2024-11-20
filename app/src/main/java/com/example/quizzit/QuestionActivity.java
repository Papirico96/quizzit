package com.example.quizzit;

import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizzit.data.QuestionRepository;

import java.util.List;

public class QuestionActivity extends AppCompatActivity {
    private static final String TAG = "QuestionActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        // Crear una instancia del repositorio
        QuestionRepository questionRepo = new QuestionRepository(this);

        // Insertar una pregunta (esto es solo para pruebas, lo puedes hacer cuando sea necesario)
        questionRepo.insertQuestion(1, "¿Cuál es el resultado de 2 + 2?",
                "3", "4", "5", "6", 2);

        // Recuperar las opciones barajadas para la pregunta con id = 1
        List<String> options = questionRepo.getShuffledOptions(1);

        // Mostrar las opciones en Log (para ver las opciones barajadas en Logcat)
        for (String option : options) {
            Log.d(TAG, "Opción: " + option);
        }

        // Si quieres mostrar las opciones en un RadioGroup en la UI, puedes hacerlo de la siguiente manera:
        RadioGroup optionsRadioGroup = findViewById(R.id.radio_group);

        // Limpiar cualquier vista previa en el RadioGroup
        optionsRadioGroup.removeAllViews();

        // Agregar las opciones barajadas como RadioButton al RadioGroup
        for (String option : options) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(option);  // Establecer el texto de la opción
            optionsRadioGroup.addView(radioButton);  // Agregarlo al RadioGroup
        }
    }
}