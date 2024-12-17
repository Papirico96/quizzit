package com.example.quizzit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<MyData> dataList;
    private OnItemClickListener listener;

    // Interfaz para manejar clics en los elementos
    public interface OnItemClickListener {
        void onItemClick(MyData data);
    }

    // Constructor modificado
    public MyAdapter(List<MyData> dataList, OnItemClickListener listener) {
        this.dataList = dataList;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MyData item = dataList.get(position);
        holder.bind(item, listener); // Vincula los datos y el listener
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView descriptionTextView;

        ViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView); // Referencia al TextView para el título
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView); // Referencia al TextView para la descripción
        }

        void bind(final MyData data, final OnItemClickListener listener) {
            titleTextView.setText(data.getTitle());  // Asigna el título
            descriptionTextView.setText(data.getDescription());  // Asigna la descripción
            itemView.setOnClickListener(v -> listener.onItemClick(data)); // Configura el clic
        }
    }
}