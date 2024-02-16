package com.example.myapplication.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.navigation.Navigation;
import com.example.myapplication.note.Note;
import com.example.myapplication.observe.Publisher;
import com.google.android.material.button.MaterialButton;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class NoteFragment extends Fragment {
    public static final String CURRENT_NOTE = "currentNote";
    private Note note;
    private Navigation navigation;
    private Publisher publisher;
    private EditText titleText;
    private EditText contentText;
    private MaterialButton mButtonSave;
    private MaterialButton mButtonBack;
    private TextView dateOfCreationText;
    private String dateOfCreation;
    private boolean isNewNote = false;



    public static NoteFragment newInstance(){
        return new NoteFragment();
    }

    public static NoteFragment newInstance(Note note) {
        NoteFragment noteFragment = new NoteFragment();
        Bundle test = new Bundle();
        test.putParcelable(CURRENT_NOTE, note);
        noteFragment.setArguments(test);
        return noteFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            note=getArguments().getParcelable(CURRENT_NOTE);
        }
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity) context;
        publisher = activity.getPublisher();
        navigation=activity.getNavigation();
    }

    @Override
    public void onDetach() {
        publisher = null;
        super.onDetach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note, container, false);
        initView(view);
        if (note != null) {
            dateOfCreation = note.getCalendarData();
            populateView(view);
        }
        if (isNewNote) {
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy",
                    Locale.getDefault());
            dateOfCreation = String.format("%s: %s", "Data created",
                    formatter.format(Calendar.getInstance().getTime()));
            populateView(view);
        }
        setHasOptionsMenu(true);
        return view;
    }

    private Note collectNote() {
        String title = Objects.requireNonNull(this.titleText.getText()).toString();
        String content = Objects.requireNonNull(this.contentText.getText()).toString();
        if (isNewNote) {
            isNewNote = false;
        }
        if (note != null) {
            Note answer = new Note(title, content, dateOfCreation);
            answer.setId(note.getId());
            return answer;
        } else {
            return new Note(title, content, dateOfCreation);
        }
    }

    private void initView(View view) {
        titleText = view.findViewById(R.id.edit_title_id);
        contentText = view.findViewById(R.id.edit_description_id);
        dateOfCreationText = view.findViewById(R.id.note_date_of_creation);
        mButtonSave=view.findViewById(R.id.back_save);
        mButtonBack=view.findViewById(R.id.back_batton);
        mButtonSave.setOnClickListener(v -> {
            Note newNote = collectNote();
            if (publisher != null) {
                publisher.notifySingle(newNote);
            }
        });
        mButtonBack.setOnClickListener(v -> {
            navigation.addFragment(ListOfNoteFragment.newInstance(), false);
        });

    }

    private void populateView(View view) {
        if (isNewNote) {
            dateOfCreationText.setText(dateOfCreation);
        } else {
            dateOfCreationText.setText(note.getCalendarData());
            titleText.setText(note.getTitle());
            contentText.setText(note.getDiscription());
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        MenuItem addNote = menu.findItem(R.id.menu_add_note);
        MenuItem search = menu.findItem(R.id.menu_search);
        addNote.setVisible(false);
        search.setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }
}