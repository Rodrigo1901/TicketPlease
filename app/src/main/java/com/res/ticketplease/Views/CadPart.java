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
import com.res.ticketplease.Classes.Participante;
import com.res.ticketplease.Config.Base64Custom;
import com.res.ticketplease.Config.ConfigFirebase;
import com.res.ticketplease.R;

public class CadPart extends AppCompatActivity {

    private EditText fieldNome, fieldCPF, fieldEmail, fieldSenha, fieldRepetirSenha;
    private CardView cadButton;
    private FirebaseAuth autenticacao;
    private Participante participante;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_part);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Cadastro Participante");

        fieldNome = findViewById(R.id.fieldCadPartNome);
        fieldCPF = findViewById(R.id.fieldCadPartCPF);
        fieldEmail = findViewById(R.id.fieldCadPartEmail);
        fieldSenha = findViewById(R.id.fieldCadPartSenha);
        fieldRepetirSenha = findViewById(R.id.fieldCadPartRepetirSenha);
        cadButton = findViewById(R.id.cadPartButton);

        SimpleMaskFormatter smf = new SimpleMaskFormatter("NNN.NNN.NNN-NN");
        MaskTextWatcher mtw = new MaskTextWatcher(fieldCPF, smf);
        fieldCPF.addTextChangedListener(mtw);

        cadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validaCampos()) {
                    participante = new Participante();
                    participante.setNome(fieldNome.getText().toString());
                    participante.setCpf(fieldCPF.getText().toString());
                    participante.setEmail(fieldEmail.getText().toString());
                    participante.setSenha(fieldSenha.getText().toString());
                    cadastrarParticipante(participante);
                }

            }
        });

    }

    private boolean validaCampos() {

        boolean res = false;
        String nome = fieldNome.getText().toString();
        String cpf = fieldCPF.getText().toString();
        String email = fieldEmail.getText().toString();
        String senha = fieldSenha.getText().toString();
        String repetirSenha = fieldRepetirSenha.getText().toString();

        if (isCampoVazio(nome)) {
            fieldNome.requestFocus();
            Toast.makeText(CadPart.this, "Nome inválido!", Toast.LENGTH_SHORT).show();
        } else if (isCampoVazio(cpf)) {
            fieldCPF.requestFocus();
            Toast.makeText(CadPart.this, "CPF inválido!", Toast.LENGTH_SHORT).show();
        } else if (isCampoVazio(email)) {
            fieldEmail.requestFocus();
            Toast.makeText(CadPart.this, "Email inválido!", Toast.LENGTH_SHORT).show();
        } else if (isCampoVazio(senha)) {
            fieldSenha.requestFocus();
            Toast.makeText(CadPart.this, "Senha inválida!", Toast.LENGTH_SHORT).show();
        } else if (!isSenhasIguais(repetirSenha, senha)) {
            Toast.makeText(CadPart.this, "As senhas não coincidem!", Toast.LENGTH_SHORT).show();
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

    private void cadastrarParticipante(final Participante participante) {

        autenticacao = ConfigFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
                participante.getEmail(), participante.getSenha()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    Toast.makeText(CadPart.this, "Sucesso ao cadastrar!", Toast.LENGTH_SHORT).show();
                    finish(); // Fecha a tela de cadastro e vai para a tela principal

                    try {

                        String idUser = Base64Custom.codificarBase64(participante.getEmail());
                        participante.setId(idUser);
                        participante.salvar();

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

                    Toast.makeText(CadPart.this, excecao, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}