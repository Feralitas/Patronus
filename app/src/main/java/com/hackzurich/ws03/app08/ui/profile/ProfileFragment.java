package com.hackzurich.ws03.app08.ui.profile;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.hackzurich.ws03.app08.Constants;
import com.hackzurich.ws03.app08.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {

    private ProfileViewModel galleryViewModel;
    private FragmentProfileBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(ProfileViewModel.class);


        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

       // final TextView textView = binding.textProfile;
        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
               // textView.setText(s);
            }
        });


        //Data Management
        SharedPreferences preferences = this.getActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE);

        //Name preferences
        EditText name = binding.editTextName;
        if(name!=null)
            name.setText(preferences.getString(Constants.PREF_CONSTANT_SETTINGS_NAME,"Paula Klein"));

        name.setText(preferences.getString(Constants.PREF_CONSTANT_SETTINGS_NAME,"Paula Klein"));
        name.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length() != 0){

                    //store and persist changes in preferences
                    //SharedPreferences preferences = this.getSharedPreferences("prefs", Context.MODE_PRIVATE);

                    SharedPreferences.Editor editor = preferences.edit();

                    editor.putString(Constants.PREF_CONSTANT_SETTINGS_NAME,s.toString());

                    editor.commit(); // commit changes

                }
            }
        });


/*
        //patientEditText preferences
        patientTextView = (TextView) this.findViewById(R.id.settings_patient);
        patientTextView.setText(pref.getString(Constants.PREF_CONSTANT_SETTINGS_PATIENT_ID,""));

        patientTextView.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length() != 0){

                    //store and persist changes in preferences
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("corona_pref", 0); // 0 - for private mode
                    SharedPreferences.Editor editor = pref.edit();

                    editor.putString(Constants.PREF_CONSTANT_SETTINGS_PATIENT_ID,s.toString());

                    editor.commit(); // commit changes
                }
            }
        });

        //locationTextView preferences
        locationTextView = (TextView) this.findViewById(R.id.settings_location);
        locationTextView.setText(pref.getString(Constants.PREF_CONSTANT_SETTINGS_LOCATION_ID,""));

        locationTextView.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length() != 0){

                    //store and persist changes in preferences
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("corona_pref", 0); // 0 - for private mode
                    SharedPreferences.Editor editor = pref.edit();

                    editor.putString(Constants.PREF_CONSTANT_SETTINGS_LOCATION_ID,s.toString());

                    editor.commit(); // commit changes
                }
            }
        });


*/

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



}