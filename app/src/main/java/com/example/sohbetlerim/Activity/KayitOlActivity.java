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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class KayitOlActivity extends AppCompatActivity {

    EditText input_email,input_parola;
    Button registerButton;
    FirebaseAuth auth;
    TextView hesapvarText;
    FirebaseDatabase firebasedatabase;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit_ol);
        tanimla();
    }
    public void tanimla(){
        input_email=findViewById(R.id.input_email);
        input_parola=findViewById(R.id.input_parola);
        registerButton=findViewById(R.id.registerButton);
        hesapvarText=(TextView)findViewById(R.id.hesapvarText);
        auth=FirebaseAuth.getInstance();
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=input_email.getText().toString();
                String pass=input_parola.getText().toString();
                if(!email.equals("")&&!pass.equals("")){
                    input_email.setText("");
                    input_parola.setText("");
                    kayitOl(email,pass);
                }else{
                    Toast.makeText(getApplicationContext(),"Bilgileri boş giremezsiniz...",Toast.LENGTH_LONG).show();
                }
            }
        });
        hesapvarText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(KayitOlActivity.this, GirisActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public void kayitOl(String email,String pass){
        auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    firebasedatabase=FirebaseDatabase.getInstance();
                    reference=firebasedatabase.getReference().child("KullaniciBilgileri").child(auth.getUid());
                    Map map=new HashMap();
                    map.put("resim","null");
                    map.put("isim","null");
                    map.put("egitim","null");
                    map.put("dogumtarih","null");
                    map.put("hakkimda","null");
                    reference.setValue(map);



                    Intent intent=new Intent(KayitOlActivity.this, MainActivity2.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),"Kayıt olma esnasında bir problem oluştu",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
