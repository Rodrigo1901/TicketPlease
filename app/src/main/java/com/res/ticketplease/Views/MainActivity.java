package com.res.ticketplease.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.res.ticketplease.R;

public class MainActivity extends AppCompatActivity {

    private Button botaoPart, botaoOrg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        botaoPart = findViewById(R.id.buttonPart);
        botaoOrg = findViewById(R.id.buttonOrg);

        /* Chamada botão Participante */
        botaoPart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(MainActivity.this, LoginParticipantes.class);
                startActivity(it);
            }
        });
        botaoOrg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(MainActivity.this, LoginOrganizadores.class);
                startActivity(it);
            }
        });

    }
}