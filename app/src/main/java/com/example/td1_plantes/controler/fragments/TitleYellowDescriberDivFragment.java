package com.example.td1_plantes.controler.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.td1_plantes.R;


public class TitleYellowDescriberDivFragment extends Fragment {

    String titleElement = "";

    /*
    public TitleYellowDescriberDivFragment() {
        // Required empty public constructor
    }

     */

    public TitleYellowDescriberDivFragment(String titleElement) {
        // Required empty public constructor
        this.titleElement = titleElement;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_title_yellow_describer_div, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView text = getView().findViewById(R.id.fragment_title_yellow_describer_div_textelement);
        text.setText(titleElement);
    }
}