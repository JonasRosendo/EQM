package br.com.softpeach.www.eqm.br.com.softpeach.www.eqm.screens;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;

import java.util.ArrayList;
import java.util.List;

import br.com.softpeach.www.eqm.R;
import br.com.softpeach.www.eqm.helper.DatabaseHelper;

public class StatisticTotalMonthsActivity extends AppCompatActivity {

    private Spinner spinner_totalMonth;
    private BarChart barChart_totalMonths;
    private TextView txv_totalAttenddances;
    private ArrayAdapter<Integer> adapterMonth;
    private List<Integer> monthsList = new ArrayList<>();
    private DatabaseHelper db = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic_total_months);

        spinner_totalMonth = findViewById(R.id.spinner_totalmonth);
        barChart_totalMonths = findViewById(R.id.barchart_totalmonths);
        txv_totalAttenddances = findViewById(R.id.txv_totalattendancesmonth);

        monthsList = db.getAllMonths();

        adapterMonth = new ArrayAdapter<Integer>(this, android.R.layout.simple_list_item_1, monthsList);
        spinner_totalMonth.setAdapter(adapterMonth);

        //em construção...
    }
}