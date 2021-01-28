package com.testalarstudios.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.testalarstudios.R;
import com.testalarstudios.databinding.RowItemBinding;
import com.testalarstudios.model.DataModel;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.Holder> {

    private final ListAdapterListener listener;
    private final ArrayList<DataModel> list = new ArrayList<>();
    private Context context;

    public ListAdapter(ListAdapterListener listener) {
        this.listener = listener;
    }

    public void setData(List<DataModel> data) {
        list.addAll(data);
        notifyDataSetChanged();
    }

    public interface ListAdapterListener {
        void onItemClickListener(DataModel item);
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new Holder(LayoutInflater.from(context).inflate(R.layout.row_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        DataModel item = list.get(position);

        holder.vb.labelId.setText(item.getId());
        holder.vb.labelName.setText(item.getName());
        Glide.with(context).load("https://avatars.githubusercontent.com/u/" + position + 1 + "?v=4").into(holder.vb.imageAvatar);
        holder.vb.getRoot().setOnClickListener(v -> listener.onItemClickListener(item));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class Holder extends RecyclerView.ViewHolder {
        RowItemBinding vb;

        public Holder(@NonNull View itemView) {
            super(itemView);

            vb = RowItemBinding.bind(itemView);
        }
    }
}
