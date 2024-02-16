package com.example.myapplication.note;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Note implements Parcelable {
    private String id;
    private String title;
    private String discription;
    private String calendarData;


    public Note(String title, String discription, String calendarData) {
        this.title = title;
        this.discription = discription;
        this.calendarData = calendarData;}


    protected Note(Parcel in) {
        title = in.readString();
        discription = in.readString();
        calendarData= in.readString();
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public String getTitle() {
        return title;
    }
    public String getDiscription() {
        return discription;
    }

    public String getCalendarData() {
        return calendarData;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(discription);
        dest.writeString(calendarData);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
