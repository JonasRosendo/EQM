package br.com.softpeach.www.eqm.br.com.softpeach.www.eqm.screens;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import br.com.softpeach.www.eqm.R;

//esta activity mostra as opções de estatisticas
public class StatisticsActivity extends AppCompatActivity {

    ImageView img_totalYear, img_individual_year, img_totalMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        getSupportActionBar().setSubtitle("Statistics");
        //configura componentes
        img_totalYear = findViewById(R.id.img_totalYear);
        img_individual_year = findViewById(R.id.img_individualYear);
        img_totalMonth = findViewById(R.id.img_totalMonth);

        //abre cada activity no seu respectivo ImageView(botão)
        img_totalYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StatisticTotalYearActivity.class);
                startActivity(intent);
            }
        });

        img_individual_year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StatisticsIndividualElderYearActivity.class);
                startActivity(intent);
            }
        });

        img_totalMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StatisticTotalMonthsActivity.class);
                startActivity(intent);
            }
        });

        //em construção...
    }
}
