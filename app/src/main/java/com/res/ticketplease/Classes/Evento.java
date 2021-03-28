package com.res.ticketplease.Classes;

import com.google.firebase.database.DatabaseReference;
import com.res.ticketplease.Config.ConfigFirebase;
import com.res.ticketplease.Config.FirebaseUser;

public class Evento {

    private String nome, local, data, horário, preco, maxParticipantes, detalhes;

    public Evento() {
    }

    public void salvar() {

        String userID = FirebaseUser.getIdentificadorUsuario();
        DatabaseReference firebaseRef = ConfigFirebase.getFirebaseDatabase();
        DatabaseReference evento = firebaseRef.child("eventos").child(getNome());
        DatabaseReference listEventos = firebaseRef.child("organizadores").child(userID).child("eventos").child(getNome());
        DatabaseReference ultimoEvento = firebaseRef.child("organizadores").child(userID).child("ultimo");

        ultimoEvento.setValue(getNome());
        evento.setValue(this);
        listEventos.setValue(this);
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHorário() {
        return horário;
    }

    public void setHorário(String horário) {
        this.horário = horário;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    public String getMaxParticipantes() {
        return maxParticipantes;
    }

    public void setMaxParticipantes(String maxParticipantes) {
        this.maxParticipantes = maxParticipantes;
    }

    public String getDetalhes() {
        return detalhes;
    }

    public void setDetalhes(String detalhes) {
        this.detalhes = detalhes;
    }
}
