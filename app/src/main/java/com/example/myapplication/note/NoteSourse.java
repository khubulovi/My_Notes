package com.example.myapplication.note;

import android.content.res.Resources;
import android.icu.text.SimpleDateFormat;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.myapplication.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;

public class NoteSourse implements Parcelable,NoteSourceInteface {
    private ArrayList<Note> notes;
    private Resources resources;

    public NoteSourse(Resources resources) throws IOException {
        this.resources = resources;
        notes = new ArrayList<>();
    }


    public NoteSourse(Parcel in) {
        notes = in.createTypedArrayList(Note.CREATOR);
    }

    public static final Creator<NoteSourse> CREATOR = new Creator<NoteSourse>() {
        @Override
        public NoteSourse createFromParcel(Parcel in) {
            return new NoteSourse(in);
        }

        @Override
        public NoteSourse[] newArray(int size) {
            return new NoteSourse[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeTypedList(notes);
    }

    @Override
    public NoteSourceInteface init(NoteSourceResponce notesSourceResponse) {

        Note[] notesArray = new Note[]{
                new Note(resources.getString(R.string.first_note_title),
                        resources.getString(R.string.first_note_content),
                        getDateOfCreation()),
                new Note(resources.getString(R.string.second_note_title),
                        resources.getString(R.string.second_note_content),
                        getDateOfCreation()),
                new Note(resources.getString(R.string.third_note_title),
                        resources.getString(R.string.third_note_content),
                        getDateOfCreation()),
                new Note(resources.getString(R.string.four_note_content),
                        resources.getString(R.string.four_note_content),
                        getDateOfCreation()),
                new Note(resources.getString(R.string.five_note_title),
                        resources.getString(R.string.five_note_content),
                        getDateOfCreation()),
                new Note(resources.getString(R.string.six_note_title),
                        resources.getString(R.string.six_note_content),
                        getDateOfCreation()),
                new Note(resources.getString(R.string.seven_note_title),
                        resources.getString(R.string.seven_note_content),
                        getDateOfCreation()),
                new Note(resources.getString(R.string.eight_note_title),
                        resources.getString(R.string.eight_note_content),
                        getDateOfCreation()),
                new Note(resources.getString(R.string.nine_note_title),
                        resources.getString(R.string.nine_note_content),
                        getDateOfCreation()),
                new Note(resources.getString(R.string.ten_note_title),
                        resources.getString(R.string.ten_note_content),
                        getDateOfCreation()),
        };
        Collections.addAll(notes, notesArray);
        return this;
    }

    public Note getNote(int position) {
        return notes.get(position);
    }

    public int size() {
        return notes.size();
    }

    public boolean deleteNote(int position) {
        notes.remove(position);
        return false;
    }

    public void changeNote(int position, Note note) {
        notes.set(position, note);
    }

    public void addNote(Note note) {
        notes.add(note);
    }

    public String getDateOfCreation() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy",
                Locale.getDefault());
        return String.format("%s: %s", "Data of creation",
                formatter.format(Calendar.getInstance().getTime()));
    }
}
