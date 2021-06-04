package com.example.dogcattalk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private FirebaseAuth mfirebaseAuth;
    private Button Delte_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mfirebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        Button btn_logut = findViewById(R.id.logout);
        Delte_btn = findViewById(R.id.delteUser);

        Button testbtn = findViewById(R.id.test);

        btn_logut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //로그아웃 하기
                mfirebaseAuth.signOut();
                Toast.makeText(MainActivity.this,"로그아웃 되셨습니다",Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();

            }
        });
        
        Delte_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            
            //회원 탈퇴
            public void onClick(View v) {

                FirebaseUser user = mfirebaseAuth.getCurrentUser(); //현재 유저 정보를가져오는것
                String firebaseUid = mfirebaseAuth.getUid();
                user.delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    //Log.d(TAG, "User account deleted.");
                                    Toast.makeText(MainActivity.this,"이용해주셔서 감사합니다",Toast.LENGTH_SHORT).show();

                                    mDatabase.child("Users").child("UserAccount").child(firebaseUid).removeValue(); //데이터베이스에서 유저정보 삭제

                                    Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                                    startActivity(intent);
                                    finish();

                                }
                            }
                        });
                
            }
        });


        testbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = PreferenceManager.getString(getApplicationContext(),"id");
                Toast.makeText(MainActivity.this,a,Toast.LENGTH_SHORT).show();
            }
        });
    }
}




