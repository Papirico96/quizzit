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
import androidx.fragment.app.Fragment;
import com.google.android.material.navigation.NavigationView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private ArrayList<MyData> dataList;

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
                    showFragment(new BlocksFragment());
                    break;
                case R.id.createQuestion:
                    startActivity(new Intent(this, CreateBlock.class));
                    break;
                case R.id.testMode:
                    startActivity(new Intent(this, ExamModeActivity.class));
                    break;
                case R.id.examMode:
                    startActivity(new Intent(this, ExamModeActivity.class));
                    break;
            }
            drawerLayout.closeDrawers();
            return true;
        });

        // Botones en la pantalla principal
        Button btnCreateBlock = findViewById(R.id.btnCreateBlock);
        btnCreateBlock.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, CreateBlock.class)));

        Button btnCreateQuestion = findViewById(R.id.btnCreateQuestion);
        btnCreateQuestion.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, QuestionActivity.class)));

        Button btnPracticeMode = findViewById(R.id.btnPracticeMode);
        btnPracticeMode.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, PracticeModeActivity.class)));

        Button btnExamMode = findViewById(R.id.btnExamMode);
        btnExamMode.setOnClickListener(v -> {
            Log.d("MainActivity", "Intent para iniciar ExamModeActivity disparado");
            startActivity(new Intent(MainActivity.this, ExamModeActivity.class));
        });

        // Mostrar por defecto el fragmento de bloques
        if (savedInstanceState == null) {
            showFragment(new BlocksFragment());
        }

        // Inicialización del RecyclerView
        recyclerView = findViewById(R.id.recyclerViewBlocks);  // Asegúrate de tener este ID en tu XML
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // LayoutManager para lista vertical

        // Configuración inicial del adaptador con una lista vacía
        dataList = new ArrayList<>();
        adapter = new MyAdapter(dataList); // Crear el adaptador
        recyclerView.setAdapter(adapter);

        // Simular carga de datos
        loadData();
    }

    // Este método simula la carga de datos de manera asíncrona
    private void loadData() {
        new Thread(() -> {
            try {
                Thread.sleep(2000); // Simula un retraso de 2 segundos
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            runOnUiThread(() -> {
                // Datos simulados
                dataList.clear();
                dataList.add(new MyData("Item 1"));
                dataList.add(new MyData("Item 2"));
                dataList.add(new MyData("Item 3"));
                adapter.notifyDataSetChanged(); // Notificar al adaptador
            });
        }).start();
    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();
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
