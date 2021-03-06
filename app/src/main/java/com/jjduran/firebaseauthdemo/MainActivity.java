package com.jjduran.firebaseauthdemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonSignup;

    private TextView textViewSignin;

    private ProgressDialog progressDialog;



    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        firebaseAuth = FirebaseAuth.getInstance();


        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        textViewSignin = (TextView) findViewById(R.id.textViewSignin);
        buttonSignup = (Button) findViewById(R.id.buttonSignup);

        progressDialog = new ProgressDialog(this);


        buttonSignup.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);
    }

    private void registerUser(){


        String email = editTextEmail.getText().toString().trim();
        String password  = editTextPassword.getText().toString().trim();


        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Ingrese el email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Ingrese la contraseña",Toast.LENGTH_LONG).show();
            return;
        }



        progressDialog.setMessage("Registrando ...");
        progressDialog.show();


        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if(task.isSuccessful()){
                            //display some message here
                            Toast.makeText(MainActivity.this,"Registro Exitoso",Toast.LENGTH_LONG).show();
                        }else{
                            //display some message here
                            Toast.makeText(MainActivity.this,"Error de Registro",Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });

    }

    @Override
    public void onClick(View view) {
        if (view == buttonSignup) {
            registerUser();
        }
        if (view == textViewSignin) {
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}