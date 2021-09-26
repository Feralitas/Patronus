package com.hackzurich.ws03.app08.ui.settings;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.annotation.NonNull;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.garmin.health.Device;
import com.garmin.health.DeviceManager;
import com.hackzurich.ws03.app08.R;
import com.hackzurich.ws03.app08.databinding.FragmentSettingsBinding;
import com.hackzurich.ws03.app08.ui.PairedDevicesDialogFragment;
import com.hackzurich.ws03.app08.ui.adapters.SettingsAdapter;

public class SettingsFragment extends DialogFragment {

    private SettingsViewModel settingsViewModel;
    private FragmentSettingsBinding binding;

    private ImageView mDeviceImage;
    private View mRootView;
    private ListView mSettingsList;
    private Button mForgetButton;
    private SettingsAdapter mSettingsAdapter;

    private Device mDevice;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        settingsViewModel =
                new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(SettingsViewModel.class);

        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        Bundle bundle = getArguments();

        this.setRetainInstance(true);

        if(mDevice == null)
        {
           try {
               mDevice = DeviceManager.getDeviceManager().getDevice(getArguments().getString(PairedDevicesDialogFragment.DEVICE_ARG));
           }catch (Exception e){
               System.out.println(e.getMessage());
           }
        }

        mRootView = inflater.inflate(R.layout.fragment_device_settings, container, false);

        mDeviceImage = mRootView.findViewById(R.id.device_image);

        mSettingsList = mRootView.findViewById(R.id.settings_list);
        mSettingsAdapter = new SettingsAdapter(mDevice, getContext());
        mSettingsList.setAdapter(mSettingsAdapter);

        mForgetButton = mRootView.findViewById(R.id.forget_button);

        mForgetButton.setOnClickListener(forgetListener);

        if(mDevice != null)
        mDeviceImage.setImageResource(mDevice.image());




        return mRootView;
    }

    private View.OnClickListener forgetListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

            builder.setMessage(String.format("Forget Device: %s?", mDevice.friendlyName()));

            builder.setPositiveButton(R.string.y, (dialogInterface, i) -> {
                DeviceManager.getDeviceManager().forget(mDevice.address());

                getFragmentManager().popBackStack();
                getFragmentManager().popBackStack();
            });

            builder.setNegativeButton("No", (dialogInterface, i) -> {/* Do nothing...*/});

            builder.create().show();
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}