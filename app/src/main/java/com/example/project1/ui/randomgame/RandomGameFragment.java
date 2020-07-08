package com.example.project1.ui.randomgame;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.project1.R;

public class RandomGameFragment extends Fragment {

    private RandomGameViewModel randomGameViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        randomGameViewModel =
                ViewModelProviders.of(this).get(RandomGameViewModel.class);
        View root = inflater.inflate(R.layout.fragment_randomgame, container, false);
        final ImageView imageView = root.findViewById(R.id.text_randomgame);
//        final TextView textView = root.findViewById(R.id.text_randomgame);
        return root;
    }
}