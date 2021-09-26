package com.hackzurich.ws03.app08.ui.datasources;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DataSourcesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public DataSourcesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is data sources fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}