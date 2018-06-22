package com.example.icg_dominicana.pribandoenreportero.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.icg_dominicana.pribandoenreportero.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    Button button_login, button_register, button_nologin;
    EditText editTextemail, editTextpassword;

    FirebaseAuth.AuthStateListener mAuthStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.login_layout );


        button_login = (Button) findViewById( R.id.button_signin );
        button_nologin = (Button) findViewById( R.id.id_buttonNoLogin);
        button_register = (Button) findViewById( R.id.button_register );
        editTextemail = (EditText) findViewById( R.id.id_editText_email );
        editTextpassword = (EditText) findViewById( R.id.id_editText_password );


        button_register.setOnClickListener( this );
        button_login.setOnClickListener( this );
        button_nologin.setOnClickListener( this );

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user =  firebaseAuth.getCurrentUser();
                if (user != null){
                    Toast.makeText( LoginActivity.this, "Seccion iniciada",Toast.LENGTH_LONG).show();
                    goToMain();
                }else {
                    Toast.makeText( LoginActivity.this, "ERROR DE REGISTRO SESION",Toast.LENGTH_LONG).show();
                }
            }
        };

    }

    private void register(String email, String pass){
        FirebaseAuth.getInstance().createUserWithEmailAndPassword( email,pass).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText( LoginActivity.this, "Seccion iniciada",Toast.LENGTH_LONG).show();
                }else {
                    Log.i( "REGISTRO", task.getException().getMessage()+"" );
                }
            }
        } );

    }


    private void signin(String email, String pass){
        FirebaseAuth.getInstance().createUserWithEmailAndPassword( email,pass).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText( LoginActivity.this, "Seccion iniciada",Toast.LENGTH_LONG).show();
                }else {
                    Log.i( "SESION",task.getException().getMessage()+"" );
                }
            }
        } );

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case  R.id.button_signin:
                String email_sign =  editTextemail.getText().toString();
                String pass_sign =  editTextpassword.getText().toString();
                signin( email_sign, pass_sign );
                break;
            case R.id.button_register:
                String email_reg =  editTextemail.getText().toString();
                String pass_reg =  editTextpassword.getText().toString();
                register( email_reg, pass_reg );
                break;

            case R.id.id_buttonNoLogin:
                goToMain();
                break;
        }
    }
    @Override
    protected void onStart(){
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener( mAuthStateListener );
    }

    @Override
    protected void onStop(){
        super.onStop();
        if (mAuthStateListener != null){
            FirebaseAuth.getInstance().removeAuthStateListener( mAuthStateListener );
        }
    }
    private void goToMain(){
        Intent intent =  new Intent(this, MainActivity.class);
        intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
        startActivity( intent );
    }

    private boolean isValidEmail(String email){
        return !TextUtils.isEmpty( email ) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    private boolean isValidPassword(String password){
        return password.length() >= 6;
    }

}
