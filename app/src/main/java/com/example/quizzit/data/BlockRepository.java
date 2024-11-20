package com.example.quizzit.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.quizzit.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class BlockRepository {
    private DatabaseHelper dbHelper;

    // Constructor
    public BlockRepository(Context context) {
        dbHelper = new DatabaseHelper(context);
    }
    // Insertar un bloque temático

    public void insertBlock(String name, String description) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("description", description);
        db.insert("blocks", null, values);
        db.close();
    }
    // Obtener todos los bloques
    public List<String> getAllBlocks() {
        List<String> blocks = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM blocks", null);

        if (cursor.moveToFirst()) {
            do {
                blocks.add(cursor.getString(1)); // Nombre del bloque
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return blocks;
    }
}