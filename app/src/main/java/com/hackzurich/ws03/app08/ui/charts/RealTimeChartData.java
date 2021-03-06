package com.hackzurich.ws03.app08.ui.charts;

import com.garmin.device.realtime.RealTimeAccelerometer;
import com.garmin.device.realtime.RealTimeAscent;
import com.garmin.device.realtime.RealTimeBodyBattery;
import com.garmin.device.realtime.RealTimeCalories;
import com.garmin.device.realtime.RealTimeHeartRate;
import com.garmin.device.realtime.RealTimeHeartRateVariability;
import com.garmin.device.realtime.RealTimeIntensityMinutes;
import com.garmin.device.realtime.RealTimeRespiration;
import com.garmin.device.realtime.RealTimeResult;
import com.garmin.device.realtime.RealTimeSpo2;
import com.garmin.device.realtime.RealTimeSteps;
import com.garmin.device.realtime.RealTimeStress;

/**
 * Copyright (c) 2017 Garmin International. All Rights Reserved.
 * <p></p>
 * This software is the confidential and proprietary information of
 * Garmin International.
 * You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement
 * you entered into with Garmin International.
 * <p></p>
 * Garmin International MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE
 * SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE, OR NON-INFRINGEMENT. Garmin International SHALL NOT BE LIABLE FOR ANY DAMAGES
 * SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING
 * THIS SOFTWARE OR ITS DERIVATIVES.
 * <p></p>
 * Created by jacksoncol on 10/25/18.
 */
public class RealTimeChartData extends ChartData
{
    private RealTimeResult mResult;

    public RealTimeChartData(int dataPoint, long timestamp)
    {
        super(dataPoint, timestamp);
    }

    public RealTimeChartData(RealTimeResult result)
    {
        super();

        mResult = result;
    }

    public RealTimeAccelerometer getAccelerometer()
    {
        return mResult.getAccelerometer();
    }

    public RealTimeCalories getCalories()
    {
        return mResult.getCalories();
    }

    public RealTimeAscent getAscent()
    {
        return mResult.getAscent();
    }

    public RealTimeHeartRate getHeartRate()
    {
        return mResult.getHeartRate();
    }

    public RealTimeHeartRateVariability getHeartRateVariability()
    {
        return mResult.getHeartRateVariability();
    }

    public RealTimeIntensityMinutes getIntensityMinutes()
    {
        return mResult.getIntensityMinutes();
    }

    public RealTimeSpo2 getSpo2()
    {
        return mResult.getSpo2();
    }

    public RealTimeSteps getSteps()
    {
        return mResult.getSteps();
    }

    public RealTimeStress getStress()
    {
        return mResult.getStress();
    }

    public RealTimeBodyBattery getBodyBattery()
    {
        return mResult.getBodyBattery();
    }

    public RealTimeRespiration getRespiration()
    {
        return mResult.getRespiration();
    }
}
