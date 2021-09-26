package com.hackzurich.ws03.app08.ui.interventions;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.hackzurich.ws03.app08.databinding.FragmentInterventionsBinding;

public class InterventionsFragment extends Fragment {

    private InterventionsViewModel slideshowViewModel;
    private FragmentInterventionsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(InterventionsViewModel.class);

        binding = FragmentInterventionsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        /*final TextView textView = binding.textInterventions;
        slideshowViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

        SharedPreferences preferences = this.getActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE);


        TextView patronusTotal = binding.textViewInputTotal;
        if(patronusTotal!=null)
            patronusTotal.setText(preferences.getLong(Constants.PREF_CONSTANT_PATRONUS,0)+"");

        TextView patronusAverage= binding.textViewInputAvg;
        if(patronusAverage!=null)
            patronusAverage.setText(preferences.getLong(Constants.PREF_CONSTANT_PATRONUS_AVERAGE,2500)+"");

        TextView patronusToday= binding.textViewInputToday;
        if(patronusToday!=null)
            patronusToday.setText(preferences.getLong(Constants.PREF_CONSTANT_PATRONUS_TODAY,0)+"");


        TextView patronusSteps= binding.StepsTodayInput;
        if(patronusSteps!=null)
            patronusSteps.setText(preferences.getLong(Constants.PREF_CONSTANT_PATRONUS_STEPS,0)+"");

        TextView patronusStepsPoints = binding.editInputPoints;
        if(patronusStepsPoints!=null)
            patronusStepsPoints.setText(preferences.getLong(Constants.PREF_CONSTANT_PATRONUS_STEPS_POINTS,0)+"");

        TextView patronusExplorationPoints = binding.StepsTodayInput2;
        if(patronusExplorationPoints!=null)
            patronusExplorationPoints.setText(preferences.getLong(Constants.PREF_CONSTANT_PATRONUS_EXPLORATION_POINTS,0)+"");

        TextView automaticallyBreath = binding.textView1122;
        if(automaticallyBreath!=null)
            automaticallyBreath.setText(preferences.getLong(Constants.PREF_CONSTANT_PATRONUS_BREATHING_DETECTED,0)+"");

        TextView pointsForBreathe = binding.PointsForBreathe;
        if(pointsForBreathe!=null)
            pointsForBreathe.setText(preferences.getLong(Constants.PREF_CONSTANT_PATRONUS_BREATHING_POINTS,0)+"");




        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}