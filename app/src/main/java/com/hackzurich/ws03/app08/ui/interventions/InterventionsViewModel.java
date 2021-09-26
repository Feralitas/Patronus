package com.hackzurich.ws03.app08.ui.interventions;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class InterventionsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public InterventionsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is interventions fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}