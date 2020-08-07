package com.example.assigment_longmt.ui.love;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.assigment_longmt.Model.Photo;

import java.util.List;

public class LoveViewModel extends ViewModel {
    private MutableLiveData<List<Photo>> mPhoto;

    public LoveViewModel() {
        mPhoto = new MutableLiveData<>();

    }

    public LiveData<List<Photo>> getPhoto(){
        return mPhoto;
    }

}