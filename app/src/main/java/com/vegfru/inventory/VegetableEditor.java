package com.vegfru.inventory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class VegetableEditor extends AppCompatActivity {

    EditText productNameText;
    EditText productPriceText;
    EditText productQuantityText;
    Button deleteBtn;
    Button editBtn;

    String documentId;
    String productName;
    double productPrice;
    double productQuantity;

    ArrayList<String> brands = new ArrayList<String>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vegatable_editor);
        documentId = getIntent().getStringExtra("documentId");
        productName = getIntent().getStringExtra("productName");
        productPrice = Double.parseDouble(getIntent().getStringExtra("productPrice"));
        productQuantity = Double.parseDouble(getIntent().getStringExtra("productQuantity"));

        productNameText = findViewById(R.id.editor_product_name_text);
        productPriceText = findViewById(R.id.editor_product_price_text);
        productQuantityText = findViewById(R.id.editor_product_quantity_text);
        productNameText.setText(productName);
        productPriceText.setText(String.valueOf((int)productPrice));
        productQuantityText.setText(String.valueOf((int)productQuantity));

        editBtn = findViewById(R.id.editor_edit_button);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(productNameText.getText().toString().equals(""))){
                    try {
                        updateProduct();
                    }catch (Exception e){
                        Toast.makeText(VegetableEditor.this,
                                "Error: Missing Fields!", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(VegetableEditor.this,
                            "Error: Missing Fields!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        deleteBtn = findViewById(R.id.editor_delete_button);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteProduct();
            }
        });

    }

    public void updateProduct(){
        DocumentReference ref = db.collection("products").document(documentId);
        ref.update( "productName", productNameText.getText().toString(),
                "productPrice", parseInt(productPriceText.getText().toString()),
                "productQuantity", parseInt(productQuantityText.getText().toString()))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(VegetableEditor.this,
                                "Product Successfully Edited!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(VegetableEditor.this,
                                "Error: Product not edited!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    public void deleteProduct(){
        db.collection("products").document(documentId)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(VegetableEditor.this,
                                "Product Successfully Deleted!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(VegetableEditor.this,
                                "Error: Product not deleted!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}