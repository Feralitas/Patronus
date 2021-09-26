package com.hackzurich.ws03.app08.ui.diagnostics;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DiagnosticsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public DiagnosticsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is diagnostics fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}