package com.hackzurich.ws03.app08.ui.data;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.garmin.device.realtime.RealTimeDataType;
import com.garmin.device.realtime.RealTimeResult;
import com.garmin.device.realtime.listeners.RealTimeDataListener;
import com.garmin.health.Device;
import com.garmin.health.DeviceManager;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.google.android.material.snackbar.Snackbar;
import com.hackzurich.ws03.app08.Constants;
import com.hackzurich.ws03.app08.DataDisplayActivity;

import com.hackzurich.ws03.app08.R;
import com.hackzurich.ws03.app08.databinding.FragmentDataBinding;
import com.hackzurich.ws03.app08.ui.PairedDevicesDialogFragment;
import com.hackzurich.ws03.app08.ui.charts.AccelerometerChart;
import com.hackzurich.ws03.app08.ui.charts.BodyBatteryChart;
import com.hackzurich.ws03.app08.ui.charts.CaloriesChart;
import com.hackzurich.ws03.app08.ui.charts.FloorsChart;
import com.hackzurich.ws03.app08.ui.charts.GHLineChart;
import com.hackzurich.ws03.app08.ui.charts.HeartRateChart;
import com.hackzurich.ws03.app08.ui.charts.HeartRateVariabilityChart;
import com.hackzurich.ws03.app08.ui.charts.IntensityMinutesChart;
import com.hackzurich.ws03.app08.ui.charts.RealTimeChartData;
import com.hackzurich.ws03.app08.ui.charts.RespirationChart;
import com.hackzurich.ws03.app08.ui.charts.Spo2Chart;
import com.hackzurich.ws03.app08.ui.charts.StepsChart;
import com.hackzurich.ws03.app08.ui.charts.StressLevelChart;
import com.hackzurich.ws03.app08.ui.realtime.RealTimeDataHandler;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class DataFragment extends Fragment implements RealTimeDataListener {

    private DataViewModel dataViewModel;
    private FragmentDataBinding binding;

    private static final int CHART_HEIGHT = 375;

    private static final String TAG = DataDisplayActivity.class.getSimpleName();
    public static final String APP_START_TIME = "appStartTime";

    private String mAddress;
    private int originalChartHeight = CHART_HEIGHT;

    private HeartRateChart hrChart;
    private HeartRateVariabilityChart hrvChart;
    private StepsChart stepsChart;
    private CaloriesChart caloriesChart;
    private FloorsChart floorsChart;
    private IntensityMinutesChart intensityMinutesChart;
    private StressLevelChart stressChart;
    private AccelerometerChart accelerometerChart;
    private Spo2Chart spo2Chart;
    private BodyBatteryChart bodyBatteryChart;
    private RespirationChart respirationChart;

    private long startTime;
    private List<GHLineChart> charts;
    private Set<RealTimeDataType> mSupportedTypes;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
      //  dataViewModel =
           //     new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(DataViewModel.class);

        binding = FragmentDataBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //final TextView textView = binding.textData;
      //  dataViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
       //     @Override
         //   public void onChanged(@Nullable String s) {



          //      textView.setText(s);
          //  }
        //});

        //initialize TextView on start page
        SharedPreferences preferences = this.getActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE);

        mAddress = preferences.getString(Constants.DEVICE_ADDRESS_EXTRA,"ED:64:D8:75:93:");


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

        TextView patronus = binding.patronusStrength;
        if(patronus!=null)
            patronus.setText(preferences.getLong(Constants.PREF_CONSTANT_PATRONUS,0)+"");


        Intent intent = getActivity().getIntent();
        DeviceManager deviceManager = DeviceManager.getDeviceManager();
        Device device = deviceManager.getDevice(mAddress);
        mSupportedTypes = device.supportedRealTimeTypes();


        // Initialize charts
        Point size = findDisplaySize();

        hrChart = binding.hrChart;
        hrvChart = binding.hrvChart;
        stepsChart = binding.stepsChart;
        caloriesChart = binding.caloriesChart;
        floorsChart = binding.floorChart;
        intensityMinutesChart = binding.intensityMinutesChart;
        stressChart = binding.stressChart;
        accelerometerChart = binding.accelerometerChart;
        spo2Chart = binding.spo2Chart;
        bodyBatteryChart = binding.bodyBatteryChart;
        respirationChart = binding.respirationChart;


        initRealTimeData();

        if(savedInstanceState != null)
        {
            startTime = savedInstanceState.getLong(APP_START_TIME);
        }

        startTime = (startTime == 0) ? System.currentTimeMillis() : startTime;
        charts = Arrays.asList(hrChart, hrvChart, stressChart, stepsChart, caloriesChart, intensityMinutesChart, floorsChart, accelerometerChart, spo2Chart, bodyBatteryChart, respirationChart);

        // create GH charts
        for(GHLineChart chart : charts)
        {
            if(chart != null )
            {
                chart.createChart(savedInstanceState, startTime);
                // resizeChart(chart, size.x, CHART_HEIGHT);
                // apply the gesture listener
           //     chart.setOnChartGestureListener(new DataDisplayActivity.HealthChartGestureListener(chart));
            }
        }

        // show the message
        showSnackbarMessage(R.string.all_charts_refreshed);

        return root;
    }

    @Override
    public void onResume()
    {
        super.onResume();

        DeviceManager.getDeviceManager().enableRealTimeData(mAddress, mSupportedTypes);
        DeviceManager.getDeviceManager().addRealTimeDataListener(this, mSupportedTypes);
    }

    @Override
    public void onStop()
    {
        super.onStop();



        //Activity isn't running, don't need to listen for data
        DeviceManager.getDeviceManager().removeRealTimeDataListener(this, mSupportedTypes);
        DeviceManager.getDeviceManager().disableRealTimeData(mAddress, mSupportedTypes);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void initRealTimeData()
    {
        //Data may have been received when page wasn't in the foreground
        HashMap<RealTimeDataType, RealTimeResult> latestData = RealTimeDataHandler.getInstance().getLatestData(mAddress);
        if(latestData != null)
        {
            for (RealTimeDataType type : latestData.keySet())
            {
                updateData(type, latestData.get(type));
            }
        }

        for(RealTimeDataType queryType : RealTimeDataType.values())
        {
            if(mSupportedTypes == null || !mSupportedTypes.contains(queryType))
            {
                switch(queryType)
                {
                    case STEPS:
                        disableChartCard(R.id.steps_chart_card);



                        break;
                    case HEART_RATE_VARIABILITY:
                        disableChartCard(R.id.hrv_chart_card);
                        break;
                    case CALORIES:
                        disableChartCard(R.id.calories_chart_card);
                        break;
                    case ASCENT:
                        disableChartCard(R.id.floor_chart_card);
                        break;
                    case INTENSITY_MINUTES:
                        disableChartCard(R.id.intensity_minutes_chart_card);
                        break;
                    case HEART_RATE:
                        disableChartCard(R.id.hr_chart_card);
                        break;
                    case STRESS:
                        disableChartCard(R.id.stress_chart_card);
                        break;
                    case ACCELEROMETER:
                        disableChartCard(R.id.accelerometer_chart_card);
                        break;
                    case SPO2:
                        disableChartCard(R.id.spo2_chart_card);
                        break;
                    case RESPIRATION:
                        disableChartCard(R.id.respiration_chart_card);
                        break;
                    case BODY_BATTERY:
                        disableChartCard(R.id.body_battery_chart_card);
                        break;
                }
            }
        }
    }


    /**
     * Toggles chart card based on the menu selection
     * @param item
     * @param chartCardId
     */
    private void toggleChartCard(MenuItem item, int chartCardId ){
        boolean checked = item.isChecked();
        item.setChecked(checked ? false : true);
      //  binding.chartCardI.setVisibility(checked ? View.GONE : View.VISIBLE);
    }

    private void disableChartCard(int chartCardId){
      //  findViewById(chartCardId).setVisibility(View.GONE);
    }


    @Override
    @MainThread
    public void onDataUpdate(@NonNull String macAddress, @NonNull final RealTimeDataType dataType, @NonNull final RealTimeResult result)
    {
        if(!macAddress.equals(mAddress))
        {
            //Real time data came from different device
            return;
        }

        //Use same logging as single instance real time listener for sample
        RealTimeDataHandler.logRealTimeData(TAG, macAddress, dataType, result);

        updateData(dataType, result);
    }

    private void updateData(final RealTimeDataType dataType, final RealTimeResult result)
    {
        if (dataType == null || result == null)
        {
            return;
        }
        SharedPreferences preferences;
        SharedPreferences.Editor editor;
        try{

         preferences = getActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE);

        editor = preferences.edit();

        //Update views with new data
        switch (dataType)
        {
            case HEART_RATE:
                hrChart.updateChart(new RealTimeChartData(result), System.currentTimeMillis());
                break;
            case HEART_RATE_VARIABILITY:
                hrvChart.updateChart(new RealTimeChartData(result), System.currentTimeMillis());
                break;
            case STRESS:
                stressChart.updateChart(new RealTimeChartData(result), System.currentTimeMillis());
                break;
            case STEPS:
                stepsChart.updateChart(new RealTimeChartData(result), System.currentTimeMillis());


                TextView patronus = binding.patronusStrength;


                editor.putLong(Constants.PREF_CONSTANT_PATRONUS,(Math.round(preferences.getLong(Constants.PREF_CONSTANT_PATRONUS,0)+result.getSteps().getCurrentStepCount()*0.5)));


                editor.putLong(Constants.PREF_CONSTANT_PATRONUS_TODAY,(Math.round(preferences.getLong(Constants.PREF_CONSTANT_PATRONUS_TODAY,0)+result.getSteps().getCurrentStepCount()*0.5)));

                editor.putLong(Constants.PREF_CONSTANT_PATRONUS_STEPS,(Math.round(preferences.getLong(Constants.PREF_CONSTANT_PATRONUS_STEPS,0)+result.getSteps().getCurrentStepCount())));

                editor.putLong(Constants.PREF_CONSTANT_PATRONUS_STEPS_POINTS,(Math.round(preferences.getLong(Constants.PREF_CONSTANT_PATRONUS_STEPS_POINTS,0)+result.getSteps().getCurrentStepCount()/20)));

                editor.putLong(Constants.PREF_CONSTANT_PATRONUS_EXPLORATION_POINTS,(Math.round(preferences.getLong(Constants.PREF_CONSTANT_PATRONUS_EXPLORATION_POINTS,0)+result.getSteps().getCurrentStepCount()/5)));




                editor.commit();

                if(patronus!=null)
                    patronus.setText(preferences.getLong(Constants.PREF_CONSTANT_PATRONUS,0)+"");



                break;
            case CALORIES:
                caloriesChart.updateChart(new RealTimeChartData(result), System.currentTimeMillis());
                break;
            case ASCENT:
                floorsChart.updateChart(new RealTimeChartData(result), System.currentTimeMillis());
                break;
            case INTENSITY_MINUTES:
                intensityMinutesChart.updateChart(new RealTimeChartData(result), System.currentTimeMillis());
                break;
            case ACCELEROMETER:
                accelerometerChart.updateChart(new RealTimeChartData(result), System.currentTimeMillis());
                break;
            case SPO2:
                spo2Chart.updateChart(new RealTimeChartData(result), System.currentTimeMillis());
                break;
            case BODY_BATTERY:
                bodyBatteryChart.updateChart(new RealTimeChartData(result), System.currentTimeMillis());
                break;
            case RESPIRATION:
                respirationChart.updateChart(new RealTimeChartData(result), System.currentTimeMillis());


                editor.putLong(Constants.PREF_CONSTANT_PATRONUS_BREATHING_DETECTED,(Math.round(preferences.getLong(Constants.PREF_CONSTANT_PATRONUS_BREATHING_DETECTED,0)+result.getRespiration().getRespirationRate()*0.5)));

                editor.putLong(Constants.PREF_CONSTANT_PATRONUS_BREATHING_POINTS,(Math.round(preferences.getLong(Constants.PREF_CONSTANT_PATRONUS_BREATHING_POINTS,0)+result.getRespiration().getRespirationRate()/20)));




                editor.commit();

                break;
            default:
                break;
        }

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Shows the provided message as a snackbar text
     * @param resId
     */
    private  void showSnackbarMessage(@StringRes int resId){
       Snackbar refreshMessage = Snackbar.make(binding.hrChart, resId, Snackbar.LENGTH_LONG);
        refreshMessage.show();
    }

    /**
     * Resizes the chart
     * @param chart
     * @param width
     * @param height
     */
    private void resizeChart(LineChart chart, int width, int height)
    {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) chart.getLayoutParams();
        layoutParams.width = width;
        layoutParams.height = height;
        chart.setLayoutParams(layoutParams);
    }

    /**
     * finds the display size of the device
     * @return
     */
    private Point findDisplaySize(){

        Display display = getActivity().getWindowManager().getDefaultDisplay();
      Point size = new Point();
        display.getSize(size);

        return size;
    }
    /**
     * Listens to the chart gestures
     */
    public class HealthChartGestureListener implements OnChartGestureListener
    {
        private LineChart chart;

        public HealthChartGestureListener(LineChart chart1)
        {
            chart = chart1;
        }

        @Override
        public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {}

        @Override
        public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {}

        @Override
        public void onChartLongPressed(MotionEvent me) {}

        @Override
        public void onChartDoubleTapped(MotionEvent me)
        {
            // finds the display size
            Display display = getActivity().getWindowManager().getDefaultDisplay();
            Point size = new Point();
           display.getSize(size);

            //Check the chart's size and then resize
            int maxHeight = 3*size.y/4;

            if(chart.getHeight() == maxHeight)
            {
                //already full screen, so reduce the size
        //        this.resizeChart(chart, chart.getWidth(), originalChartHeight);
            }
            else
            {
                // make it full screen and allow pinch zoom
             //   originalChartHeight = chart.getHeight();
              //  DataDisplayActivity.this.resizeChart(chart, chart.getWidth(), maxHeight);
            }
        }

        @Override
        public void onChartSingleTapped(MotionEvent me) {}

        @Override
        public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {}

        @Override
        public void onChartScale(MotionEvent me, float scaleX, float scaleY) {}

        @Override
        public void onChartTranslate(MotionEvent me, float dX, float dY) {}
    }

}