package com.res.ticketplease.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.res.ticketplease.Classes.Evento;
import com.res.ticketplease.Classes.Organizador;
import com.res.ticketplease.Config.ConfigFirebase;
import com.res.ticketplease.Config.FirebaseUser;
import com.res.ticketplease.R;

public class HomeOrg extends AppCompatActivity {

    private String identificadorUsuario, ultimoEvento;
    private DatabaseReference dataRef;
    private TextView nome, endereco, nomeEvento, localEvento, dataEvento, horarioEvento;
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
        nomeEvento = findViewById(R.id.homeOrgNomeEvento);
        localEvento = findViewById(R.id.homeOrgLocalEvento);
        dataEvento = findViewById(R.id.homeOrgDataEvento);
        horarioEvento = findViewById(R.id.homeOrgHorarioEvento);

        dataRef.child("organizadores").child(identificadorUsuario).addListenerForSingleValueEvent(new ValueEventListener() {
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

        dataRef.child("organizadores").child(identificadorUsuario).child("ultimo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    dataRef.child("organizadores").child(identificadorUsuario).child("eventos").child(snapshot.getValue().toString()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                Evento evento = snapshot.getValue(Evento.class);
                                nomeEvento.setText(evento.getNome());
                                localEvento.setText(evento.getLocal());
                                dataEvento.setText(evento.getData());
                                horarioEvento.setText(evento.getHorário());
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }

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