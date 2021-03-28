package com.res.ticketplease.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.firebase.auth.FirebaseAuth;
import com.res.ticketplease.Classes.Evento;
import com.res.ticketplease.Classes.Organizador;
import com.res.ticketplease.R;

public class NovoEvento extends AppCompatActivity {

    private EditText fieldNome, fieldLocal, fieldData, fieldHorario, fieldMaxParticipantes;
    private CardView adicionarButton;
    private Evento evento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_evento);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Novo Evento");

        fieldNome = findViewById(R.id.fieldEventoNome);
        fieldLocal = findViewById(R.id.fieldEventoLocal);
        fieldData = findViewById(R.id.fieldEventoData);
        fieldHorario = findViewById(R.id.fieldEventoHorario);
        fieldMaxParticipantes = findViewById(R.id.fieldEventoParticipantes);
        adicionarButton = findViewById(R.id.novoEventoButton);

        SimpleMaskFormatter smf = new SimpleMaskFormatter("NN/NN/NNNN");
        MaskTextWatcher mtw = new MaskTextWatcher(fieldData, smf);
        fieldData.addTextChangedListener(mtw);

        SimpleMaskFormatter smf2 = new SimpleMaskFormatter("NN:NN - NN:NN");
        MaskTextWatcher mtw2 = new MaskTextWatcher(fieldHorario, smf2);
        fieldHorario.addTextChangedListener(mtw2);

        adicionarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validaCampos()) {
                    evento = new Evento();
                    evento.setNome(fieldNome.getText().toString());
                    evento.setLocal(fieldLocal.getText().toString());
                    evento.setData(fieldData.getText().toString());
                    evento.setHorário(fieldHorario.getText().toString());
                    evento.setMaxParticipantes(fieldMaxParticipantes.getText().toString());
                    evento.salvar();
                    finish();
                }

            }
        });

    }

    private boolean validaCampos() {

        boolean res = false;
        String nome = fieldNome.getText().toString();
        String local = fieldLocal.getText().toString();
        String data = fieldData.getText().toString();
        String horario = fieldHorario.getText().toString();
        String participantes = fieldMaxParticipantes.getText().toString();

        if (isCampoVazio(nome)) {
            fieldNome.requestFocus();
            Toast.makeText(NovoEvento.this, "Nome inválido!", Toast.LENGTH_SHORT).show();
        } else if (isCampoVazio(local)) {
            fieldLocal.requestFocus();
            Toast.makeText(NovoEvento.this, "Local inválido!", Toast.LENGTH_SHORT).show();
        } else if (isCampoVazio(data)) {
            fieldData.requestFocus();
            Toast.makeText(NovoEvento.this, "Data inválida!", Toast.LENGTH_SHORT).show();
        } else if (isCampoVazio(horario)) {
            fieldHorario.requestFocus();
            Toast.makeText(NovoEvento.this, "Horário inválido!", Toast.LENGTH_SHORT).show();
        } else if (isCampoVazio(participantes)) {
            Toast.makeText(NovoEvento.this, "Número de participantes inválido!", Toast.LENGTH_SHORT).show();
            fieldMaxParticipantes.requestFocus();
        } else {
            res = true;
        }
        return res;
    }

    private boolean isCampoVazio(String valor) {

        boolean resultado = (TextUtils.isEmpty(valor) || valor.trim().isEmpty());
        return resultado;
    }
}