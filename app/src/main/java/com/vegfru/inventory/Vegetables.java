package com.vegfru.inventory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Vegetables extends AppCompatActivity {

    EditText productNameText;
    EditText productPriceText;
    EditText productQuantityText;

    String productName;
    Double productPrice;
    Double productQuantity;

    FirebaseFirestore db;
    LinearLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vegetables);
        productNameText = findViewById(R.id.product_name_text);
        productPriceText = findViewById(R.id.product_price_text);
        productQuantityText = findViewById(R.id.product_quantity_text);
    }
    public void addProduct(){
        db = FirebaseFirestore.getInstance();
        Map<String, Object> data = new HashMap<>();
        data.put("productName", productName);
        data.put("productPrice", productPrice);
        data.put("productQuantity", productQuantity);
        data.put("category","Vegetables");
        db.collection("products")
                .add(data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        productNameText.setText("");
                        productPriceText.setText("");
                        productQuantityText.setText("");
                        productNameText.clearFocus();
                        productPriceText.clearFocus();
                        productQuantityText.clearFocus();
                        Toast.makeText(Vegetables.this, "Product Successfully Added!", Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Vegetables.this, "Error: Product not added!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void addVegetableClick(View view) {
        mainLayout = (LinearLayout)findViewById(R.id.linearLayoutProduct);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm != null){
            imm.hideSoftInputFromWindow(mainLayout.getWindowToken(), 0);
        }
        try {
            productName = productNameText.getText().toString();
            productPrice = Double.parseDouble(String.valueOf(productPriceText.getText()));
            productQuantity = Double.parseDouble(String.valueOf(productQuantityText.getText()));
            if(!(productName.equals(""))){
                addProduct();
            }else{
                Toast.makeText(Vegetables.this, "Missing Fields!", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(Vegetables.this, "Missing Fields!", Toast.LENGTH_SHORT).show();
        }
    }
}