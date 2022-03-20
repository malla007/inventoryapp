package com.vegfru.inventory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void launchLogin(View view) {
        intent = new Intent(this,Login.class);
        startActivity(intent);
    }

    public void launchRegister(View view) {
        intent = new Intent(this,Register.class);
        startActivity(intent);
    }


}