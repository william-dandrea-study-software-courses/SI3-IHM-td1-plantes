package com.example.td1_plantes.controler.fragments.homefragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.td1_plantes.R;
import com.example.td1_plantes.controler.activities.utils.recyclerlists.NewsRecyclerAdapter;
import com.example.td1_plantes.model.appobjects.News;

/**
 * @author D'Andrea William
 */
public class NewsDivHomeFragment extends Fragment {


    RecyclerView recyclerView;
    NewsRecyclerAdapter adapter;
    News[] elements;

    public NewsDivHomeFragment(News[] elements) {
        this.elements = elements;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news_div_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (RecyclerView) getView().findViewById(R.id.fragment_news_div_home_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new NewsRecyclerAdapter(elements, getActivity());
        recyclerView.setAdapter(adapter);

    }
}