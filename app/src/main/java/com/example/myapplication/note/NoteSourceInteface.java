package com.example.myapplication.note;

public interface NoteSourceInteface {
    NoteSourceInteface init(NoteSourceResponce notesSourceResponse);
    Note getNote(int position);

    int size();

    boolean deleteNote(int position);

    void changeNote(int position, Note note);

    void addNote(Note note);
}
