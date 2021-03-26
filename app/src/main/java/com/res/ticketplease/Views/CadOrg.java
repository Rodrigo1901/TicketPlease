package com.res.ticketplease.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.res.ticketplease.Classes.Organizador;
import com.res.ticketplease.Classes.Participante;
import com.res.ticketplease.Config.Base64Custom;
import com.res.ticketplease.Config.ConfigFirebase;
import com.res.ticketplease.R;

public class CadOrg extends AppCompatActivity {

    private EditText fieldNome, fieldEndereco, fieldEmail, fieldSenha, fieldRepetirSenha;
    private CardView cadButton;
    private FirebaseAuth autenticacao;
    private Organizador organizador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_org);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Cadastro Organizadores");

        fieldNome = findViewById(R.id.fieldCadOrgNome);
        fieldEndereco = findViewById(R.id.fieldCadOrgEndereco);
        fieldEmail = findViewById(R.id.fieldCadOrgEmail);
        fieldSenha = findViewById(R.id.fieldCadOrgSenha);
        fieldRepetirSenha = findViewById(R.id.fieldCadOrgRepetirSenha);
        cadButton = findViewById(R.id.cadOrgButton);

        cadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validaCampos()) {
                    organizador = new Organizador();
                    organizador.setNome(fieldNome.getText().toString());
                    organizador.setEndereco(fieldEndereco.getText().toString());
                    organizador.setEmail(fieldEmail.getText().toString());
                    organizador.setSenha(fieldSenha.getText().toString());
                    cadastrarOrganizador(organizador);
                }

            }
        });

    }

    private boolean validaCampos() {

        boolean res = false;
        String nome = fieldNome.getText().toString();
        String endereco = fieldEndereco.getText().toString();
        String email = fieldEmail.getText().toString();
        String senha = fieldSenha.getText().toString();
        String repetirSenha = fieldRepetirSenha.getText().toString();

        if (isCampoVazio(nome)) {
            fieldNome.requestFocus();
            Toast.makeText(CadOrg.this, "Nome inválido!", Toast.LENGTH_SHORT).show();
        } else if (isCampoVazio(endereco)) {
            fieldEndereco.requestFocus();
            Toast.makeText(CadOrg.this, "Endereço inválido!", Toast.LENGTH_SHORT).show();
        } else if (isCampoVazio(email)) {
            fieldEmail.requestFocus();
            Toast.makeText(CadOrg.this, "Email inválido!", Toast.LENGTH_SHORT).show();
        } else if (isCampoVazio(senha)) {
            fieldSenha.requestFocus();
            Toast.makeText(CadOrg.this, "Senha inválida!", Toast.LENGTH_SHORT).show();
        } else if (!isSenhasIguais(repetirSenha, senha)) {
            Toast.makeText(CadOrg.this, "As senhas não coincidem!", Toast.LENGTH_SHORT).show();
            fieldRepetirSenha.requestFocus();
        } else {
            res = true;
        }
        return res;
    }

    private boolean isCampoVazio(String valor) {

        boolean resultado = (TextUtils.isEmpty(valor) || valor.trim().isEmpty());
        return resultado;
    }

    private boolean isSenhasIguais(String confirmarSenha, String senha) {

        boolean resultado = (confirmarSenha.equals(senha));
        return resultado;
    }

    private void cadastrarOrganizador(final Organizador organizador) {

        autenticacao = ConfigFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
                organizador.getEmail(), organizador.getSenha()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    Toast.makeText(CadOrg.this, "Sucesso ao cadastrar!", Toast.LENGTH_SHORT).show();
                    finish(); // Fecha a tela de cadastro e vai para a tela principal

                    try {

                        String idUser = Base64Custom.codificarBase64(organizador.getEmail());
                        organizador.setId(idUser);
                        organizador.salvar();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    String excecao = "";
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        excecao = "Senha fraca!";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        excecao = "Email inválido!";
                    } catch (FirebaseAuthUserCollisionException e) {
                        excecao = "Conta já cadastrada!";
                    } catch (Exception e) {
                        excecao = "Erro ao cadastrar usuário: " + e.getMessage();
                        e.printStackTrace();
                    }

                    Toast.makeText(CadOrg.this, excecao, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}