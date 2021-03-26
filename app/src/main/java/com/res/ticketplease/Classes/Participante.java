package com.res.ticketplease.Classes;

import com.google.firebase.database.DatabaseReference;
import com.res.ticketplease.Config.ConfigFirebase;

public class Participante  {

    private String nome, cpf, email, senha, id;

    public Participante() {
    }

    public void salvar() {

        DatabaseReference firebaseRef = ConfigFirebase.getFirebaseDatabase();
        DatabaseReference usuario = firebaseRef.child("participantes").child(getId());

        usuario.setValue(this);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
