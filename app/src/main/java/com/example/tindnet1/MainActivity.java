package com.example.tindnet1;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.BeginSignInResult;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.SignInMethodQueryResult;




public class MainActivity extends AppCompatActivity {
    private static final int REQ_ONE_TAP = 2; // Puede ser cualquier número entero único para la actividad.


    private static final String TAG = "MainActivity";
    private FirebaseAuth mAuth;
    private SignInClient oneTapClient;
    private BeginSignInRequest signUpRequest;
    private GoogleSignInClient mGoogleSignInClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initApp();
        //Instanciación de FireBase
        mAuth = FirebaseAuth.getInstance();

        Button buttonSignInWithGoogle = findViewById(R.id.button8);
        buttonSignInWithGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// Llama a la función para iniciar sesión con Google
              //  signInWithGoogle();
                beginGoogleSignIn();
            }
        });



    }
    public void abrirLoginusuario(View view) {
// Crear un Intent para abrir la actividad de la página de registro de usuario
        Intent intent = new Intent(this, Login_usuario.class);
        startActivity(intent); // Iniciar la actividad
    }
    public void abrirLoginempresa(View view) {
// Crear un Intent para abrir la actividad de la página de registro de empresa
        Intent intent = new Intent(this, Login_empresa.class);
        startActivity(intent); // Iniciar la actividad
    }
    public void abrirInicioSesion(View view) {
// Crear un Intent para abrir la actividad de la página de login
        Intent intent = new Intent(this, Inicio_sesion.class);
        startActivity(intent); // Iniciar la actividad
    }

    private void updateUI(FirebaseUser user) {
// Aquí puedes actualizar la interfaz de usuario según el estado de autenticación
// Por ejemplo, puedes redirigir al usuario a otra actividad después de que haya iniciado sesión correctamente
        if (user != null) {
            Intent intent = new Intent(MainActivity.this, Login_usuario.class); //Cambiarlo por la página siguiente
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
// Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }
    }

    private void initApp() {
        oneTapClient = Identity.getSignInClient(this);
        signUpRequest = BeginSignInRequest.builder()
                .setPasswordRequestOptions(BeginSignInRequest.PasswordRequestOptions.builder().setSupported(false).build())
                .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        .setServerClientId("1094989100388-kqq7lvv8iqoc9k0e95pg1bpf1p2mjhe8.apps.googleusercontent.com")
                        .setFilterByAuthorizedAccounts(false)
                        .build())
                .setAutoSelectEnabled(false)
                .build();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestProfile()
                .requestEmail()
                .requestIdToken("1094989100388-4vb502im4l4rj4men6av6c4trksqhj49.apps.googleusercontent.com")
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void beginGoogleSignIn() {
        oneTapClient.beginSignIn(signUpRequest)
                .addOnSuccessListener(new OnSuccessListener<BeginSignInResult>() {
                    @Override
                    public void onSuccess(BeginSignInResult beginSignInResult) {
                        try {
                            startIntentSenderForResult(
                                    beginSignInResult.getPendingIntent().getIntentSender(), REQ_ONE_TAP, null, 0, 0, 0, null);
                        } catch (IntentSender.SendIntentException e) {
                            Toast.makeText(MainActivity.this, "Error: No se pudo iniciar el proceso de inicio de sesión con Google. PendingIntent", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnCanceledListener(new OnCanceledListener() {
                    @Override
                    public void onCanceled() {
                        Toast.makeText(MainActivity.this, "Cancelado", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                        startActivityForResult(signInIntent, REQ_ONE_TAP);
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_ONE_TAP) {
            try {
                handleOneTapSignIn(data);
            } catch (ApiException e) {
// Handle API error
            }
        }
    }

    private void handleOneTapSignIn(Intent data) throws ApiException {
        SignInCredential credential = oneTapClient.getSignInCredentialFromIntent(data);
        String token = credential.getGoogleIdToken();
        if (token != null) {
            AuthCredential firebaseCredential = GoogleAuthProvider.getCredential(token, null);
            mAuth.signInWithCredential(firebaseCredential)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                            } else {
                                updateUI(null);
                            }
                        }
                    });
        } else {
            Toast.makeText(this, "No se pudo obtener el token de identificación de Google.", Toast.LENGTH_SHORT).show();
        }
    }

    /* Manejo de errores:
Error al enviar la PendingIntent.
Error al iniciar el proceso de inicio de sesión con Google One Tap.
No se puede obtener el token de identificación de Google del Intent.
El resultado de inicio de sesión con Google no es válido.
*/
  /*  private void signInWithGoogle() {
// Configura las opciones de la solicitud del token de identificación de Google
        BeginSignInRequest.GoogleIdTokenRequestOptions googleIdTokenRequestOptions =
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        .setServerClientId("1094989100388-4vb502im4l4rj4men6av6c4trksqhj49.apps.googleusercontent.com") // Utiliza el ID de cliente "servidor"
                        .setFilterByAuthorizedAccounts(true)
                        .build();

// Configura la solicitud de inicio de sesión con Google
        BeginSignInRequest signInRequest =
                BeginSignInRequest.builder()
                        .setGoogleIdTokenRequestOptions(googleIdTokenRequestOptions)
                        .build();

// Inicia el proceso de inicio de sesión con Google One Tap
        Identity.getSignInClient(this).beginSignIn(signInRequest)
                .addOnSuccessListener(this, new OnSuccessListener<BeginSignInResult>() {
                    @Override
                    public void onSuccess(BeginSignInResult beginSignInResult) {
                        try {
                            beginSignInResult.getPendingIntent().send(MainActivity.this, REQ_ONE_TAP, null, null, null, null);
                        } catch (PendingIntent.CanceledException e) {
// Error al enviar la PendingIntent
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Error: No se pudo iniciar el proceso de inicio de sesión con Google. PendingIntent", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
// Otro error al configurar la solicitud de inicio de sesión
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Error: Configuración incorrecta de la solicitud de inicio de sesión con Google One Tap.", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
// Error al iniciar el proceso de inicio de sesión con Google One Tap
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "Error: No se pudo iniciar el proceso de inicio de sesión con Google. GoogleTap", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_ONE_TAP:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    try {
                        SignInCredential credential = Identity.getSignInClient(this).getSignInCredentialFromIntent(data);
                        String idToken = credential.getGoogleIdToken();
                        if (idToken != null) {
// Got an ID token from Google. Use it to authenticate
// with Firebase.
                            AuthCredential firebaseCredential = GoogleAuthProvider.getCredential(idToken, null);
                            mAuth.signInWithCredential(firebaseCredential)
                                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {
// Sign in success, update UI with the signed-in user's information
                                                Log.d(TAG, "signInWithCredential:success");
                                                FirebaseUser user = mAuth.getCurrentUser();
                                                updateUI(user);
                                            } else {
// If sign in fails, display a message to the user.
                                                Log.w(TAG, "signInWithCredential:failure", task.getException());
                                                updateUI(null);
                                            }
                                        }
                                    });
                        } else {
// No se pudo obtener el token de identificación de Google del Intent
                            Log.e(TAG, "Error: No se pudo obtener el token de identificación de Google.");
                            updateUI(null);
                        }
                    } catch (ApiException e) {
// Error al obtener el token de identificación de Google del Intent
                        Log.e(TAG, "signInResult:failed code=" + e.getStatusCode());
                        updateUI(null);
                    } catch (Exception e) {
// Otro error al procesar el resultado de inicio de sesión con Google
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "Error: Resultado de inicio de sesión con Google no válido.", Toast.LENGTH_SHORT).show();
                        updateUI(null);
                    }
                } else {
// Resultado de inicio de sesión con Google no válido
                    Log.e(TAG, "Error: Resultado de inicio de sesión con Google no válido.");
                    updateUI(null);
                }
                break;
        }
    }
*/



}