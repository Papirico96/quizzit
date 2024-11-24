package com.example.quizzit;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;

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

        toolbar.setNavigationOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(this, v);
            MenuInflater inflater = popupMenu.getMenuInflater();
            inflater.inflate(R.menu.navigation_menu, popupMenu.getMenu());
            popupMenu.show();
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
                    showFragment(new BlocksFragment()); // Mostrar el fragmento de bloques
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
        btnExamMode.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ExamModeActivity.class)));

        // Mostrar por defecto el fragmento de bloques
        if (savedInstanceState == null) {
            showFragment(new BlocksFragment());  // Mostrar el fragmento de bloques
        }
    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();
    }
}
