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

import java.util.List;

public class BlocksFragment extends Fragment {

    private RecyclerView recyclerView;
    private BlocksAdapter adapter;
    private DatabaseHelper databaseHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blocks, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        databaseHelper = new DatabaseHelper(getContext());
        List<Block> blocks = databaseHelper.getAllBlocks();

        adapter = new BlocksAdapter(blocks, block -> openBlockDetails(block));
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void openBlockDetails(Block block) {
        Intent intent = new Intent(getActivity(), BlockDetailsActivity.class);
        intent.putExtra("BLOCK_ID", block.getId());
        startActivity(intent);
    }
}
