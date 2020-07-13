package com.example.project1.ui.phonebook;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.project1.R;

import java.util.ArrayList;

public class PhoneBookAdapter extends RecyclerView.Adapter<PhoneBookAdapter.PhoneBookViewHolder> {
    private ArrayList<JsonData> listViewItemList;

    public static class PhoneBookViewHolder extends RecyclerView.ViewHolder {
        private ImageView photo;
        private TextView name;
        private TextView number;
        private TextView email;
        private View expandableList;
        public PhoneBookViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            number = itemView.findViewById(R.id.number);
            email = itemView.findViewById(R.id.email);
            photo = itemView.findViewById(R.id.photo);
            expandableList = itemView.findViewById(R.id.expandable_list);
        }

        public void bind(JsonData item) {
            boolean expanded = item.getExpanded();

            expandableList.setVisibility(expanded ? View.VISIBLE : View.GONE);

            name.setText(item.getName());
            number.setText(item.getNumber());
            email.setText(item.getEmail());
            photo.setImageURI(item.getPhoto());
        }
    }

    public PhoneBookAdapter(ArrayList<JsonData> items) {
        this.listViewItemList = items;
    }

    @Override
    public PhoneBookAdapter.PhoneBookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_phonebook_listview, parent, false);
        return new PhoneBookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PhoneBookAdapter.PhoneBookViewHolder holder, final int position) {
        final JsonData item = listViewItemList.get(position);
        holder.bind(item);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.setExpanded(!item.getExpanded());
                notifyItemChanged(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listViewItemList.size();
    }

    public void updateItems(ArrayList<JsonData> items) {
        listViewItemList.clear();
        if (items != null)
            listViewItemList.addAll(items);
        notifyDataSetChanged();
    }
}
