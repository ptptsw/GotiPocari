package com.example.project1.ui.randomgame;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.project1.R;

import java.io.IOException;
import java.io.InputStream;

public class RGAdapter extends RecyclerView.Adapter<RGAdapter.MyViewHolder> {
    private String[] imageDataset;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public MyViewHolder(ImageView v) {
            super(v);
            imageView = v;
        }
    }

    public RGAdapter(String[] dataset) {
        imageDataset = dataset;
    }

    @Override
    public RGAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ImageView v = (ImageView) LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        InputStream inputStream;
        String path = "bottles/" + imageDataset[position];
        try {
            inputStream = holder.imageView.getContext().getAssets().open(path);
            Drawable drawable = Drawable.createFromStream(inputStream, null);
            holder.imageView.setImageDrawable(drawable);
        } catch (IOException e) {}
    }

    @Override
    public int getItemCount() {
        return imageDataset.length;
    }
}

