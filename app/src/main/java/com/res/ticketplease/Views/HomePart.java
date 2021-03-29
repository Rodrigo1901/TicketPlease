package com.res.ticketplease.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.res.ticketplease.Classes.Organizador;
import com.res.ticketplease.Classes.Participante;
import com.res.ticketplease.Config.ConfigFirebase;
import com.res.ticketplease.Config.FirebaseUser;
import com.res.ticketplease.R;

public class HomePart extends AppCompatActivity {

    private String identificadorUsuario;
    private DatabaseReference dataRef;
    private TextView nome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_part);

        getSupportActionBar().setTitle("Home");

        dataRef = ConfigFirebase.getFirebaseDatabase();
        identificadorUsuario = FirebaseUser.getIdentificadorUsuario();

        nome = findViewById(R.id.textViewPartHomeNome);

        dataRef.child("participantes").child(identificadorUsuario).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Participante participante = snapshot.getValue(Participante.class);
                nome.setText(participante.getNome());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}