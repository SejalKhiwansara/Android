package com.example.madhura.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.app.ProgressDialog;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    Firebase ref;
    private TextView sup, act, sin,  sendmail;
    private EditText pswd;
    private EditText mail;
    private EditText name;
    EditText phno;
    EditText add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register1);
        sup = (TextView) findViewById(R.id.sup);
        sin = (TextView) findViewById(R.id.sin);
        act = (TextView) findViewById(R.id.act);
        mail = (EditText) findViewById(R.id.mal);
        pswd = (EditText) findViewById(R.id.pswd);
        phno = (EditText) findViewById(R.id.phno);
        sendmail = (TextView) findViewById(R.id.sendmail);
        name = (EditText) findViewById(R.id.name);
        add = (EditText) findViewById(R.id.add);
        sendmail.setOnClickListener(this);
        act.setOnClickListener(this);
        sin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(it);
            }
        });

        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        Firebase.setAndroidContext(this);
        //ref = new Firebase("https://myprj-74da3.firebaseio.com/");
        ref=new Firebase("https://farm-database-c29f5.firebaseio.com/");
    }

    @Override
    public void onClick(View v) {
        email = mail.getText().toString().trim();
        password = pswd.getText().toString().trim();
        if (!checkEmail(email)) {
            mail.setError("Enter valid email");
        }
        if (!checkPassword(password)) {
            pswd.setError("use strong password");
        }
        switch (v.getId()) {
            case R.id.sendmail:
                if(email.equals("")||email.length()==0||password.equals("")||password.length()==0){
                    Toast.makeText(this, "Enter email and password..!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        sendmail();
                    }
                });
                break;

            case R.id.act:
                register();
                break;
        }
    }

    Matcher matcher;
    boolean checkEmail(String email) {
        Pattern pattern;
        Matcher matcher;
        String EMAIL_PATTERN = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    boolean checkPassword(String str) {
        Pattern pattern;
        String PASSWORD_PATTERN =
                "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();
    }

    String email, password, name1, phno1, add1;
    void register() {
        email = mail.getText().toString().trim();
        name1 = name.getText().toString().trim();
       // if(name1.equals("")||name1.length()==0||phno1.equals("")||phno1.length()==0||add1.equals("")||add1.length()==0){
        //    Toast.makeText(this, "Enter all credentials..!!", Toast.LENGTH_SHORT).show();
           // return;
       // }
        if (name.equals("")) {
            name.setError("name is required");
        }
        phno1 = phno.getText().toString().trim();
        if (!checkPhone(phno1)) {
            phno.setError("enter 10 digit no");
        }
        add1 = add.getText().toString().trim();
        if (add.equals("")) {
            add.setError("address is required");
        }
        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Successfully registered", Toast.LENGTH_LONG).show();
                    GetterSetter ob = new GetterSetter();
                    ob.setEmail(email);
                    ob.setName(name1);
                    ob.setAddress(add1);
                    ob.setPhone(phno1);
                    ref.child("Farmer").child(phno1).child("Personalinfo").setValue(ob);
                    progressDialog.dismiss();
                    check();
                } else {
                    Toast.makeText(RegisterActivity.this, "Registration Error", Toast.LENGTH_LONG).show();
                }
            }

        });
    }

    boolean checkPhone(String phno) {
        Pattern pattern;
        Matcher matcher;
        String PHONE_PATTERN = "^(\\+91[\\-\\s]?)?[0]?(91)?[789]\\d{9}$";
        pattern = Pattern.compile(PHONE_PATTERN);
        matcher = pattern.matcher(phno);
        return matcher.matches();
    }

    void check() {
        if (user.isEmailVerified()) {
            Toast.makeText(RegisterActivity.this, "mail okay", Toast.LENGTH_SHORT).show();
            Intent it = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(it);
        } else {
            Toast.makeText(RegisterActivity.this, "Mail is fake", Toast.LENGTH_SHORT).show();
        }
    }

    FirebaseUser user;
    void sendmail() {

        user = firebaseAuth.getCurrentUser();
        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(RegisterActivity.this, "mail sent", Toast.LENGTH_SHORT).show();
            }

        });

    }

}
