package com.example.myapplication.observe;

import com.example.myapplication.note.Note;

import java.util.ArrayList;
import java.util.List;

public class Publisher {
    private List<Observer> observerList;

    public Publisher() {
        this.observerList = new ArrayList<>();
    }

    public synchronized void subscribe(Observer observer) {
        observerList.add(observer);
    }

    public synchronized void unSubscribe(Observer observer) {
        observerList.remove(observer);
    }

    public synchronized void notifySingle(Note note) {
        List<Observer> observersToUnsoscrube = new ArrayList<>();
        for (Observer observer : observerList) {
            observer.update(note);
        }
        for(Observer observer :observersToUnsoscrube ){
            unSubscribe(observer);
        }
    }
}
