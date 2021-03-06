package com.hackzurich.ws03.app08.ui.charts;

import com.garmin.health.database.dtos.ZeroCrossingLog;

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
public class ChartData
{
    private ZeroCrossingLog mZcLog;
    private Object mLog;

    private long mTimestamp;
    private float mDataPoint;

    public ChartData(int dataPoint, long timestamp)
    {
        mDataPoint = dataPoint;
        mTimestamp = timestamp;
    }

    public ChartData(ZeroCrossingLog log)
    {
        mZcLog = log;
    }

    public ChartData() {}

    public ChartData(Object log)
    {
        mLog = log;
    }

    public long getTimestamp()
    {
        return mTimestamp;
    }

    public float getDataPoint()
    {
        return mDataPoint;
    }

    public ZeroCrossingLog getZcLog()
    {
        return mZcLog;
    }

    public Object getLog()
    {
        return mLog;
    }
}
