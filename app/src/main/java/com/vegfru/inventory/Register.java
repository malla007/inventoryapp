package com.vegfru.inventory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    private EditText usernameText;
    private EditText passwordText;
    private EditText confirmPasswordText;

    private String password;
    private String username;
    private String confirmPassword;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        usernameText = findViewById(R.id.username_edit_text);
        passwordText = findViewById(R.id.password_edit_text);
        confirmPasswordText = findViewById(R.id.password2_edit_text);
    }

    public void signUp(View view) {
        username = usernameText.getText().toString();
        password = passwordText.getText().toString();
        confirmPassword = passwordText.getText().toString();
        if(!username.equals("")&&!password.equals("")&&
                !confirmPassword.equals("")){
            if (password.equals(confirmPassword)){
                databaseSignUp();
            }else{
                Toast.makeText(Register.this, "Error: Passwords do not match!",
                        Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(Register.this, "Error: Some Fields are empty!", 
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void databaseSignUp() {
        db = FirebaseFirestore.getInstance();
        Map<String, Object> data = new HashMap<>();
        data.put("username", username);
        data.put("password", password);
        db.collection("users")
                .add(data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(Register.this, "Successfully Signed Up!",
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Register.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Register.this, "Error: Problem during Sign Up!",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

}