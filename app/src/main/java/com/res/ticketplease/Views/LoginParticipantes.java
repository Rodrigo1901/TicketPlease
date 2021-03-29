package com.res.ticketplease.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.res.ticketplease.Classes.Participante;
import com.res.ticketplease.Config.ConfigFirebase;
import com.res.ticketplease.R;

public class LoginParticipantes extends AppCompatActivity {

    private TextView registrar, recuperarSenha;
    private EditText campoEmail, campoSenha;
    private Button botaoEntrar;
    private FirebaseAuth autenticacao;
    private Participante participante;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_participantes);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Login Participantes");

        registrar = findViewById(R.id.textPartRegistrarButton);
        campoEmail = findViewById(R.id.fieldPartLoginEmail);
        campoSenha = findViewById(R.id.fieldPartLoginSenha);
        botaoEntrar = findViewById(R.id.buttonPartEntrar);

        botaoEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String textoEmail = campoEmail.getText().toString();
                String textoSenha = campoSenha.getText().toString();

                if (!textoEmail.isEmpty()) {
                    if (!textoSenha.isEmpty()) {
                        participante = new Participante();
                        participante.setEmail(textoEmail);
                        participante.setSenha(textoSenha);
                        validarLogin();
                    } else {
                        campoSenha.requestFocus();
                        Toast.makeText(LoginParticipantes.this, "Preencha a senha!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    campoEmail.requestFocus();
                    Toast.makeText(LoginParticipantes.this, "Preencha o email!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent it = new Intent(LoginParticipantes.this, CadPart.class);
                startActivity(it);
            }
        });

        recuperarSenha = findViewById(R.id.textPartRecuperarSenha);
        recuperarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent it = new Intent(LoginParticipantes.this, RecuperarSenha.class);
                startActivity(it);
            }
        });
    }

    /*
    @Override
    protected void onStart() {
        super.onStart();
        verificarUsuarioLogado();
    }
     */

    public void validarLogin() {

        autenticacao = ConfigFirebase.getFirebaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(participante.getEmail(), participante.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    startActivity(new Intent(LoginParticipantes.this, HomePart.class));

                } else {
                    String excecao = "";
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidUserException e) {
                        excecao = "Usuário não está cadastrado!";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        excecao = "Email e senha não correspondem a um usuário cadastrado!";
                    } catch (Exception e) {
                        excecao = "Erro ao fazer login!" + e.getMessage();
                        e.printStackTrace();
                    }
                    Toast.makeText(LoginParticipantes.this, excecao, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void verificarUsuarioLogado() {

        autenticacao = ConfigFirebase.getFirebaseAutenticacao();
        if (autenticacao.getCurrentUser() != null) {
            startActivity(new Intent(LoginParticipantes.this, HomePart.class));
        }
    }
}