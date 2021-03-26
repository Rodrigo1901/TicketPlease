package com.res.ticketplease.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.res.ticketplease.R;

public class LoginOrganizadores extends AppCompatActivity {

    private TextView registrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_organizadores);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Login Organizadores");

        registrar = findViewById(R.id.textOrgRegistrarButton);
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent it = new Intent(LoginOrganizadores.this, CadOrg.class);
                startActivity(it);
            }
        });

    }
}