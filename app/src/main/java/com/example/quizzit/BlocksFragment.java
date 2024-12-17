package com.example.quizzit;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizzit.adapters.BlocksAdapter;
import com.example.quizzit.models.Block;

import java.util.ArrayList;
import java.util.List;

public class BlocksFragment extends Fragment {

    private RecyclerView recyclerView;
    private BlocksAdapter blockAdapter;  // Declara el adaptador correctamente
    private List<Block> blockList;       // Declara la lista de bloques
    private DatabaseHelper databaseHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blocks, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewBlocks);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        databaseHelper = new DatabaseHelper(getContext());

        // Obtener la lista de bloques desde la base de datos
        blockList = databaseHelper.getAllBlocks();

        // Crear y asignar el adaptador al RecyclerView
        blockAdapter = new BlocksAdapter(blockList, new BlocksAdapter.onBlockClickListener() {
            @Override
            public void onBlockClick(Block block) {
                openBlockDetails(block);  // Abre los detalles del bloque
            }
        });
        recyclerView.setAdapter(blockAdapter);

        return view;
    }

    private List<Block> getBlocks() {
        // Simulamos la obtención de bloques (esto debería ser real)
        List<Block> blocks = new ArrayList<>();
        blocks.add(new Block(1, "Matemáticas", "Descripción de Matemáticas"));
        blocks.add(new Block(2, "Historia", "Descripción de Historia"));
        return blocks;
    }

    private void insertSampleBlocks() {
        // Si no hay bloques en la base de datos, insertamos algunos bloques de ejemplo
        if (databaseHelper.getAllBlocks().isEmpty()) {
            databaseHelper.addBlock("Matemáticas", "Descripción de Matemáticas");
            databaseHelper.addBlock("Historia", "Descripción de Historia");
        }
    }

    private void openBlockDetails(Block block) {
        Intent intent = new Intent(getActivity(), BlockDetailsActivity.class);
        intent.putExtra("BLOCK_ID", block.getId());  // Pasamos el ID del bloque
        startActivity(intent);
    }
}