package com.codescafe.dailytask.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codescafe.dailytask.Model.ColorSet;
import com.codescafe.dailytask.databinding.ItemColorBinding;

import java.util.List;

public class ColorsAdapter extends RecyclerView.Adapter<ColorsAdapter.ColorsHolder> {

    private final List<ColorSet> colors;
    private final AdapterView.OnItemClickListener onItemClickListener;

    public ColorsAdapter(List<ColorSet> colors, AdapterView.OnItemClickListener onItemClickListener) {
        this.colors = colors;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ColorsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ColorsHolder(ItemColorBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ColorsHolder holder, int position) {
        holder.binding.viewColor.setBackgroundResource(colors.get(position).getColorResId());
        holder.itemView.setOnClickListener(view -> {
            onItemClickListener.onItemClick(null, holder.itemView, position, 0);
            Toast.makeText(view.getContext(), ""+colors.get(position).getColorResId(), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return colors.size();
    }

    public static class ColorsHolder extends RecyclerView.ViewHolder {

        private final ItemColorBinding binding;

        public ColorsHolder(ItemColorBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
