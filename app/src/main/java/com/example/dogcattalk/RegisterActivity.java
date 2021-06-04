package com.example.dogcattalk;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth; //파이어베이스 인증
    private DatabaseReference mDatabaseRef; //실시간 데이터 베이스
    private EditText mEtEmail, mEtPad; //회원가입 입력필드
    private Button mBtnRegister; //회원가입 버튼
    private RadioGroup radio_gender;//성별 선택
    private EditText mbirthday;//생년월일
    private Button user_check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Users");

        mEtEmail = findViewById(R.id.et_email);
        mEtPad = findViewById(R.id.et_pwd);
        mBtnRegister = findViewById(R.id.btn_register);
        mbirthday = findViewById(R.id.user_birthday);

        radio_gender = findViewById(R.id.gender_check);
        radio_gender.clearCheck(); //라디오 버튼 초기화

        user_check = findViewById(R.id.id_check_btn);

        UserAccount account = new UserAccount();

        //남자 여자 체크
        radio_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.men_btn) {
                    account.setGender("남자");
                } else {
                    account.setGender("여자");
                }
            }
        });

        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 회원가입 처리 시작
                if (isValidEmail(mEtEmail.getText().toString())) {
                    signup(account);
                }
                else{
                    Toast.makeText(RegisterActivity.this, "이메일 주소를 확인해주세요", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    
    /*
    작업해야 할것 
    비밀번호 정규식, 비밀번호 확인체크
    생년월일 정규식 , 닉네임 체크 , 리얼타임파이어베이스 -> 데이터베이스
    
    
    
   
     */
    
    
    //이메일 체크 (정규식)
    public static boolean isValidEmail(String email) {
        boolean err = false;
        String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        if (m.matches()) {
            err = true;
        }
        return err;
    }

    public void signup (UserAccount account){

        String strEmail = mEtEmail.getText().toString(); //이메일 입력값 가져오기
        String strPwd = mEtPad.getText().toString(); //비밀번호
        String strBirthday = mbirthday.getText().toString();// 유저의 생년월일
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser(); //현재의 유저 정보를 가저온다

        mFirebaseAuth.createUserWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    account.setEmailID(firebaseUser.getEmail());
                    account.setPassword(strPwd); //사용자가입력했던내용을 가져옴
                    account.setBirthday(strBirthday);
                    account.setIdToken(firebaseUser.getUid());

                    mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).setValue(account);
                    Toast.makeText(RegisterActivity.this, "회원가입에 성공하셨습니다", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                    

                } else {
                    
                        Toast.makeText(RegisterActivity.this, "이미 가입된 계정입니다", Toast.LENGTH_SHORT).show();

                }

            }
        });



    }


}