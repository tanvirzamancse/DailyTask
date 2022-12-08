package com.codescafe.dailytask.Adapter;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codescafe.dailytask.databinding.ItemFontsBinding;

import java.util.List;

public class FontsAdapter extends RecyclerView.Adapter<FontsAdapter.FontsHolder> {

    private final List<String> fontsList;
    private final AdapterView.OnItemClickListener onItemClickListener;

    public FontsAdapter(List<String> fontsList, AdapterView.OnItemClickListener onItemClickListener) {
        this.fontsList = fontsList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public FontsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FontsHolder(ItemFontsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FontsHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return fontsList.size();
    }

    public class FontsHolder extends RecyclerView.ViewHolder {

        private final ItemFontsBinding binding;

        public FontsHolder(@NonNull ItemFontsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(int position) {
            Typeface typeface = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/" + fontsList.get(position));
            binding.tvFontName.setTypeface(typeface);
            binding.tvFontName.setText(fontsList.get(position).substring(0, fontsList.get(position).length() - 4));
            itemView.setOnClickListener(view -> {
                onItemClickListener.onItemClick(null, itemView, position, 0);
                Toast.makeText(view.getContext(), ""+fontsList.get(position), Toast.LENGTH_SHORT).show();
            });
        }
    }
}
