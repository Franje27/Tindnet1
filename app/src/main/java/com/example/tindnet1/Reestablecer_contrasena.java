package com.example.tindnet1;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;

public class Reestablecer_contrasena extends AppCompatActivity {

    private EditText micorreo;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reestablecer_contrasena); // Asegúrate de cambiar "tu_layout" al nombre real de tu layout XML

        mAuth = FirebaseAuth.getInstance();
        micorreo = findViewById(R.id.editTextTextEmailAddress4);
        Button reestablecer = findViewById(R.id.button9);

        reestablecer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });

    }



    private void resetPassword() {
        String email = micorreo.getText().toString().trim();

        if (email.isEmpty()) {
            Toast.makeText(this, "Por favor, ingresa tu correo electrónico.", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.fetchSignInMethodsForEmail(email)
                .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null && user.isEmailVerified()) {
// El correo electrónico está registrado, enviar correo electrónico de restablecimiento
                                mAuth.sendPasswordResetEmail(email)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(Reestablecer_contrasena.this, "Se ha enviado un correo electrónico de restablecimiento de contraseña.", Toast.LENGTH_SHORT).show();
                                                    finish();
                                                } else {
                                                    Toast.makeText(Reestablecer_contrasena.this, "Error al enviar el correo electrónico de restablecimiento de contraseña.", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            } else {
// El correo electrónico no está registrado en Firebase Authentication
                                Toast.makeText(Reestablecer_contrasena.this, "No existe una cuenta asociada a este correo electrónico.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
// Error al verificar el correo electrónico
                            Toast.makeText(Reestablecer_contrasena.this, "Error al verificar el correo electrónico.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}