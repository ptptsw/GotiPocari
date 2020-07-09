package com.example.project1.ui.randomgame;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Button;

import android.view.animation.Interpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.AnticipateOvershootInterpolator;

import java.util.Random;
import java.io.InputStream;
import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project1.R;

public class RandomGameFragment extends Fragment {
    private RandomGameViewModel randomGameViewModel;
    private ImageView imageView;
    private Button button;
    private Random rng = new Random();
    private float lastDegree;
    private String[] bottlePaths;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        randomGameViewModel =
                ViewModelProviders.of(this).get(RandomGameViewModel.class);
        View root = inflater.inflate(R.layout.fragment_randomgame, container, false);

        recyclerView = root.findViewById(R.id.rg_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        try {
            bottlePaths = getActivity().getAssets().list("bottles");
        } catch (java.io.IOException e) {
            bottlePaths = new String[]{};
        }
        mAdapter = new RGAdapter(bottlePaths);
        recyclerView.setAdapter(mAdapter);

        imageView = root.findViewById(R.id.text_randomgame);
        button = root.findViewById(R.id.spin_bottle);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimationSet animSet = new AnimationSet(true);
                animSet.setInterpolator(new AnticipateOvershootInterpolator(1f, 0.3f));
                animSet.setFillAfter(true);
                animSet.setFillEnabled(true);

                float randDegree = rng.nextInt(360);
                final RotateAnimation rotate = new RotateAnimation(
                        lastDegree,
                        lastDegree + 360 * 20 + randDegree,
                        Animation.RELATIVE_TO_SELF,
                        0.5f,
                        Animation.RELATIVE_TO_SELF,
                        0.5f
                );
                lastDegree = (lastDegree + randDegree) % 360;
                rotate.setDuration((rng.nextInt(5) + 5) * 1000);
                rotate.setFillAfter(true);
                animSet.addAnimation(rotate);
                imageView.startAnimation(animSet);
            }
        });

        return root;
    }

    private class SpinInterpolator implements Interpolator {
        public SpinInterpolator() {}
        public float getInterpolation(float t) {
            return t * (1 - t);
        }

        public float pow(float base, int exponent) {
            return exponent == 0 ? 1 : base * pow(base, exponent - 1);
        }
    }
}