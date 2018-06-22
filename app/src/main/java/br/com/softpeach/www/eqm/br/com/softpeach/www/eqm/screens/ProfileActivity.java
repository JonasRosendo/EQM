package br.com.softpeach.www.eqm.br.com.softpeach.www.eqm.screens;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.com.softpeach.www.eqm.R;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getSupportActionBar().setSubtitle("Profile");
    }
}
