package com.example.myapplication.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.navigation.Navigation;
import com.example.myapplication.observe.Publisher;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import javax.annotation.Nullable;

public class StartFragment extends Fragment {

    private static final int RC_SIGN_IN = 40404;
    private static final String TAG = "GoogleAuth";
    public Navigation navigation;
    public Publisher publisher;
    public GoogleSignInClient googleSignInClient;

    public TextInputEditText editText;
    public MaterialButton continueBtn;
    public MaterialButton skipBtn;
    public SignInButton signInButton;


    public static StartFragment newInstance() {
       return new StartFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity) context;
        navigation = activity.getNavigation();
        publisher = activity.getPublisher();
    }

    @Override
    public void onDetach() {
        navigation = null;
        publisher = null;
        super.onDetach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_start, container, false);
        googleSignIn();
        initialView(view);
        enableSign();
        return view;
    }

    private void googleSignIn(){
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleSignInClient= GoogleSignIn.getClient(requireContext(),googleSignInOptions);
    }

    private void initialView(View view){
        editText = view.findViewById(R.id.typeEmail);
        signInButton= view.findViewById(R.id.signInButton);
        continueBtn=view.findViewById(R.id.buttonContinue);
        skipBtn=view.findViewById(R.id.buttonSkip);
        signInButton.setOnClickListener(v -> {signIn();});
        continueBtn.setOnClickListener(v -> {
            navigation.addFragment(ListOfNoteFragment.newInstance(),false);
        });
        skipBtn.setOnClickListener(v -> {
            navigation.addFragment(ListOfNoteFragment.newInstance(),false);
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(requireContext());
        if (account != null) {
            disableSign();
            updateUI(account.getEmail());
        }
    }

    private void signIn() {
        startActivityForResult(googleSignInClient.getSignInIntent(), RC_SIGN_IN);
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            handleSignInResult(GoogleSignIn.getSignedInAccountFromIntent(data));
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            disableSign();
            updateUI(account.getEmail());
        } catch (ApiException e) {
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
        }
    }

    private void updateUI(String email) {
        editText.setText(email);
    }

    private void enableSign() {
        signInButton.setEnabled(true);
        continueBtn.setEnabled(true);
    }

    private void disableSign() {
        signInButton.setEnabled(false);
        continueBtn.setEnabled(true);
    }
}
