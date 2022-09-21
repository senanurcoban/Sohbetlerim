package com.example.sohbetlerim.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sohbetlerim.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class GirisActivity extends AppCompatActivity {

    private EditText input_email_login,input_parola_login;
    private Button loginButton;
    private FirebaseAuth auth;
    private TextView hesapYok;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris);
        tanimla();
    }
    public void tanimla(){
        input_email_login=(EditText)findViewById(R.id.input_email_login);
        input_parola_login=(EditText)findViewById(R.id.input_parola_login);
        loginButton=(Button)findViewById(R.id.loginButton);
        hesapYok=(TextView)findViewById(R.id.hesapYok);
        auth=FirebaseAuth.getInstance();
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=input_email_login.getText().toString();
                String parola=input_parola_login.getText().toString();
                if(!email.equals("")&&!parola.equals("")){
                    sistemeGiris(email,parola);
                }else{
                    Toast.makeText(getApplicationContext(),"Boş Girilemez...",Toast.LENGTH_LONG).show();
                }
            }
        });
        hesapYok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(GirisActivity.this,KayitOlActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public void sistemeGiris(String email,String parola){
        auth.signInWithEmailAndPassword(email,parola).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent intent=new Intent(GirisActivity.this,MainActivity2.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),"Hatalı Bilgi Girdiniz...",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
