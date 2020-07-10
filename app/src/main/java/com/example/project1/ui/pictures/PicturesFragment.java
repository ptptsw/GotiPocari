package com.example.project1.ui.pictures;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.project1.R;


public class PicturesFragment extends Fragment {

    private PicturesViewModel picturesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        /*picturesViewModel =ViewModelProviders.of(this).get(PicturesViewModel.class);*/
        View root = inflater.inflate(R.layout.fragment_pictures, container, false);
        GridView gridView = (GridView) root.findViewById(R.id.grid_view);
        gridView.setAdapter(new picturesAdapter(getActivity()));
        /*picturesViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent i = new Intent(getActivity(), FullImageActivity.class);
                i.putExtra("id", position);
                startActivity(i);
            }
        });
        return root;
    }
}