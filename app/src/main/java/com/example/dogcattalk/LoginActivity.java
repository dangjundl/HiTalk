package com.example.dogcattalk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth; //파이어베이스 인증
    private DatabaseReference mDatabaseRef; //실시간 데이터 베이스
    private EditText Email_ID , pwd;
    private Button sign_in_btn;
    private TextView  sign_Up_btn;

    private Button  quick_sign_in_btn;
    private TextView id_Search;
    private TextView  pw_Search;
    private CheckBox  save_Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sign_in_btn = (Button)findViewById(R.id.sign_in_btn); //로그인버튼
        sign_Up_btn = (TextView)findViewById(R.id.sign_Up_btn); //회원가입버튼
        save_Login =(CheckBox) findViewById(R.id.stay_LoginCheckBox); //체크박스
        Email_ID = (EditText) findViewById(R.id.input_ID); //로그인입력
        pwd = (EditText)findViewById(R.id.input_PW); // 패스워드입력

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Chat");


        quick_sign_in_btn = (Button)findViewById(R.id.quick_sign_in_btn); //간편 회원가입버튼
        id_Search = (TextView)findViewById(R.id.id_Search); //id찾기
        pw_Search = (TextView)findViewById(R.id.pw_Search); //비밀번호 찾기

        boolean boo = PreferenceManager.getBoolean(getApplicationContext(),"check"); //로그인 정보 기억하기 체크 유무 확인
         if(boo){
             Email_ID.setText(PreferenceManager.getString(getApplicationContext(), "id"));
             pwd.setText(PreferenceManager.getString(getApplicationContext(), "pw"));
        save_Login.setChecked(true);
         }


        sign_in_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               String strEmail = Email_ID.getText().toString();
               String strPwd = pwd.getText().toString();


                if(strEmail.isEmpty()){
                    Toast.makeText(LoginActivity.this,"아이디를 입력해주세요!",Toast.LENGTH_SHORT).show();
                }
                else if(strPwd.isEmpty()){

                    Toast.makeText(LoginActivity.this,"비밀번호를 입력해주세요!",Toast.LENGTH_SHORT).show();
                }

                else{

                   PreferenceManager.setString(getApplicationContext(), "id", strEmail); //id라는 키값으로 저장
                   PreferenceManager.setString(getApplicationContext(), "pw", strPwd); //pw라는 키값으로 저장

                  
                    mFirebaseAuth.signInWithEmailAndPassword(strEmail,strPwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                //로그인 성공!!
                                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(LoginActivity.this,"로그인실패!",Toast.LENGTH_SHORT).show();

                            }

                        }
                    });

                }
            }
        });









        save_Login.setOnClickListener(new CheckBox.OnClickListener() {
            @Override public void onClick(View v) {
                if (((CheckBox)v).isChecked()) {
                PreferenceManager.setString(getApplicationContext(), "id", PreferenceManager.getString(getApplicationContext(),"id"));
                PreferenceManager.setString(getApplicationContext(), "pw",PreferenceManager.getString(getApplicationContext(),"pw"));
                PreferenceManager.setBoolean(getApplicationContext(), "check", save_Login.isChecked());
            } else {
                PreferenceManager.setBoolean(getApplicationContext(), "check", save_Login.isChecked());
                PreferenceManager.clear(getApplicationContext());
            }
            }
        }) ;






        quick_sign_in_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //로그인 요청

//                Toast myToast = Toast.makeText(getApplicationContext(),"간편로그인 진행", Toast.LENGTH_SHORT);
//                myToast.show();
            }
        });


        id_Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast myToast = Toast.makeText(getApplicationContext(),"아이디 찾기", Toast.LENGTH_SHORT);
                myToast.show();
            }
        });


        pw_Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast myToast = Toast.makeText(getApplicationContext(),"비밀번호 찾기", Toast.LENGTH_SHORT);
                myToast.show();
            }
        });

        sign_Up_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //회원가입화면으로 이동

                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);

//                Toast myToast = Toast.makeText(getApplicationContext(),"가입 진행", Toast.LENGTH_SHORT);
//                myToast.show();
            }
        });


    }
}