package com.example.project1.ui.pictures;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project1.R;

public class PreviewAdapter extends RecyclerView.Adapter<PreviewAdapter.ViewHolder> {

    public interface OnListItemSelectedInterface{
        void onItemSelected(View v, int position);
    }

    private LayoutInflater mInflater;
    private Integer[] mViewImages;
    private OnListItemSelectedInterface mListener;


    PreviewAdapter(Context context, Integer[] images, OnListItemSelectedInterface listner){
        this.mInflater=LayoutInflater.from(context);
        this.mViewImages=images;
        this.mListener=listner;
    }



    @NonNull
    @Override
    public PreviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.preview_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.myImageView.setImageResource(mViewImages[position]);

    }

    @Override
    public int getItemCount() {
        return mViewImages.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView myImageView;
        public ViewHolder(View itemview) {
            super(itemview);
            myImageView=itemview.findViewById(R.id.preview_item);
            itemview.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.d("Recyclerview", "position = "+ getAdapterPosition());
            int position= getAdapterPosition();
            mListener.onItemSelected(view, position);



        }


    }
}
