package com.vegfru.inventory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import static java.lang.Integer.parseInt;

public class Inventory extends AppCompatActivity {

    EditText productNameText;
    EditText productPriceText;
    EditText productQuantityText;
    EditText keywordText;
    TextView totalText;
    String productPrice;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        productNameText = findViewById(R.id.pos_product_name_text);
        productPriceText = findViewById(R.id.pos_product_price_text);
        productQuantityText = findViewById(R.id.pos_product_quantity_text);
        totalText = findViewById(R.id.pos_product_total_text);
        keywordText = findViewById(R.id.pos_product_id_edit_text);

    }

    public void searchProduct(View view){
        db.collection("products")
                .whereEqualTo("productName", keywordText.getText().toString())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int count = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                productPrice = document.getData().get("productPrice").toString();
                                productNameText.setText(document.getData().get("productName").toString());
                                productQuantityText.setText(String.valueOf((int)
                                        Double.parseDouble(document.getData().get("productQuantity").toString())));
                                productPriceText.setText(String.valueOf((int)Double.parseDouble(productPrice)));
                                count = count+1;
                            }
                            if(count<1){
                                Toast.makeText(Inventory.this, "Error: Couldn't find product!", Toast.LENGTH_SHORT).show();
                                productNameText.setText("");
                                productPriceText.setText("");
                                totalText.setText("");
                                productNameText.setText("");
                            }
                        } else {
                            Toast.makeText(Inventory.this, "Error: Couldn't find product!", Toast.LENGTH_SHORT).show();
                            productNameText.setText("");
                            productPriceText.setText("");
                            totalText.setText("");
                            productNameText.setText("");
                        }
                    }
                });
    }

    public void calculateTotal(View view) {
        if(!productQuantityText.getText().toString().equals("")
                && !productPriceText.getText().toString().equals("")){
            int total = parseInt(productQuantityText.getText().toString()) *
                    (int)Double.parseDouble(productPriceText.getText().toString());
            totalText.setText("Total : "+ total);
        }else{
            totalText.setText("Total : ");
        }

    }
}