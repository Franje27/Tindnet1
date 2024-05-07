package com.example.tindnet1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class Login_empresa extends Login_usuario {
    private static final String TAG="Login_usuario";
    private EditText editTextName, editTextEmail, editTextPassword;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro_empresa);

        mAuth = FirebaseAuth.getInstance();
        // Obtención de referencias a los EditTexts
        editTextName = findViewById(R.id.editTextText);
        editTextEmail = findViewById(R.id.editTextTextEmailAddress);
        editTextPassword = findViewById(R.id.editTextTextPassword2);

        Button buttonCreateAccount = findViewById(R.id.button6);
        buttonCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// Obtención de los datos de los EditTexts
                String name = editTextName.getText().toString();
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();


            }
        });
    }

}