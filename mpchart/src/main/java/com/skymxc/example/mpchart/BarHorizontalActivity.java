package com.skymxc.example.mpchart;

import android.graphics.Color;
import android.graphics.Paint;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class BarHorizontalActivity extends AppCompatActivity {
    HorizontalBarChart chart;
    protected List<String> list = new ArrayList<>();
    public static final  float groupSpace = 0.33f;
    public static final   float barSpace = 0.1f; // * DataSet
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.init(this);
        setContentView(R.layout.activity_bar_horizontal);
        chart = findViewById(R.id.chart_bar_horizontal);

        //描述
//        chart.getDescription().setEnabled(false);
        Description description = chart.getDescription();
        description.setText("描述吗?");
        description.setTextAlign(Paint.Align.CENTER);
        //是否 启用 description 会对 y 轴起点 0 的位置有影响; ?
        description.setEnabled(false);
        //网格背景(不是线)
//        chart.setDrawGridBackground(true);
        //动画
        chart.animateXY(1000, 1000);

        //左边 Y轴
        YAxis axisLeft = chart.getAxisLeft();
        //起始值
        axisLeft.setAxisMinimum(-10);
        //轴线
        axisLeft.setAxisLineColor(Color.BLUE);
        axisLeft.setAxisLineWidth(2f);
        //没有明显变化
        axisLeft.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        // 0
        axisLeft.setDrawZeroLine(true);
        //
        axisLeft.setDrawGridLines(false);
        //绘制 轴吗
        axisLeft.setDrawAxisLine(false);
        //格式化 左边Y轴上的值
        EmptyValueFormatter emptyValueFormatter = new EmptyValueFormatter();
        axisLeft.setValueFormatter(emptyValueFormatter);
        //右边 Y轴
        YAxis axisRight = chart.getAxisRight();
        axisRight.setDrawAxisLine(false);
        axisRight.setDrawTopYLabelEntry(false);
        axisRight.setDrawGridLines(false);
        axisRight.setValueFormatter(emptyValueFormatter);

        // x轴
        XAxis xAxis = chart.getXAxis();
        xAxis.setAxisMinimum(-2);
        xAxis.setDrawGridLines(false);
        xAxis.setAxisLineColor(Color.GRAY);
        xAxis.setAxisLineWidth(2f);
        xAxis.setDrawAxisLine(false);

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawLabels(true);
        //最小粒度
        xAxis.setGranularity(spaceForBar);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                    int i = (int) ((int) value/spaceForBar);
                    if (list.size()>i&&i>=0){
                        return list.get(i);
                    }
                return "";
            }
        });

        //值 在 柱子的顶端
        chart.setDrawValueAboveBar(true);
        setData();
        //让所有的 label 都显示,不折叠.
        xAxis.setLabelCount(list.size());
//        xAxis.setAxisMaximum(max+barWidth);
        //图例显示在 中间
//        xAxis.setCenterAxisLabels(true);
        //图例
        chart.getLegend().setEnabled(false);
        chart.setPinchZoom(false);
        chart.invalidate();
    }

    final float barWidth =2f;
    float spaceForBar = 10f;
    float max = 0;
    public void setData() {
        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            float val = (float) (Math.random() * 100);
            BarEntry entry = new BarEntry(i * spaceForBar, val, String.valueOf(i * spaceForBar));
            entries.add(entry);
            max =entry.getX();
            list.add(i+1 + "月");
        }

        BarDataSet set1 = new BarDataSet(entries, "每月完成进度");

        //柱子的边框
//        set1.setBarBorderColor(Color.BLUE);
//        set1.setBarBorderWidth(2f);
        //高亮透明值
//        set1.setHighLightAlpha(1);

        //给一个数组,随机柱子颜色
        set1.setColors(colors());
        //阴影颜色
//        set1.setBarShadowColor(Color.YELLOW);

        set1.setDrawValues(true);
        set1.setHighlightEnabled(false);

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        BarData data = new BarData(dataSets);
        //柱子上的 value 字体尺寸
        data.setValueTextSize(15f);
        data.setValueTextColor(Color.RED);
        //主子宽度
//        float barWidth = (1.0f - groupSpace) / dataSetArrayList.size() - barSpace;
//        data.groupBars(0, groupSpace, barSpace);
        data.setBarWidth(barWidth);
        //绘制 值 默认是 true
//        data.setDrawValues(true);
        chart.setData(data);
    }

    public static int[] colors() {
        return new int[]{Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.BLACK, Color.CYAN, Color.GRAY, Color.MAGENTA, Color.LTGRAY};
    }
}
