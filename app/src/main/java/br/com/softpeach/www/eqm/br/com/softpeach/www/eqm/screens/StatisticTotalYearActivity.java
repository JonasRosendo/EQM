package br.com.softpeach.www.eqm.br.com.softpeach.www.eqm.screens;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import br.com.softpeach.www.eqm.R;
import br.com.softpeach.www.eqm.helper.DatabaseHelper;

//Esta activity apresenta todas as estatisticas anual
public class StatisticTotalYearActivity extends AppCompatActivity {

    private List<String> yearStringList = new ArrayList<>();
    private TextView txv_attendance, txv_total_meeting, txv_total_elders;
    private Spinner spinner_year_statistics;
    private ArrayAdapter<String> adapter;
    private BarChart barChart;
    private DatabaseHelper db = new DatabaseHelper(this);


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic_total_year);

        getSupportActionBar().setSubtitle("Total Year Statistics");

        //configura componentes
        txv_attendance = findViewById(R.id.txv_totalAttendance);
        txv_total_meeting = findViewById(R.id.txv_total_meetings);
        txv_total_elders = findViewById(R.id.txv_total_elders);
        spinner_year_statistics = findViewById(R.id.spinner_year_statistics);
        barChart = findViewById(R.id.totalYearBarChart);

        //pega estatisticas do bd
        yearStringList = db.getAllStatisticsYear();
        //atribui as estatisticas a um adapter string
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, yearStringList);
        spinner_year_statistics.setAdapter(adapter); //configura spinner com dados do adapter
        //ao selecionar um dado no spinner altera e apresenta o gráfico
        spinner_year_statistics.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //pega dados do spinner e do bd e atribui valores a cada variável
                String year = spinner_year_statistics.getSelectedItem().toString();
                int meetings = db.getTotalMeetings(year);
                int attendances = db.getTotalAttendance(year);
                int totalElders = db.getTotalElders(year);

                txv_attendance.setText("The Number of attendances in " + year + " was " + attendances + " out of " + totalElders * meetings);
                Log.i("LOG_TAG", "Total Attendances : " + attendances);
                txv_total_meeting.setText("The Number of meetings in " + year + " was " + meetings);
                Log.i("LOG_TAG", "Total Meetings : " + meetings);
                txv_total_elders.setText("The Number of Elders in " + year + " was " + totalElders);
                Log.i("LOG_TAG", "Total Elders : " + totalElders);

                barChart.setDrawBarShadow(false);
                barChart.setDrawValueAboveBar(true);
                barChart.setMaxVisibleValueCount(3000);
                barChart.setPinchZoom(false);
                barChart.setDrawGridBackground(true);
                barChart.setContentDescription("Total Year Statistics");

                ArrayList<String> labels = new ArrayList<>();
                labels.add(year);

                ArrayList<BarEntry> barEntries1 = new ArrayList<>();
                ArrayList<BarEntry> barEntries2 = new ArrayList<>();
                ArrayList<BarEntry> barEntries3 = new ArrayList<>();

                //atribui os valores do bd no gráfico através das variáveis
                barEntries1.add(new BarEntry(1, attendances));

                BarDataSet dataSet = new BarDataSet(barEntries1, "Attendances");
                dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

                barEntries2.add(new BarEntry(2, meetings));

                BarDataSet dataSet1 = new BarDataSet(barEntries2, "Meetings");
                dataSet1.setColors(ColorTemplate.COLORFUL_COLORS);

                barEntries3.add(new BarEntry(3, totalElders));

                BarDataSet dataSet2 = new BarDataSet(barEntries3, "Elders");
                dataSet2.setColors(ColorTemplate.MATERIAL_COLORS);

                barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
                BarData barData = new BarData(dataSet, dataSet1, dataSet2);
                barChart.setData(barData);

                float groupSpace = 0.04f;
                float barSpace = 0.02f;
                float barWidth = 0.46f;

                barChart.groupBars(barWidth, groupSpace, barSpace);
                Toast.makeText(getApplicationContext(), "Touch the map to update data for " + year, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }
}
