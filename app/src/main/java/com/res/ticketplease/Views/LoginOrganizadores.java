package com.res.ticketplease.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.res.ticketplease.R;

public class LoginOrganizadores extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_organizadores);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}