package com.example.sohbetlerim.Activity;

import android.content.Intent;
import android.os.Bundle;

import com.example.sohbetlerim.Fragments.AnaSayfaFragment;
import com.example.sohbetlerim.Fragments.BildirimFragment;
import com.example.sohbetlerim.Fragments.KullaniciProfilFragment;
import com.example.sohbetlerim.R;
import com.example.sohbetlerim.Utils.ChangeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity2 extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseUser user;
    private ChangeFragment changeFragment;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            =(item)-> {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                changeFragment.change(new AnaSayfaFragment());
                return true;
            case R.id.navigation_dashboard:
                changeFragment.change(new BildirimFragment());
                return true;
            case R.id.navigation_profil:
                changeFragment.change(new KullaniciProfilFragment());
                return true;
            case R.id.navigation_exit:
                cık();
                return true;
        }
        return false;

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        tanimla();
        kontrol();
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        changeFragment=new ChangeFragment(MainActivity2.this);
        changeFragment.change(new AnaSayfaFragment());
    }
    public void cık(){
       auth.signOut();
        Intent intent = new Intent(MainActivity2.this, GirisActivity.class);
        startActivity(intent);
        finish();
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference reference=firebaseDatabase.getReference().child("KullaniciBilgileri");
        reference.child(user.getUid()).child("state").setValue(false);
    }
    public void tanimla(){
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
    }

    @Override
    protected void onStop() {
        super.onStop();
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference reference=firebaseDatabase.getReference().child("KullaniciBilgileri");
        reference.child(user.getUid()).child("state").setValue(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference reference=firebaseDatabase.getReference().child("KullaniciBilgileri");
        reference.child(user.getUid()).child("state").setValue(true);
    }

    public void kontrol() {
        if (user == null) {
            Intent intent = new Intent(MainActivity2.this, GirisActivity.class);
            startActivity(intent);
            finish();
        }else{
            FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
            DatabaseReference reference=firebaseDatabase.getReference().child("KullaniciBilgileri");
            reference.child(user.getUid()).child("state").setValue(true);
        }
    }
}