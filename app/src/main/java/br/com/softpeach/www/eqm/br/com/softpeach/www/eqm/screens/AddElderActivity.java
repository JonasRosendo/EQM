package br.com.softpeach.www.eqm.br.com.softpeach.www.eqm.screens;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.softpeach.www.eqm.R;
import br.com.softpeach.www.eqm.helper.DatabaseHelper;
import br.com.softpeach.www.eqm.model.Elder;

public class AddElderActivity extends AppCompatActivity {

    private EditText edt_firstname, edt_middlename, edt_lastname;
    private Button btn_submitElder, btn_cancelElder, btn_resetElder;
    private DatabaseHelper db = new DatabaseHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_elder);

        getSupportActionBar().setSubtitle("Add New Elder");
        //configura componentes
        edt_firstname = findViewById(R.id.edt_firstname);
        edt_middlename = findViewById(R.id.edt_middlename);
        edt_lastname = findViewById(R.id.edt_lastname);
        btn_submitElder = findViewById(R.id.btn_submitElder);
        btn_cancelElder = findViewById(R.id.btn_cancelElder);
        btn_resetElder = findViewById(R.id.btn_resetElder);
        //salva no bd
        btn_submitElder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //pega dados nos editTexts
                String firstname = edt_firstname.getText().toString().trim();
                String middlename = edt_middlename.getText().toString().trim();
                String lastname = edt_lastname.getText().toString().trim();

                //verifica se estão corretamente preenchidos
                if(firstname.length() > 0 && middlename.length() > 0 && lastname.length() > 0)
                {
                    //tenta gravr no bd
                    try
                    {
                        Elder elder = db.addElder(firstname, middlename, lastname, db.currentTime());
                        Toast.makeText(getApplicationContext(), "Elder added with success", Toast.LENGTH_SHORT).show();
                        //atualiza estatisticas
                        long result = db.addStatisticsElders(db.getCurrentYear(), elder.getId());
                        Toast.makeText(getApplicationContext(), "Elder " + elder.getFirst_name() + " " + elder.getMiddle_name()  + " "
                                + elder.getLast_name() + " statistics " + db.getCurrentYear() + " updated", Toast.LENGTH_SHORT).show();
                        Log.i("LOG_TAG", "Elder id " + result + "added");

                        db.updateTotalStatistics(db.getCurrentYear());
                        Toast.makeText(getApplicationContext(), "Statistics " + db.getCurrentYear() +" updated", Toast.LENGTH_SHORT).show();
                    }
                    catch(SQLiteException ex)
                    {
                        ex.printStackTrace();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Fill all the fields", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //cancela com um pop-up de confirmação
        btn_cancelElder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(AddElderActivity.this);
                builder.setCancelable(false);
                builder.setIcon(android.R.drawable.ic_dialog_alert);
                builder.setTitle("Alert!");
                builder.setMessage("You will lose all your data inside the forms. Do you want to proceed ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Please, keep filling the form", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.show();
            }
        });

        //apaga todos os campos
        btn_resetElder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt_firstname.setText("");
                edt_middlename.setText("");
                edt_lastname.setText("");
                edt_firstname.requestFocus();
            }
        });

    }
}