package com.vegfru.inventory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class VegetableList extends AppCompatActivity {

    ListView productListView;
    Intent intent;
    ArrayAdapter<String> adapter;
    ArrayList<String> productsList = new ArrayList<String>();
    ArrayList<Object> documentList = new ArrayList<Object>();
    private FirebaseFirestore db =  FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vegetable_list);
        getProducts();
        productListView = findViewById(R.id.product_list_view);
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, productsList);
        productListView.setAdapter(adapter);
        productListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent = new Intent(VegetableList.this, VegetableEditor.class);
                QueryDocumentSnapshot document = (QueryDocumentSnapshot) documentList.get(position);
                intent.putExtra("documentId",document.getId());
                intent.putExtra("productName",document.getData().get("productName").toString());
                intent.putExtra("productPrice",document.getData().get("productPrice").toString());
                intent.putExtra("productQuantity",document.getData().get("productQuantity").toString());
                startActivity(intent);
            }
        });
    }

    public void getProducts(){
        db.collection("products")
                .whereEqualTo("category","Vegetables")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                productsList.add(document.getData().get("productName").toString()
                                        +" - QTY : "
                                        +(int)Double.parseDouble(document.getData()
                                        .get("productQuantity").toString())+" - Rs. "+
                                        (int)Double.parseDouble(document.getData().
                                                get("productPrice").toString()));
                            }
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                documentList.add(document);
                            }
                            adapter.notifyDataSetChanged();
                        } else {
                        }
                    }
                });
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        this.recreate();
    }
}