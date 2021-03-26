package com.res.ticketplease.Classes;

import com.google.firebase.database.DatabaseReference;
import com.res.ticketplease.Config.ConfigFirebase;

public class Organizador {

    private String nome, endereco, email, senha, id;

    public Organizador() {
    }

    public void salvar() {

        DatabaseReference firebaseRef = ConfigFirebase.getFirebaseDatabase();
        DatabaseReference usuario = firebaseRef.child("organizadores").child(getId());

        usuario.setValue(this);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
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
