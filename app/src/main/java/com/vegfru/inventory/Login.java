package com.vegfru.inventory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Login extends AppCompatActivity {

    private EditText usernameText;
    private EditText passwordText;
    private FirebaseFirestore db =  FirebaseFirestore.getInstance();
    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameText = findViewById(R.id.username_edit_text);
        passwordText = findViewById(R.id.password_edit_text);

    }

    public void launchMenu(View view) {
        username = usernameText.getText().toString();
        password = passwordText.getText().toString();
        if (!username.equals("") && !password.equals("")) {
            login();
        } else {
            Toast.makeText(Login.this, "Empty Fields!",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void login() {

        db.collection("users")
                .whereEqualTo("username", username)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int count = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                count = count + 1;
                                if (document.getData().get("password").equals(password)) {
                                    Toast.makeText(Login.this,
                                            "Login Successful!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Login.this,
                                            Menu.class);
                                    startActivity(intent);
                                    usernameText.setText("");
                                    passwordText.setText("");
                                    usernameText.clearFocus();
                                    passwordText.clearFocus();
                                } else {
                                    Toast.makeText(Login.this,
                                            "Error : Password is incorrect!",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                            if (count == 0) {
                                Toast.makeText(Login.this,
                                        "Error : Username entered is incorrect!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(Login.this,
                                    "Error : Login Not Successful!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}