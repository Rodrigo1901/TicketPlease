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
import com.res.ticketplease.Classes.Organizador;
import com.res.ticketplease.Classes.Participante;
import com.res.ticketplease.Config.ConfigFirebase;
import com.res.ticketplease.R;

public class LoginOrganizadores extends AppCompatActivity {

    private TextView registrar, recuperarSenha;
    private EditText campoEmail, campoSenha;
    private Button botaoEntrar;
    private FirebaseAuth autenticacao;
    private Organizador organizador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_organizadores);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Login Organizadores");

        registrar = findViewById(R.id.textOrgRegistrarButton);
        campoEmail = findViewById(R.id.fieldOrgLoginEmail);
        campoSenha = findViewById(R.id.fieldOrgLoginSenha);
        botaoEntrar = findViewById(R.id.buttonOrgEntrar);

        botaoEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String textoEmail = campoEmail.getText().toString();
                String textoSenha = campoSenha.getText().toString();

                if (!textoEmail.isEmpty()) {
                    if (!textoSenha.isEmpty()) {
                        organizador = new Organizador();
                        organizador.setEmail(textoEmail);
                        organizador.setSenha(textoSenha);
                        validarLogin();
                    } else {
                        campoSenha.requestFocus();
                        Toast.makeText(LoginOrganizadores.this, "Preencha a senha!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    campoEmail.requestFocus();
                    Toast.makeText(LoginOrganizadores.this, "Preencha o email!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent it = new Intent(LoginOrganizadores.this, CadOrg.class);
                startActivity(it);
            }
        });

        recuperarSenha = findViewById(R.id.textOrgRecuperarSenha);
        recuperarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent it = new Intent(LoginOrganizadores.this, RecuperarSenha.class);
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
        autenticacao.signInWithEmailAndPassword(organizador.getEmail(), organizador.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    startActivity(new Intent(LoginOrganizadores.this, HomeOrg.class));

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
                    Toast.makeText(LoginOrganizadores.this, excecao, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void verificarUsuarioLogado() {

        autenticacao = ConfigFirebase.getFirebaseAutenticacao();
        if (autenticacao.getCurrentUser() != null) {
            startActivity(new Intent(LoginOrganizadores.this, HomeOrg.class));
        }
    }

}