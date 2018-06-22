package br.com.softpeach.www.eqm.br.com.softpeach.www.eqm.screens;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.softpeach.www.eqm.R;
import br.com.softpeach.www.eqm.adapter.MeetingsAdapter;
import br.com.softpeach.www.eqm.helper.DatabaseHelper;
import br.com.softpeach.www.eqm.model.Meetings;

public class RegisteredMeetingsActivity extends AppCompatActivity {

    private ListView lv_meetings;
    private ArrayList<Meetings> meetingsList;
    private MeetingsAdapter adapter;
    private DatabaseHelper db = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered_meetings);

        getSupportActionBar().setSubtitle("Meetings");
        //configura componentes
        lv_meetings = findViewById(R.id.lv_meetings);
        //pega dados de reuniões do bd
        meetingsList = (ArrayList<Meetings>) db.getAllMeetings();
        //coloca esses dados no custom adapter de reuniões
        adapter = new MeetingsAdapter(this, meetingsList);
        //apresenta dados do adapter na lista
        lv_meetings.setAdapter(adapter);
        // ao dar um longo clique
        lv_meetings.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, final long id) {

                //cria um pop-up de confirmação
                final AlertDialog.Builder builder = new AlertDialog.Builder(RegisteredMeetingsActivity.this);
                builder.setCancelable(false);
                builder.setTitle("Warning..");
                builder.setMessage("Are you sure about deleting the meeting at " +
                        adapter.getItem(position).getDay() + "/" + adapter.getItem(position).getMonth() + "/" + adapter.getItem(position).getYear());
                builder.setIcon(android.R.drawable.ic_dialog_alert);
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        long selectedMeeting = adapter.getItem(position).getId();
                        try {
                            db.deleteMeeting(selectedMeeting);

                            Toast.makeText(getApplicationContext(), "Deleted successfully!", Toast.LENGTH_SHORT).show();

                            db.updateTotalStatistics(db.getCurrentYear());
                            Toast.makeText(getApplicationContext(), "Statistics " + db.getCurrentYear() + " updated", Toast.LENGTH_SHORT).show();
                        }catch(SQLiteException ex)
                        {
                            ex.printStackTrace();
                        }
                        openAddMeetings();

                    }
                });

                builder.show();
                return true;
            }
        });

    }

    public void openAddMeetings()
    {
        Intent intent = new Intent(getApplicationContext(), AddMeetingActivity.class);
        startActivity(intent);
    }
}
