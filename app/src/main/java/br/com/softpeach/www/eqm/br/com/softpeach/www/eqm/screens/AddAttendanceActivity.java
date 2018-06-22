package br.com.softpeach.www.eqm.br.com.softpeach.www.eqm.screens;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.softpeach.www.eqm.R;
import br.com.softpeach.www.eqm.adapter.EldersAdapter;
import br.com.softpeach.www.eqm.adapter.MeetingsAdapter;
import br.com.softpeach.www.eqm.helper.DatabaseHelper;
import br.com.softpeach.www.eqm.model.Elder;
import br.com.softpeach.www.eqm.model.Meetings;

public class AddAttendanceActivity extends AppCompatActivity {

    Spinner spinner_elders, spinner_meetings;
    ConstraintLayout layoutAddAttendance;
    Button btn_attendance_save, btn_attendance_cancel, btn_see_attendance;
    EldersAdapter eldersAdapter;
    MeetingsAdapter meetingsAdapter;
    ArrayList<Elder> elderArrayList;
    ArrayList<Meetings> meetingsArrayList;
    DatabaseHelper db = new DatabaseHelper(this);
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_attendance);

        getSupportActionBar().setSubtitle("Add Attendance");

        //configura componentes
        layoutAddAttendance = findViewById(R.id.LayoutAddAttendance);
        btn_attendance_save = findViewById(R.id.btn_attendance_save);
        btn_attendance_cancel = findViewById(R.id.btn_attendance_cancel);
        btn_see_attendance = findViewById(R.id.btn_see_attendances);
        spinner_elders = findViewById(R.id.spinner_elders);
        spinner_meetings = findViewById(R.id.spinner_meetings);
        //recupera os dados do Banco de Dados
        elderArrayList = (ArrayList<Elder>) db.getAllElders();
        meetingsArrayList = (ArrayList<Meetings>) db.getAllMeetings();

        //configura spinners com custom adapters
        eldersAdapter = new EldersAdapter(this, elderArrayList);
        spinner_elders.setAdapter(eldersAdapter);

        meetingsAdapter = new MeetingsAdapter(this, meetingsArrayList);
        spinner_meetings.setAdapter(meetingsAdapter);

        //salvar no bd
        btn_attendance_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pega as posições escolhidas nos spinners
                final Elder selected_elder = (Elder) eldersAdapter.getItem(spinner_elders.getSelectedItemPosition());
                final Meetings selected_meeting = (Meetings) meetingsAdapter.getItem(spinner_meetings.getSelectedItemPosition());
                //cria um Pop-up para confirmar a alteração
                AlertDialog.Builder builder = new AlertDialog.Builder(AddAttendanceActivity.this);
                builder.setIcon(android.R.drawable.ic_dialog_alert);
                builder.setCancelable(false);
                builder.setTitle("Warning...");
                builder.setMessage("Are you sure about adding Elder " + selected_elder.getFirst_name() + " " + selected_elder.getMiddle_name() + " "
                        + selected_elder.getLast_name() + " \n to the meetting at " + selected_meeting.getDay() + "/"
                        + selected_meeting.getMonth() + "/" + selected_meeting.getYear() + " ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //tenta gravar no bd
                        try {
                            db.addAtendance(selected_elder.getId(), selected_meeting.getId());
                            Snackbar snackbar = Snackbar.make(layoutAddAttendance, "Saved With Success", Snackbar.LENGTH_SHORT);
                            snackbar.show();
                            //atualiza a as tabelas de estatisticas.
                            long result = db.updateStatisticsElders(db.getCurrentYear(), selected_elder.getId());
                            Toast.makeText(getApplicationContext(), "Elder " + selected_elder.getFirst_name() + " " + selected_elder.getMiddle_name()  + " "
                                    + selected_elder.getLast_name() + " statistics " + db.getCurrentYear() + " updated", Toast.LENGTH_SHORT).show();
                            Log.i("LOG_TAG", "Attendance id " + result + " updated");

                            db.updateTotalStatistics(db.getCurrentYear());
                            Toast.makeText(getApplicationContext(), "Statistics " + db.getCurrentYear() +" updated", Toast.LENGTH_SHORT).show();
                        } catch (SQLiteException ex) {
                            ex.printStackTrace();
                        }
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.show();
            }
        });

        //cancela alterações na tela com um pop-up de confirmação
        btn_attendance_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddAttendanceActivity.this);
                builder.setIcon(android.R.drawable.ic_dialog_alert);
                builder.setCancelable(false);
                builder.setTitle("Warning...");
                builder.setMessage("Do you want to exit?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.show();
            }
        });

        //chama tela de frequências
        btn_see_attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), RegisteredAttendancesActivity.class);
                startActivity(intent);

            }
        });

    }
}
