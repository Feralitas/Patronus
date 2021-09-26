package com.hackzurich.ws03.app08.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.FragmentTransaction;
import androidx.cardview.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.hackzurich.ws03.app08.Constants;
import com.hackzurich.ws03.app08.R;
import com.hackzurich.ws03.app08.databinding.FragmentHomeBinding;
import com.hackzurich.ws03.app08.ui.datasources.DataSourcesFragment;
import com.hackzurich.ws03.app08.ui.diagnostics.DiagnosticsFragment;
import com.hackzurich.ws03.app08.ui.interventions.InterventionsFragment;
import com.hackzurich.ws03.app08.ui.notifications.NotificationsFragment;
import com.hackzurich.ws03.app08.ui.profile.ProfileFragment;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {



          //      textView.setText(s);
            }
        });

        //initialize TextView on start page
        SharedPreferences preferences = this.getActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putLong(Constants.PREF_CONSTANT_PATRONUS_TODAY,0);

        editor.putLong(Constants.PREF_CONSTANT_PATRONUS_STEPS,0);

        editor.putLong(Constants.PREF_CONSTANT_PATRONUS_STEPS_POINTS,0);

        editor.putLong(Constants.PREF_CONSTANT_PATRONUS_EXPLORATION_POINTS,0);

        editor.commit(); // commit changes

        TextView name = binding.Name;

        if(name!=null)
            name.setText(preferences.getString(Constants.PREF_CONSTANT_SETTINGS_NAME,"Paula Klein"));

        //TODO: add here Patronus Score
        TextView patronus = binding.patronusStrength;
        if(patronus!=null)
            patronus.setText(preferences.getLong(Constants.PREF_CONSTANT_PATRONUS,0)+"");

        CardView cvProfile = (CardView) root.findViewById(R.id.CardViewProfile);

        cvProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("test","Profile ");
                Fragment someFragment = new ProfileFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(((ViewGroup)getView().getParent()).getId(), someFragment ); // give your fragment container id in first parameter
                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                transaction.commit();

            }
        });


        CardView cvDiagnostics = (CardView) root.findViewById(R.id.CardViewDiagnostics);

        cvDiagnostics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("test","Diagnostics");
                Fragment someFragment = new DiagnosticsFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(((ViewGroup)getView().getParent()).getId(), someFragment ); // give your fragment container id in first parameter
                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                transaction.commit();


            }
        });


        CardView cvDatasources = (CardView) root.findViewById(R.id.CardViewDatasources);

        cvDatasources.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("test","Datasources");
                Fragment someFragment = new DataSourcesFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(((ViewGroup)getView().getParent()).getId(), someFragment ); // give your fragment container id in first parameter
                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                transaction.commit();


            }
        });


        CardView cvInterventions = (CardView) root.findViewById(R.id.CardViewInterventions);

        cvInterventions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("test","Interventions");
                Fragment someFragment = new InterventionsFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(((ViewGroup)getView().getParent()).getId(), someFragment ); // give your fragment container id in first parameter
                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                transaction.commit();

            }
        });

        CardView cvNotifications = (CardView) root.findViewById(R.id.CardViewNotifications);

        cvNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("test","Notifications");
                Fragment someFragment = new NotificationsFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(((ViewGroup)getView().getParent()).getId(), someFragment ); // give your fragment container id in first parameter
                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                transaction.commit();


            }
        });

       /* CardView cvSettings = (CardView) root.findViewById(R.id.CardViewSettings);

        cvSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("test","Settings");
                Fragment someFragment = new SettingsFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(((ViewGroup)getView().getParent()).getId(), someFragment ); // give your fragment container id in first parameter
                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                transaction.commit();


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