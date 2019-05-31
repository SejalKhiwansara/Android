package com.example.madhura.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.app.ProgressDialog;
import android.content.Intent;


public class LoginActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener,View.OnClickListener {
    RadioGroup rg;
    //ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private TextView fbook,acc,sin,sup,login,act;
    private EditText mal,pswd;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login1);
        login=(TextView)findViewById(R.id.loginas);
        sup = (TextView)findViewById(R.id.sup);
        sin = (TextView)findViewById(R.id.sin);
        act= (TextView)findViewById(R.id.act);
        mal = (EditText)findViewById(R.id.mal);
        pswd = (EditText)findViewById(R.id.pswd);
        pd=new ProgressDialog(this);
        pd.setMessage("Please wait...");
        act.setOnClickListener(this);
        sup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent it = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(it);
            }
        });
        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                login();
            }
        });
        rg=(RadioGroup)findViewById(R.id.radioGroup);
        rg.setOnCheckedChangeListener(this);
        //progressDialog = new ProgressDialog(this);
        Firebase.setAndroidContext(this);
        firebaseAuth=FirebaseAuth.getInstance();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.radioButton:
                Toast.makeText(this, "farmer login", Toast.LENGTH_SHORT).show();
                TEST.type="farmer";
//                progressDialog.setMessage("Please Wait...");
//                progressDialog.show();
                break;


            case R.id.radioButton2:
                Toast.makeText(this, "consumer login", Toast.LENGTH_SHORT).show();
                TEST.type="consumer";
                break;

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.act:
                login();
                break;
        }
    }
    void login(){
        final String email=mal.getText().toString();
        String pass=pswd.getText().toString();

        if(email.equals("")||email.length()==0||pass.equals("")||pass.length()==0){
            Toast.makeText(this, "Enter both credentials..!!", Toast.LENGTH_SHORT).show();
            return;
        }
        pd.show();
        firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    TEST.email=email;
                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                    Intent it=new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(it);
                } else {
                    Toast.makeText(LoginActivity.this, "Login Not Successful", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        });
    }
}


