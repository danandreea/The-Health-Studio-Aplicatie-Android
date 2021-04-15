package com.example.proiect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity implements TextWatcher,CompoundButton.OnCheckedChangeListener {



    private EditText Emailet,Passet;
    private Button SignIn;
    private TextView tv_SignUp;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    //Remember user si pass folosind SharedPrefs
    private CheckBox remember_userpass;
    SharedPreferences srdPref;
    SharedPreferences.Editor editor;
    private static final String PREF_NAME= "prefs";
    private static final String KEY_REMEMBER= "remember";
    private static final String KEY_USER= "username";
    private static final String KEY_PASSWORD= "password";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);

        firebaseAuth=firebaseAuth.getInstance();

        srdPref=getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor=srdPref.edit();

        Emailet=findViewById(R.id.email);
        Passet=findViewById(R.id.pass);

        remember_userpass=(CheckBox)findViewById(R.id.checkbox);

        SignIn=findViewById(R.id.signin_login);
        progressDialog=new ProgressDialog(this);
        tv_SignUp=findViewById(R.id.signup_login);

        if(srdPref.getBoolean(KEY_REMEMBER,false))
            remember_userpass.setChecked(true);
        else
            remember_userpass.setChecked(false);

        Emailet.setText(srdPref.getString(KEY_USER,""));
        Passet.setText(srdPref.getString(KEY_PASSWORD,""));

        Emailet.addTextChangedListener(this);
        Passet.addTextChangedListener(this);
        remember_userpass.setOnCheckedChangeListener(this);


        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });


        tv_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void Login()
    {
        String email=Emailet.getText().toString();
        String pass=Passet.getText().toString();

        if(TextUtils.isEmpty(email))
        {
            Emailet.setError("Enter your email!");
            return;
        }
        else if(TextUtils.isEmpty(pass))
        {
            Passet.setError("Enter your password!");
            return;
        }

        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    Toast.makeText(SignInActivity.this, "Succesfully registered", Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(SignInActivity.this, ProfileActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Toast.makeText(SignInActivity.this, "Sign In fail!", Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
            }
        });


    }

    private void managePrefs()
    {
        if(remember_userpass.isChecked())
        {
            editor.putString(KEY_USER,Emailet.getText().toString().trim());
            editor.putString(KEY_PASSWORD,Passet.getText().toString().trim());
            editor.putBoolean(KEY_REMEMBER, true);
            editor.apply();
        }
        else
        {
            editor.putBoolean(KEY_REMEMBER, false);
            editor.remove(KEY_PASSWORD);
            editor.remove(KEY_USER);
            editor.apply();
        }
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        managePrefs();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
            managePrefs();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}

