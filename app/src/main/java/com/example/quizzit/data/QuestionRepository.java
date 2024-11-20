package com.example.quizzit.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.quizzit.DatabaseHelper;
import com.example.quizzit.models.Question;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuestionRepository {
    private DatabaseHelper dbHelper;

    // Constructor
    public QuestionRepository(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // Insertar una pregunta
    public void insertQuestion(int blockId, String questionText, String option1, String option2,
                               String option3, String option4, int correctOption) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("block_id", blockId);
        values.put("question_text", questionText);
        values.put("option1", option1);
        values.put("option2", option2);
        values.put("option3", option3);
        values.put("option4", option4);
        values.put("correct_option", correctOption);
        db.insert("questions", null, values);
        db.close();
    }

    // Recuperar todas las preguntas sin filtros específicos
    private List<Question> getAllQuestions() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Question> questions = new ArrayList<>();

        // Consulta para obtener todas las preguntas
        Cursor cursor = db.rawQuery(
                "SELECT id, block_id, question_text, option1, option2, option3, option4, correct_option FROM questions",
                null
        );

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                int blockId = cursor.getInt(cursor.getColumnIndexOrThrow("block_id"));
                String questionText = cursor.getString(cursor.getColumnIndexOrThrow("question_text"));
                String option1 = cursor.getString(cursor.getColumnIndexOrThrow("option1"));
                String option2 = cursor.getString(cursor.getColumnIndexOrThrow("option2"));
                String option3 = cursor.getString(cursor.getColumnIndexOrThrow("option3"));
                String option4 = cursor.getString(cursor.getColumnIndexOrThrow("option4"));
                int correctOption = cursor.getInt(cursor.getColumnIndexOrThrow("correct_option"));

                // Crear el objeto Question y agregarlo a la lista neno meno mal que emo echo funsiona el blockid polque no arrancaba miermano
                Question question = new Question(id, blockId, questionText, option1, option2, option3, option4, correctOption);
                questions.add(question);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return questions;
    }
    // Obtener preguntas para el modo examen
    public List<Question> getQuestionsForExamMode() {
        return getAllQuestions();
    }

    // Obtener preguntas para el modo práctica
    public List<Question> getQuestionsForPracticeMode() {
        return getAllQuestions();
    }
    // Método para obtener las opciones barajadas de una pregunta dada su ID
    public List<String> getShuffledOptions(int questionId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<String> options = new ArrayList<>();

        // Consulta para obtener la pregunta por ID
        Cursor cursor = db.rawQuery(
                "SELECT option1, option2, option3, option4 FROM questions WHERE id = ?",
                new String[]{String.valueOf(questionId)}
        );

        if (cursor.moveToFirst()) {
            // Recuperar las opciones de la pregunta
            options.add(cursor.getString(cursor.getColumnIndexOrThrow("option1")));
            options.add(cursor.getString(cursor.getColumnIndexOrThrow("option2")));
            options.add(cursor.getString(cursor.getColumnIndexOrThrow("option3")));
            options.add(cursor.getString(cursor.getColumnIndexOrThrow("option4")));
        }

        cursor.close();
        db.close();

        // Barajar las opciones aleatoriamente
        Collections.shuffle(options);

        return options;
    }
}