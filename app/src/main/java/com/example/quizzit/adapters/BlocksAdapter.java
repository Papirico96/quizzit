package com.example.quizzit.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.quizzit.R;
import com.example.quizzit.models.Block;
import java.util.List;

public class BlocksAdapter extends RecyclerView.Adapter<BlocksAdapter.BlockViewHolder> {

    private List<Block> blocks;
    private OnBlockClickListener listener;

    public interface OnBlockClickListener {
        void onBlockClick(Block block);
    }

    public BlocksAdapter(List<Block> blocks, OnBlockClickListener listener) {
        this.blocks = blocks;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BlockViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_block, parent, false);
        return new BlockViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BlockViewHolder holder, int position) {
        Block block = blocks.get(position);
        holder.titleTextView.setText(block.getTitle());  // Cambiado a getTitle()
        holder.descriptionTextView.setText(block.getDescription());
        holder.itemView.setOnClickListener(v -> listener.onBlockClick(block));
    }

    @Override
    public int getItemCount() {
        return blocks.size();
    }

    class BlockViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, descriptionTextView;

        public BlockViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.block_title);
            descriptionTextView = itemView.findViewById(R.id.block_description);
        }

        public void bind(Block block) {
            titleTextView.setText(block.getTitle());
            descriptionTextView.setText(block.getDescription());
            itemView.setOnClickListener(v -> listener.onBlockClick(block));
        }
    }
}
