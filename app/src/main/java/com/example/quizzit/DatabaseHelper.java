package com.example.quizzit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.quizzit.models.Block;
import com.example.quizzit.models.Question;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper {

    // Nombre y versión de la base de datos
    private static final String DATABASE_NAME = "bbddQuizzit.db";
    private static final int DATABASE_VERSION = 1;

    // Constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear tabla 'blocks'
        db.execSQL("CREATE TABLE blocks (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title TEXT NOT NULL, " +
                "description TEXT)");

        // Crear tabla 'questions'
        db.execSQL("CREATE TABLE questions (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "block_id INTEGER NOT NULL, " +
                "question_text TEXT NOT NULL, " +
                "option1 TEXT NOT NULL, " +
                "option2 TEXT NOT NULL, " +
                "option3 TEXT NOT NULL, " +
                "option4 TEXT NOT NULL, " +
                "correct_option INTEGER NOT NULL, " +
                "FOREIGN KEY(block_id) REFERENCES blocks(id) ON DELETE CASCADE)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Manejar actualizaciones de esquema (si cambias la base de datos)
        db.execSQL("DROP TABLE IF EXISTS questions");
        db.execSQL("DROP TABLE IF EXISTS blocks");
        onCreate(db);


    }
    public List<Block> getAllBlocks() {
        List<Block> blocks = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM blocks", null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                blocks.add(new Block(id, title, description));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return blocks;
    }
    public Block getBlockById(int blockId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM blocks WHERE id = ?", new String[]{String.valueOf(blockId)});

        if (cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
            String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
            cursor.close();
            return new Block(id, title, description);
        }

        cursor.close();
        return null;
    }
    public void addBlock(String title, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("description", description);
        db.insert("blocks", null, values);
    }

    public List<Question> getQuestionsForBlock(int blockId) {
        List<Question> questions = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM questions WHERE block_id = ?", new String[]{String.valueOf(blockId)});
        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                    String questionText = cursor.getString(cursor.getColumnIndexOrThrow("question_text"));
                    String option1 = cursor.getString(cursor.getColumnIndexOrThrow("option1"));
                    String option2 = cursor.getString(cursor.getColumnIndexOrThrow("option2"));
                    String option3 = cursor.getString(cursor.getColumnIndexOrThrow("option3"));
                    String option4 = cursor.getString(cursor.getColumnIndexOrThrow("option4"));
                    int correctOption = cursor.getInt(cursor.getColumnIndexOrThrow("correct_option"));

                    questions.add(new Question(id, blockId, questionText, option1, option2, option3, option4, correctOption));
                } while (cursor.moveToNext());
            }
        } else {
            Log.d("DatabaseHelper", "No questions found for block_id: " + blockId);
        }
        if (cursor != null) cursor.close();
        return questions;
    }

    public void addQuestion(int blockId, String questionText, String option1, String option2, String option3, String option4, int correctOption) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("block_id", blockId);
        values.put("question_text", questionText); // Cambiado a "question_text"
        values.put("option1", option1);
        values.put("option2", option2);
        values.put("option3", option3);
        values.put("option4", option4);
        values.put("correct_option", correctOption);
        db.insert("questions", null, values);
    }
    public void deleteQuestion(int questionId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("questions", "id = ?", new String[]{String.valueOf(questionId)});
    }


}
