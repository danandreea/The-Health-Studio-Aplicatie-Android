package com.example.proiect;

import android.app.ProgressDialog;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

private EditText etEmail,etPass,etPass_confirm;
private Button SignUp;
private TextView tv_SignIn;
private ProgressDialog progressDialog;
private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        firebaseAuth=firebaseAuth.getInstance();
        etEmail=findViewById(R.id.et_email);
        etPass=findViewById(R.id.et_pass);
        etPass_confirm=findViewById(R.id.et_pass_confirm);
        SignUp=findViewById(R.id.register);
        progressDialog=new ProgressDialog(this);
        tv_SignIn=findViewById(R.id.signin_register);

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();
            }
        });


        tv_SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }



    private void Register()
    {
        String email=etEmail.getText().toString();
        String pass=etPass.getText().toString();
        String pass2=etPass_confirm.getText().toString();
        if(TextUtils.isEmpty(email))
        {
            etEmail.setError("Enter your email!");
            return;
        }
        else if(TextUtils.isEmpty(pass))
        {
            etPass.setError("Enter your password!");
            return;
        }
        else if(!pass.equals(pass2))
        {
            etPass_confirm.setError("Different password!");
            return;
        }
        if(pass.length()<5)
        {
            etPass.setError("Length should be > 5");
            return;
        }
        if(!isValidEmail(email))
        {
            etEmail.setError("Invalid email!");
            return;
        }
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    Toast.makeText(SignUpActivity.this, "Succesfully registered", Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(SignUpActivity.this, ProfileActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Toast.makeText(SignUpActivity.this, "Sign Up fail!", Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
            }
        });
    }

    private Boolean isValidEmail(CharSequence target)
    {
     return (!TextUtils.isEmpty(target)&& Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

}
