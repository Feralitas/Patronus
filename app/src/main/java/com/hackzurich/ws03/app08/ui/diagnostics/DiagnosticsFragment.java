package com.hackzurich.ws03.app08.ui.diagnostics;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.hackzurich.ws03.app08.R;
import com.hackzurich.ws03.app08.databinding.FragmentDiagnosticsBinding;
import com.hackzurich.ws03.app08.ui.CustomAdapter;
import com.hackzurich.ws03.app08.ui.interventions.InterventionsFragment;
import com.hackzurich.ws03.app08.ui.settings.SettingsFragment;

public class DiagnosticsFragment extends Fragment {

    private DiagnosticsViewModel galleryViewModel;
    private FragmentDiagnosticsBinding binding;
    ListView simpleList;
    String documents[] = {"Treatment plan", "Lab reports", "Physician's letter", "Imaging techniques", "Emergency data"};
    int flags[] = {R.drawable.treatment_icon, R.drawable.lab_report_icon, R.drawable.medical_report_icon, R.drawable.imaging_icon, R.drawable.emergency_icon};

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(DiagnosticsViewModel.class);

        binding = FragmentDiagnosticsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();




        simpleList = (ListView) binding.simpleListView;
        CustomAdapter customAdapter = new CustomAdapter(getContext(), documents, flags);
        simpleList.setAdapter(customAdapter);

        simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);

                if(item!=null && item.startsWith("Treatment")){
                    Fragment someFragment = new InterventionsFragment();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(((ViewGroup)getView().getParent()).getId(), someFragment ); // give your fragment container id in first parameter
                    transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                    transaction.commit();
                }

                view.animate().setDuration(2000).alpha(0)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                // list.remove(item);
                                customAdapter.notifyDataSetChanged();
                                view.setAlpha(1);


                            }
                        });
            }

        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }




}