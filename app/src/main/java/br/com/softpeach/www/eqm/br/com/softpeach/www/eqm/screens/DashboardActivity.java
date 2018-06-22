package br.com.softpeach.www.eqm.br.com.softpeach.www.eqm.screens;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import br.com.softpeach.www.eqm.R;

public class DashboardActivity extends AppCompatActivity {

    private ImageButton ibtn_add, ibtn_search, ibtn_meetings, ibtn_attendance, ibtn_statistics;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        //configura imagebuttons
        ibtn_add = findViewById(R.id.ibtn_add);
        ibtn_search = findViewById(R.id.ibtn_search);
        ibtn_meetings = findViewById(R.id.ibtn_meetings);
        ibtn_attendance = findViewById(R.id.ibtn_attendance);
        ibtn_statistics = findViewById(R.id.ibtn_statistics);

        //chama cada tela através de seu respectivo botão
        ibtn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddElderActivity();
            }
        });

        ibtn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSearchActivity();
            }
        });

        ibtn_meetings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddMeetingActivity();
            }
        });

        ibtn_attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddAttendanceActivity();
            }
        });

        ibtn_statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStatisticActivity();
            }
        });
    }

    public void openAddElderActivity()
    {
        Intent intent = new Intent(this, AddElderActivity.class);
        startActivity(intent);
    }

    public void openSearchActivity()
    {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    public void openAddMeetingActivity()
    {
        Intent intent = new Intent(this, AddMeetingActivity.class);
        startActivity(intent);
    }

    public void openAddAttendanceActivity()
    {
        Intent intent = new Intent(this, AddAttendanceActivity.class);
        startActivity(intent);
    }

    public void openStatisticActivity()
    {
        Intent intent = new Intent(this, StatisticsActivity.class);
        startActivity(intent);
    }
}
