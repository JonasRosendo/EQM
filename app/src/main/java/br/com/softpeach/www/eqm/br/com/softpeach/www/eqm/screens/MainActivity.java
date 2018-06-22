package br.com.softpeach.www.eqm.br.com.softpeach.www.eqm.screens;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteException;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import br.com.softpeach.www.eqm.R;
import br.com.softpeach.www.eqm.helper.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    public static final String FIRST_START = "first_start";
    public static final String BOOL_FIRST_START = "bool_first_start";
    private DatabaseHelper db = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //cria um arquivo shared preferences para manipular se é o primeiro acesso ao app.
        final SharedPreferences preferences = getSharedPreferences(FIRST_START, MODE_PRIVATE);
        final boolean first_start = preferences.getBoolean(BOOL_FIRST_START, false);

        //handler criado para maipular o tempo que a splash screen aparecerá
        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //se for primeiro acesso BOOL_FIRST_START == false
                if(!first_start)
                {
                    //BOOL_FIRST_START se torna true
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean(BOOL_FIRST_START, true);
                    editor.commit();
                    try
                    {
                        //adiciona as primeiras estatisticas ao bd, quase todas igual a 0.
                        db.addTotalStatistics(db.getCurrentYear());
                        Toast.makeText(getApplicationContext(), "Statistics " + db.getCurrentYear() + " initialized with success...", Toast.LENGTH_SHORT).show();

                        int year;
                        int i = 1;

                        //cria linhas de estatisticas no bd.
                        while(i<21)
                        {
                            year = Integer.parseInt(db.getCurrentYear()) +i;
                            db.addTotalStatistics(String.valueOf(year));
                            i++;
                        }
                        openDashboardActivity();
                    }catch (SQLiteException ex)
                    {
                        ex.printStackTrace();
                    }
                }else
                {
                    openDashboardActivity();
                }
            }
        }, 2000);
    }

    public void openDashboardActivity()
    {
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
        finish();
    }
}