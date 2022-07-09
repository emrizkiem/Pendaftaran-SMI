package com.emrizkiem.registersmi.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class Users implements Parcelable {
    /**
     * This method for create table in database and create column info
     */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB, name = "image_profile")
    private byte[] imageProfile;

    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name = "username")
    private String username;

    @ColumnInfo(name = "password")
    private String password;

    @ColumnInfo(name = "date_birthday")
    private String dateBirthday;

    @ColumnInfo(name = "gpa")
    private Double gpa;

    @ColumnInfo(name = "address")
    private String address;

    @ColumnInfo(name = "motivation_letter")
    private String motivationLetter;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getImageProfile() {
        return imageProfile;
    }

    public void setImageProfile(byte[] imageProfile) {
        this.imageProfile = imageProfile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDateBirthday() {
        return dateBirthday;
    }

    public void setDateBirthday(String dateBirthday) {
        this.dateBirthday = dateBirthday;
    }

    public Double getGpa() {
        return gpa;
    }

    public void setGpa(Double gpa) {
        this.gpa = gpa;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMotivationLetter() {
        return motivationLetter;
    }

    public void setMotivationLetter(String motivationLetter) {
        this.motivationLetter = motivationLetter;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.id);
        parcel.writeByteArray(this.imageProfile);
        parcel.writeString(this.email);
        parcel.writeString(this.username);
        parcel.writeString(this.password);
        parcel.writeString(this.dateBirthday);
        parcel.writeDouble(this.gpa);
        parcel.writeString(this.address);
        parcel.writeString(this.motivationLetter);
    }

    @Ignore
    public Users() {}

    public Users(byte[] imageProfile, String email, String username, String password, String dateBirthday, Double gpa, String address, String motivationLetter) {
        this.imageProfile = imageProfile;
        this.email = email;
        this.username = username;
        this.password = password;
        this.dateBirthday = dateBirthday;
        this.gpa = gpa;
        this.address = address;
        this.motivationLetter = motivationLetter;
    }

    private Users(Parcel in) {
        this.id = in.readInt();
        this.imageProfile = in.createByteArray();
        this.email = in.readString();
        this.username = in.readString();
        this.password = in.readString();
        this.dateBirthday = in.readString();
        this.gpa = in.readDouble();
        this.address = in.readString();
        this.motivationLetter = in.readString();
    }

    public static final Parcelable.Creator<Users> CREATOR = new Parcelable.Creator<Users>() {
        @Override
        public Users createFromParcel(Parcel parcel) {
            return new Users(parcel);
        }

        @Override
        public Users[] newArray(int i) {
            return new Users[i];
        }
    };
}
