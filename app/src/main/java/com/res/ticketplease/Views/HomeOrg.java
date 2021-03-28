package com.res.ticketplease.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.res.ticketplease.Classes.Organizador;
import com.res.ticketplease.Config.ConfigFirebase;
import com.res.ticketplease.Config.FirebaseUser;
import com.res.ticketplease.R;

public class HomeOrg extends AppCompatActivity {

    private String identificadorUsuario;
    private DatabaseReference dataRef;
    private TextView nome, endereco;
    private Organizador organizador;
    private Button novoEvento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_org);

        getSupportActionBar().setTitle("Home");

        dataRef = ConfigFirebase.getFirebaseDatabase();
        identificadorUsuario = FirebaseUser.getIdentificadorUsuario();

        nome = findViewById(R.id.textViewOrgHomeNome);
        endereco = findViewById(R.id.textViewHomeOrgEndereco);
        novoEvento = findViewById(R.id.adicionarEventoButton);

        dataRef.child("organizadores").child(identificadorUsuario).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Organizador organizador = snapshot.getValue(Organizador.class);
                nome.setText(organizador.getNome());
                endereco.setText(organizador.getEndereco());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        novoEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(HomeOrg.this, NovoEvento.class);
                startActivity(it);
            }
        });

    }
}