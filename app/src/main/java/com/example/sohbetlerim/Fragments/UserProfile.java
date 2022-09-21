package com.example.sohbetlerim.Fragments;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sohbetlerim.Activity.ChatActivity;
import com.example.sohbetlerim.Models.KullaniciBilgileri;
import com.example.sohbetlerim.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfile extends Fragment {

    View view;
    String userId, otherId;
    TextView userProfileNameText, userProfileEgitimText, userProfileDogumText, userProfileHakkımdaText, userProfileTakipciText, userProfileArkadasText, userProfileNameText2;
    ImageView userProfileArkadasImage, userProfileMesajImage, userProfileTakipImage;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference, reference_Arkadaslik;
    CircleImageView userProfileProfileImage;
    FirebaseAuth auth;
    FirebaseUser user;
    String kontrol = "", begeniKontrol = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        tanimla();
        action();
        getBegeniText();
        getArkadasText();
        return view;
    }

    public void tanimla() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference();
        reference_Arkadaslik = firebaseDatabase.getReference().child("Arkadaslik_Istek");
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        userId = user.getUid();
        otherId = getArguments().getString("userid");
        userProfileNameText = (TextView) view.findViewById(R.id.userProfileNameText);
        userProfileEgitimText = (TextView) view.findViewById(R.id.userProfileEgitimText);
        userProfileDogumText = (TextView) view.findViewById(R.id.userProfileDogumText);
        userProfileHakkımdaText = (TextView) view.findViewById(R.id.userProfileHakkımdaText);
        userProfileTakipciText = (TextView) view.findViewById(R.id.userProfileTakipciText);
        userProfileArkadasText = (TextView) view.findViewById(R.id.userProfileArkadasText);
        userProfileNameText2 = (TextView) view.findViewById(R.id.userProfileNameText2);
        userProfileArkadasImage = (ImageView) view.findViewById(R.id.userProfileArkadasImage);
        userProfileMesajImage = (ImageView) view.findViewById(R.id.userProfileMesajImage);
        userProfileTakipImage = (ImageView) view.findViewById(R.id.userProfileTakipImage);
        userProfileProfileImage = (CircleImageView) view.findViewById(R.id.userProfileProfileImage);

        reference_Arkadaslik.child(otherId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(userId)) {
                    kontrol = "istek";
                    kontrol = snapshot.child(userId).child("tip").getValue().toString();
                    userProfileArkadasImage.setImageResource(R.drawable.arkadas_ekle_off);
                } else {
                    userProfileArkadasImage.setImageResource(R.drawable.arkadas_ekle_on);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        reference.child("Arkadaslar").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(otherId)) {
                    kontrol = "arkadas";
                    userProfileArkadasImage.setImageResource(R.drawable.deleting_user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        reference.child("Begeniler").child(otherId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(userId)) {
                    begeniKontrol = "begendi";
                    userProfileTakipImage.setImageResource(R.drawable.takip_ok);
                } else {
                    userProfileTakipImage.setImageResource(R.drawable.takip_off);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void action() {
        reference.child("Kullanicilar").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                KullaniciBilgileri kl = snapshot.getValue(KullaniciBilgileri.class);
                userProfileNameText.setText("İsim:" + kl.getIsim());
                userProfileEgitimText.setText("Eğitim:" + kl.getEgitim());
                userProfileDogumText.setText("Doğum Tarihi:" + kl.getDogumtarih());
                userProfileHakkımdaText.setText("Hakkımda:" + kl.getHakkimda());
                userProfileNameText2.setText(kl.getIsim());
                if (!kl.getResim().equals("null")) {
                    Picasso.get().load(kl.getResim()).into(userProfileProfileImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        userProfileArkadasImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (kontrol.equals("istek")) {
                    arkadasIptalEt(otherId, userId);
                } else if (kontrol.equals("arkadas")) {
                    arkadasTablosundanCıkar(otherId, userId);
                } else {
                    arkadasEkle(otherId, userId);
                }
            }
        });
        userProfileTakipImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (begeniKontrol.equals("begendi")) {
                    begeniIptal(userId, otherId);
                } else {
                    begen(userId, otherId);
                }
            }
        });
        userProfileMesajImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), ChatActivity.class);
                intent.putExtra("userName",userProfileNameText2.getText().toString());
                intent.putExtra("id",otherId);
                startActivity(intent);
            }
        });
    }

    private void begeniIptal(String userId, String otherId) {
        reference.child("Begeniler").child(otherId).child(userId).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                userProfileTakipImage.setImageResource(R.drawable.takip_off);
                begeniKontrol = "";
                Toast.makeText(getContext(), "Beğenme Iptal Edildi", Toast.LENGTH_LONG).show();
                getBegeniText();
            }
        });
    }

    private void arkadasTablosundanCıkar(final String otherId, final String userId) {
        reference.child("Arkadaslar").child(otherId).child(userId).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                reference.child("Arkadaslar").child(userId).child(otherId).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        kontrol = "";
                        userProfileArkadasImage.setImageResource(R.drawable.arkadas_ekle_on);
                        Toast.makeText(getContext(), "Arkadaşlıktan çıkarıldı", Toast.LENGTH_LONG).show();
                        getArkadasText();
                    }
                });
            }
        });

    }


    public void arkadasEkle(String otherId, String userId) {
        reference_Arkadaslik.child(userId).child(otherId).child("tip").setValue("gonderdi").
                addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (!task.isSuccessful()) {
                            reference_Arkadaslik.child(otherId).child(userId).child("tip").setValue("aldi").
                                    addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                kontrol = "istek";
                                                Toast.makeText(getContext(), "Arkadaşlık İsteği Başarıyla Gönderildi.", Toast.LENGTH_LONG).show();
                                                userProfileArkadasImage.setImageResource(R.drawable.arkadas_ekle_off);

                                            } else {
                                                Toast.makeText(getContext(), "Bir problem meydana geldi", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                        } else {
                            Toast.makeText(getContext(), "Bir problem meydana geldi", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void arkadasIptalEt(String otherId, String userId) {
        reference_Arkadaslik.child(otherId).child(userId).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                reference_Arkadaslik.child(userId).child(otherId).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        kontrol = "";
                        userProfileArkadasImage.setImageResource(R.drawable.arkadas_ekle_on);  // Resmi değiştirdik.
                        Toast.makeText(getContext(), "Arkadaşlık İsteği İptal Edildi.", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    public void begen(String userId, String otherId) {
        reference.child("Begeniler").child(otherId).child(userId).child("tip").setValue("begendi").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getContext(), "Profili Beğendiniz", Toast.LENGTH_LONG).show();
                    userProfileTakipImage.setImageResource(R.drawable.takip_ok);
                    begeniKontrol = "begendi";
                    getBegeniText();
                }
            }
        });
    }

    public void getBegeniText() {

        reference.child("Begeniler").child(otherId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userProfileTakipciText.setText(snapshot.getChildrenCount()+"Beğeni");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void getArkadasText() {

        reference.child("Arkadaslar").child(otherId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userProfileArkadasText.setText(snapshot.getChildrenCount()+"Arkadaş");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
