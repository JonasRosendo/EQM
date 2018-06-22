package br.com.softpeach.www.eqm.br.com.softpeach.www.eqm.screens;

import android.content.DialogInterface;
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
import br.com.softpeach.www.eqm.adapter.AttendanceAdapter;
import br.com.softpeach.www.eqm.helper.DatabaseHelper;
import br.com.softpeach.www.eqm.model.Attendance;

public class RegisteredAttendancesActivity extends AppCompatActivity {

    private ListView lv_attendances;
    private ArrayList<Attendance> attendanceArrayList;
    private AttendanceAdapter attendanceAdapter ;
    private DatabaseHelper db = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered_attendances);

        getSupportActionBar().setSubtitle("All Attendances");

        //configura componentes
        lv_attendances = findViewById(R.id.lv_registeredAttendances);

        //pega dados de fequência do BD.
        attendanceArrayList = (ArrayList<Attendance>) db.getAllAttendances();
        //coloca dado do bd no custom adapter de frequência
        attendanceAdapter = new AttendanceAdapter(this, attendanceArrayList);
        //apresenta dados na lista
        lv_attendances.setAdapter(attendanceAdapter);

        //ao dar um longo clique em algum item da lista
        lv_attendances.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //cria um objeto do tipo attendance e atribui os valores do objeto que foi trago do bd.
                final Attendance attendance = (Attendance) attendanceAdapter.getItem(position);
                //cria um popu de confirmação
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisteredAttendancesActivity.this);
                builder.setIcon(android.R.drawable.ic_dialog_alert);
                builder.setCancelable(false);
                builder.setTitle("Warning...");
                builder.setMessage("Do you want to delete the attendance elder id: " + attendance.getElder_id() + " from meeting id: " + attendance.getMeeting_id() + " ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //tenta deletar linha do db
                        try {
                            db.deleteAttendance(attendance.getId());
                            //confirma quem foi deletado
                            Toast.makeText(getApplicationContext(), "Elder " + attendance.getElder_id() + "Deleted with success from meeting " + attendance.getMeeting_id(), Toast.LENGTH_SHORT).show();
                            //atualiza estatisticas
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
                return true;
            }
        });
    }
}
