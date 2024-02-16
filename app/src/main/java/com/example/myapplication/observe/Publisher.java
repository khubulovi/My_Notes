package com.example.myapplication.observe;

import com.example.myapplication.note.Note;

import java.util.ArrayList;
import java.util.List;

public class Publisher {
    private List<Observer> observerList;

    public Publisher() {
        this.observerList = new ArrayList<>();
    }

    public void subscribe(Observer observer) {
        observerList.add(observer);
    }

    public void unSubscribe(Observer observer) {
        observerList.remove(observer);
    }

    public void notifySingle(Note note) {
        for (Observer observer : observerList) {
            observer.update(note);
            unSubscribe(observer);
        }
    }
}
