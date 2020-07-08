package com.example.project1.ui.randomgame;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RandomGameViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public RandomGameViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Random Game fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}