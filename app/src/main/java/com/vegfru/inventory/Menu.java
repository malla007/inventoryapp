package com.vegfru.inventory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void launchAddFruits(View view) {
        Intent intent = new Intent(Menu.this,Fruits.class);
        startActivity(intent);
    }
    public void launchAddVegetables(View view) {
        Intent intent = new Intent(Menu.this,Vegetables.class);
        startActivity(intent);
    }

    public void launchEditFruit(View view) {
        Intent intent = new Intent(Menu.this,FruitList.class);
        startActivity(intent);
    }

    public void launchEditVegetables(View view) {
        Intent intent = new Intent(Menu.this,VegetableList.class);
        startActivity(intent);
    }

    public void logout(View view) {
       finish();
    }

    public void launchInventory(View view) {
        Intent intent = new Intent(Menu.this,Inventory.class);
        startActivity(intent);
    }
}