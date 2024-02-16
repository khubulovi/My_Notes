package com.example.myapplication.navigation;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.R;

public class Navigation {

    private final FragmentManager fragmetManager;


    public Navigation(FragmentManager fragmentManager) {
        this.fragmetManager=fragmentManager;
    }


    public void addFragment(Fragment fragment,boolean useBackStack){
        FragmentTransaction fragmentTransaction=fragmetManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,fragment);
        if(useBackStack){
            fragmentTransaction.addToBackStack("");
        }
        fragmentTransaction.commit();
    }
}
