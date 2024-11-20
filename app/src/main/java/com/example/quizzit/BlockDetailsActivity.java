package com.example.quizzit;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.quizzit.models.Block;

public class BlockDetailsActivity extends AppCompatActivity {

    private TextView blockNameTextView;
    private TextView blockDescriptionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_block_details);

        blockNameTextView = findViewById(R.id.block_name);
        blockDescriptionTextView = findViewById(R.id.block_description);

        int blockId = getIntent().getIntExtra("BLOCK_ID", -1);
        if (blockId != -1) {
            DatabaseHelper databaseHelper = new DatabaseHelper(this);
            Block block = databaseHelper.getBlockById(blockId);
            if (block != null) {
                blockNameTextView.setText(block.getTitle());
                blockDescriptionTextView.setText(block.getDescription());
            }
        }
    }
}