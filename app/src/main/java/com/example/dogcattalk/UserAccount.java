package com.example.dogcattalk;

/**
 * 사용자 계정 정보 모델 클래스
 */

public class UserAccount {

    private String emailID;   //이메일 아이디
    private String password; //비밀번호
    private String idToken; //Firebase UID (고유 토큰정보)
    private String gender; //성별
    private String birthday; //생일

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public UserAccount() {} //기본적으로 빈생성자를 만들어줘야함

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() { return gender; }

    public void setGender(String gender) { this.gender = gender; }

    public String getBirthday() { return birthday; }

    public void setBirthday(String birthday) { this.birthday = birthday; }

}


