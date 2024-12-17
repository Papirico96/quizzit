package com.example.quizzit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.PopupMenu;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.quizzit.adapters.BlocksAdapter;
import com.example.quizzit.models.QuestionBlock;
import com.example.quizzit.models.Block;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private RecyclerView recyclerView;
    private BlocksAdapter blockAdapter;
    private List<Block> blocks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar DrawerLayout y NavigationView
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);

        // Inicializar Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Configurar el ícono del toolbar para mostrar un PopupMenu
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.menu_options) {
                showPopupMenu(toolbar);
                return true;
            }
            return false;
        });

        // Configurar el toggle para el menú lateral
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Configurar el menú lateral
        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.createBlock:
                    startActivity(new Intent(this, CreateBlock.class));
                    break;
                case R.id.createQuestion:
                    startActivity(new Intent(this, CreateQuestionActivity.class));
                    break;
                case R.id.examMode:
                    startActivity(new Intent(this, ExamModeActivity.class));
                    break;
            }
            drawerLayout.closeDrawers();
            return true;
        });

        // Botón de "Crear Pregunta"
        Button btnCreateQuestion = findViewById(R.id.btnCreateQuestion);
        btnCreateQuestion.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CreateQuestionActivity.class);
            startActivity(intent);
        });

        // Configuración del RecyclerView para mostrar los bloques seleccionables
        recyclerView = findViewById(R.id.recyclerViewBlocks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Cargar los datos de bloques desde la base de datos
        blocks = getBlocksFromDatabase();

        // Configurar el adaptador para los bloques
        blockAdapter = new BlocksAdapter(blocks, this::onBlockSelected);
        recyclerView.setAdapter(blockAdapter);
    }

    private void onBlockSelected(Block block) {
        Log.d("MainActivity", "Bloque seleccionado: " + block.getName());
        // Abrir una nueva actividad para mostrar las preguntas de este bloque
        Intent intent = new Intent(this, BlockDetailsActivity.class); // Nueva actividad
        intent.putExtra("BLOCK_ID", block.getId()); // Pasar el ID del bloque seleccionado
        startActivity(intent);
    }

    private List<Block> getBlocksFromDatabase() {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        return databaseHelper.getAllBlocks(); // Obtener bloques desde la base de datos
    }

    private void showPopupMenu(Toolbar toolbar) {
        PopupMenu popupMenu = new PopupMenu(this, toolbar.findViewById(R.id.menu_options));
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.createBlock:
                    return true;
                case R.id.createQuestion:
                    return true;
                default:
                    return false;
            }
        });
    }
}
