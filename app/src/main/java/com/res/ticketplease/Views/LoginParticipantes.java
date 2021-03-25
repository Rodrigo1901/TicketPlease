package com.res.ticketplease.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.res.ticketplease.R;

public class LoginParticipantes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_participantes);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}