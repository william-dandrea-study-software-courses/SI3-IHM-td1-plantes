package com.example.td1_plantes.controler.fragments.homefragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.td1_plantes.R;
import com.example.td1_plantes.controler.activities.utils.recyclerlists.NewsRecyclerAdapter;
import com.example.td1_plantes.model.appobjects.News;

/**
 * @author D'Andrea William
 */
public class NewsDivHomeFragment extends Fragment {

    String urlTwitter = "https://twitter.com/botaniquecomte?lang=fr" ;
    RecyclerView recyclerView;
    NewsRecyclerAdapter adapter;
    News[] elements;

    public NewsDivHomeFragment(News[] elements) {
        this.elements = elements;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewRoot = inflater.inflate(R.layout.fragment_news_div_home, container, false);

        viewRoot.findViewById(R.id.fragment_news_div_home_title_id).setOnLongClickListener(click ->{
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("News Twitter");
            builder.setMessage("Souhaitez vous regarder des news sur Twitter ?");
            builder.setNegativeButton("Non", null);
            builder.setPositiveButton("Oui",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            /*Toast toast = Toast.makeText(getContext(), "Ca marche",Toast.LENGTH_LONG);
                            toast.show();*/
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlTwitter));
                            startActivity(intent);

                        }
                    });

            AlertDialog dialog = builder.create();
            dialog.show();
            return true;
        } );
        return viewRoot;
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