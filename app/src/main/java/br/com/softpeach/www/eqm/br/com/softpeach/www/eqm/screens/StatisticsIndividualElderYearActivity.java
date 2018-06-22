package br.com.softpeach.www.eqm.br.com.softpeach.www.eqm.screens;

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
import br.com.softpeach.www.eqm.adapter.EldersAdapter;
import br.com.softpeach.www.eqm.helper.DatabaseHelper;
import br.com.softpeach.www.eqm.model.Elder;

//Esta activity apresenta os dados individuais dos elderes
public class StatisticsIndividualElderYearActivity extends AppCompatActivity {

    private Spinner spinner_elderYear, spinner_elderNameYear;
    private ArrayAdapter<String> elderYearAdapter;
    private EldersAdapter eldersAdapter;
    private ArrayList<Elder> elderList = new ArrayList<>();
    private List<String> yearList = new ArrayList<>();
    private BarChart barChart;
    private TextView txv_individual_year, txv_elderMeetingYear;
    private DatabaseHelper db = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics_individual_elder_year);
        //configura os componentes..
        spinner_elderYear = findViewById(R.id.spinner_elderYear);
        spinner_elderNameYear = findViewById(R.id.spinner_elderNameYear);
        txv_individual_year = findViewById(R.id.txv_individual_year);
        txv_elderMeetingYear = findViewById(R.id.txv_ElderMeetingYear);
        barChart = findViewById(R.id.barChart_individual_year);

        //dados do bd e atribui-os ao adapter de elderes e atribui o adapter no spinner
        elderList = (ArrayList<Elder>) db.getAllElders();
        eldersAdapter = new EldersAdapter(this, elderList);
        spinner_elderNameYear.setAdapter(eldersAdapter);
        //pega todos os anos de estatisticas no bd e atribui-os ao adapter e coloca no spinner
        yearList = db.getAllStatisticsYear();
        elderYearAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, yearList);
        spinner_elderYear.setAdapter(elderYearAdapter);

        //ao selecionar um item do spinner
        spinner_elderNameYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //pega dados do banco de dados e atribui a variáveis e estas são atribuidas ao grafico
                String year = spinner_elderYear.getSelectedItem().toString();
                Elder selected_elder = (Elder) eldersAdapter.getItem(position);
                int attendances = db.getElderYear(year, selected_elder.getId());
                int meetings = db.getTotalMeetings(year);

                txv_individual_year.setText("The number of attendances in " + year + " was " + attendances + " out of " + meetings);
                txv_elderMeetingYear.setText("The number of meetings in " + year + " was " + meetings);
                Log.i("LOG_TAG", "Total Attendances : " + attendances);

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

                barEntries1.add(new BarEntry(1, attendances));

                BarDataSet dataSet = new BarDataSet(barEntries1, "Attendances");
                dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

                barEntries2.add(new BarEntry(2, meetings));

                BarDataSet dataSet1 = new BarDataSet(barEntries2, "Meetings");
                dataSet1.setColors(ColorTemplate.COLORFUL_COLORS);

                barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
                BarData barData = new BarData(dataSet, dataSet1);
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

        spinner_elderYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //pega dados do banco de dados e atribui a variáveis e estas são atribuidas ao grafico
                String year = spinner_elderYear.getSelectedItem().toString();
                Elder selected_elder = (Elder) eldersAdapter.getItem(position);
                int attendances = db.getElderYear(year, selected_elder.getId());
                int meetings = db.getTotalMeetings(year);

                txv_individual_year.setText("The Number of attendances in " + year + " was " + attendances + " out of " + meetings);
                txv_elderMeetingYear.setText("The number of meetings in " + year + " was " + meetings);
                Log.i("LOG_TAG", "Total Attendances : " + attendances);

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

                barEntries1.add(new BarEntry(1, attendances));

                BarDataSet dataSet = new BarDataSet(barEntries1, "Attendances");
                dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

                barEntries2.add(new BarEntry(2, meetings));

                BarDataSet dataSet1 = new BarDataSet(barEntries2, "Meetings");
                dataSet1.setColors(ColorTemplate.COLORFUL_COLORS);

                barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
                BarData barData = new BarData(dataSet, dataSet1);
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
