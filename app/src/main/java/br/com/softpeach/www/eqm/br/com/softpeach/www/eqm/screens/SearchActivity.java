package br.com.softpeach.www.eqm.br.com.softpeach.www.eqm.screens;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.softpeach.www.eqm.R;
import br.com.softpeach.www.eqm.adapter.EldersAdapter;
import br.com.softpeach.www.eqm.helper.DatabaseHelper;
import br.com.softpeach.www.eqm.model.Elder;

public class SearchActivity extends AppCompatActivity {

    private ListView elderList;
    private ArrayList<Elder> dbListResult;
    private EldersAdapter adapter;
    private DatabaseHelper db = new DatabaseHelper(this);
    private FloatingActionButton actionBtnAdd;
    private EditText edt_search;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        getSupportActionBar().setSubtitle("Search");
        //configura componentes
        actionBtnAdd = findViewById(R.id.abtn_addelder);
        edt_search = findViewById(R.id.edt_search);
        elderList = findViewById(R.id.listView);

        //pega dados de elderes do bd
        dbListResult = (ArrayList<Elder>) db.getAllElders();
        //coloca dados do bd dentro do custom adapter de elderes
        adapter = new EldersAdapter(this, dbListResult);
        //apresenta dados na lista
        elderList.setAdapter(adapter);

        //ao dar um longo clique
        elderList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //cria um objeto elder e configura com valores do adapter
                final Elder elder = (Elder) adapter.getItem(position);
                //cria um pop-up de confirmação
                AlertDialog.Builder builder = new AlertDialog.Builder(SearchActivity.this);
                builder.setIcon(android.R.drawable.ic_dialog_alert);
                builder.setCancelable(false);
                builder.setTitle("Warning...");
                builder.setMessage("Do you want to delete elder: " + elder.getFirst_name() + " " + elder.getMiddle_name() + " " + elder.getLast_name() + " ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //tenta deletar do banco de dados
                        try {
                            db.deleteElder(elder.getId());
                            Toast.makeText(getApplicationContext(), "Elder " + elder.getFirst_name() + " " + elder.getMiddle_name() + " " + elder.getLast_name() + " deleted successfully!" , Toast.LENGTH_SHORT).show();
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

        actionBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddElderActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}