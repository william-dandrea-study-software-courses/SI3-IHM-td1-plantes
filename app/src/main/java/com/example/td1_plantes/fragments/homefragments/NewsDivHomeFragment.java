package com.example.td1_plantes.fragments.homefragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.td1_plantes.R;

/**
 * @author D'Andrea William
 */
public class NewsDivHomeFragment extends Fragment {



    public NewsDivHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_news_div_home, container, false);
    }


}