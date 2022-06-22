package com.example.maru.Model;

public class User {

    private int mId;
    private String mName, mPhoto, mPhone, mEMail;

    public User(int mId, String mName, String mPhoto, String mPhone, String mEMail) {
        this.mId = mId;
        this.mName = mName;
        this.mPhoto = mPhoto;
        this.mPhone = mPhone;
        this.mEMail = mEMail;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmPhoto() {
        return mPhoto;
    }

    public void setmPhoto(String mPhoto) {
        this.mPhoto = mPhoto;
    }

    public String getmPhone() {
        return mPhone;
    }

    public void setmPhone(String mPhone) {
        this.mPhone = mPhone;
    }

    public String getmEMail() {
        return mEMail;
    }

    public void setmEMail(String mEMail) {
        this.mEMail = mEMail;
    }
}
