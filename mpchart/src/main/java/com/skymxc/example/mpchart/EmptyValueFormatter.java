package com.skymxc.example.mpchart;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.BubbleEntry;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

/**
 * Create By 孟祥超
 * Date: 2019/5/16  9:37
 * Description:
 */
public class EmptyValueFormatter extends ValueFormatter {
    public EmptyValueFormatter() {
        super();
    }

    @Override
    public String getFormattedValue(float value) {
        return "";
    }

    @Override
    public String getAxisLabel(float value, AxisBase axis) {
        return super.getAxisLabel(value, axis);
    }

    @Override
    public String getBarLabel(BarEntry barEntry) {
        return super.getBarLabel(barEntry);
    }

    @Override
    public String getBarStackedLabel(float value, BarEntry stackedEntry) {
        return super.getBarStackedLabel(value, stackedEntry);
    }

    @Override
    public String getPointLabel(Entry entry) {
        return super.getPointLabel(entry);
    }

    @Override
    public String getPieLabel(float value, PieEntry pieEntry) {
        return super.getPieLabel(value, pieEntry);
    }

    @Override
    public String getRadarLabel(RadarEntry radarEntry) {
        return super.getRadarLabel(radarEntry);
    }

    @Override
    public String getBubbleLabel(BubbleEntry bubbleEntry) {
        return super.getBubbleLabel(bubbleEntry);
    }

    @Override
    public String getCandleLabel(CandleEntry candleEntry) {
        return super.getCandleLabel(candleEntry);
    }
}
