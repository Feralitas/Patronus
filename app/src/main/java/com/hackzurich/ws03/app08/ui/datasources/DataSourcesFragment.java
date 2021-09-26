package com.hackzurich.ws03.app08.ui.datasources;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.hackzurich.ws03.app08.databinding.FragmentDatasourcesBinding;
import com.hackzurich.ws03.app08.ui.PairedDevicesDialogFragment;
import com.hackzurich.ws03.app08.ui.pairing.ScanningDialogFragment;
import com.hackzurich.ws03.app08.ui.settings.SettingsFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.hackzurich.ws03.app08.databinding.FragmentDatasourcesBinding;

public class DataSourcesFragment extends Fragment {

    private DataSourcesViewModel dataSourcesViewModel;
    private FragmentDatasourcesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dataSourcesViewModel =
                new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(DataSourcesViewModel.class);

        binding = FragmentDatasourcesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        /*
        final TextView textView = binding.textDatasources;
        dataSourcesViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        */

        final ListView listview = (ListView) binding.listviewDataSources;
        String[] values = new String[] { "Fitness Tracker", "Blood Pressure", "others"};


        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < values.length; ++i) {
            list.add(values[i]);
        }
        final StableArrayAdapter adapter = new StableArrayAdapter(getContext(),
              android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);

       // MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(getContext(), values);
        //listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);

                if(item.startsWith("Fitness")){
                    //SettingsFragment
                    Fragment someFragment = new PairedDevicesDialogFragment();
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
                                adapter.notifyDataSetChanged();
                                view.setAlpha(1);


                            }
                        });
            }

        });

        return root;
    }

    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}