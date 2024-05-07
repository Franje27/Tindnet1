package com.example.tindnet1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Inicio_sesion extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText editTextEmail, editTextPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio_sesion);

        //Instanciación de FireBase
        mAuth = FirebaseAuth.getInstance();
// Obtención de referencias a los EditTexts
        editTextEmail = findViewById(R.id.editTextTextEmailAddress3);
        editTextPassword = findViewById(R.id.editTextTextPassword3);

        Button loginButton = findViewById(R.id.button4);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// Obtención de los datos de los EditTexts
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

// Llamada al método signInWithEmailAndPassword con los datos obtenidos
                signInWithEmailAndPassword(email, password);
            }
        });
    }
    private void signInWithEmailAndPassword(String email, String password) {
// Intenta iniciar sesión con el correo electrónico y la contraseña proporcionados
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
// Inicio de sesión exitoso
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null && user.isEmailVerified()) {
// Si el correo electrónico ha sido verificado, actualiza la UI
                                updateUI2(user);
                            } else {
// Si el correo electrónico no ha sido verificado, muestra un mensaje al usuario
                                mAuth.signOut(); // Cerrar sesión porque el correo electrónico no ha sido verificado
                                Toast.makeText(Inicio_sesion.this, "Por favor, verifica tu correo electrónico para iniciar sesión.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
// Si el inicio de sesión falla, muestra un mensaje al usuario
                            Toast.makeText(Inicio_sesion.this, "Error al iniciar sesión. No estás registrado.", Toast.LENGTH_SHORT).show();
                            updateUI2(null);
                        }
                    }
                });
    }

    private void updateUI2(FirebaseUser user) {
// Actualiza la interfaz de usuario según el estado de autenticación
        if (user != null) {
// Si el usuario ha iniciado sesión con éxito, redirige a la actividad principal (o cualquier otra actividad)
            Intent intent = new Intent(Inicio_sesion.this, MainActivity.class);
            startActivity(intent);
            finish(); // Finaliza Inicio_sesion para evitar que el usuario retroceda a esta actividad después de iniciar sesión
        }
    }

}
