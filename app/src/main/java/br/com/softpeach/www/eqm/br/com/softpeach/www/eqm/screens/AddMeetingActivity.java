package br.com.softpeach.www.eqm.br.com.softpeach.www.eqm.screens;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import br.com.softpeach.www.eqm.R;
import br.com.softpeach.www.eqm.helper.DatabaseHelper;

public class AddMeetingActivity extends AppCompatActivity {

    private DatePicker datePicker;
    private Button btn_saveDate, btn_cancelDate, btn_registered;
    private int day, month, year;
    private DatabaseHelper db = new DatabaseHelper(this);
    private ConstraintLayout meetingsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meeting);
        //configura componentes
        datePicker = findViewById(R.id.datePicker);
        btn_saveDate = findViewById(R.id.btn_saveDate);
        btn_cancelDate = findViewById(R.id.btn_cancelDate);
        btn_registered = findViewById(R.id.btn_registeredMeetings);
        meetingsLayout = findViewById(R.id.meetingLayout);
        //abre a tela de reuniões registradas
        btn_registered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegisteredMeetingsActivity();
            }
        });
        //salva reuniões no bd
        btn_saveDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = new DatabaseHelper(getApplicationContext());
                //pega a data com datePicker
                day = datePicker.getDayOfMonth();
                month = datePicker.getMonth() + 1; //acrescenta +1 porque sempre vem com "um mês abaixo"
                year = datePicker.getYear();

                //cria pop-up de confirmação
                AlertDialog.Builder builder = new AlertDialog.Builder(AddMeetingActivity.this);
                builder.setCancelable(false);
                builder.setIcon(android.R.drawable.ic_dialog_alert);
                builder.setTitle("Alert!!");
                builder.setMessage("Do you really want to save this date " + day + "-" + month + "-" + year + " ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //tenta gravar no db
                        try
                        {
                            db.addMeeting(day, month, year, db.currentTime());
                            Toast.makeText(getApplicationContext(), "Meeting saved with success", Toast.LENGTH_SHORT).show();
                            //atualiza estatisticas
                            db.updateTotalStatistics(db.getCurrentYear());
                            Toast.makeText(getApplicationContext(), "Statistics " + db.getCurrentYear() +" updated", Toast.LENGTH_SHORT).show();
                        }catch (SQLiteException ex)
                        {
                            ex.printStackTrace();
                        }
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Snackbar snackbar = Snackbar.make(meetingsLayout, "Pick Another date", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                });

                builder.show();
            }
        });

        //pop-up de confirmação para sair da tela
        btn_cancelDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(AddMeetingActivity.this);
                builder.setCancelable(false);
                builder.setIcon(android.R.drawable.ic_dialog_alert);
                builder.setTitle("Alert!!");
                builder.setMessage("Do you want to leave ?");
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Snackbar snackbar = Snackbar.make(meetingsLayout, "Pick a date", Snackbar.LENGTH_SHORT);
                        snackbar.show();
                    }
                });

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });



                builder.show();
            }
        });
    }

    public void openRegisteredMeetingsActivity()
    {
        Intent intent = new Intent(getApplicationContext(), RegisteredMeetingsActivity.class);
        startActivity(intent);
    }
}
