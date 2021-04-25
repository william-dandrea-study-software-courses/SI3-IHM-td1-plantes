package com.example.td1_plantes.controler.activities.utils.recyclerlists;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.td1_plantes.R;
import com.example.td1_plantes.controler.activities.plantpage.PlantPageActivity;
import com.example.td1_plantes.model.DownloadImageTask;
import com.example.td1_plantes.model.GestionDatabase;
import com.example.td1_plantes.model.appobjects.Plant;
import com.example.td1_plantes.model.appobjects.smallelements.Fiability;

import java.util.Arrays;

public class PlantRecyclerAdapter extends RecyclerView.Adapter<PlantRecyclerAdapter.ViewHolder> {

    String CALL_TO_ACTION = "Découvrir";

    Plant[] data;
    Context context;
    boolean forPrivateRepo;

    public PlantRecyclerAdapter(Plant[] data, Context context, boolean forPrivateRepo) {
        this.data = data;
        this.context = context;
        this.forPrivateRepo = forPrivateRepo;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.plant_list_design_one_element, parent, false);
        PlantRecyclerAdapter.ViewHolder viewHolder = new PlantRecyclerAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        new DownloadImageTask((ImageView) holder.image).execute(data[position].getImageURL());
        holder.title.setText(data[position].getTitle());
        holder.description.setText(data[position].getDescription());

        if (forPrivateRepo) {

            String pblcStr = (data[position].isPublic())?"< PUBLIC >": "< PRIVEE >";

            holder.describerTitle.setText(pblcStr);
        } else {
            String fiabilityString = "";

            if (GestionDatabase.getFiabilityForOnePlant(data[position].getIdPlant()) == Fiability.LOW) {
                fiabilityString += "Non-fiable";
                holder.describerTitle.setTextColor(Color.parseColor("#FF0000"));
            }
            if (GestionDatabase.getFiabilityForOnePlant(data[position].getIdPlant()) == Fiability.MEDIUM) {
                fiabilityString += "Peu-fiable";
                holder.describerTitle.setTextColor(Color.parseColor("#f58442"));
            }
            if (GestionDatabase.getFiabilityForOnePlant(data[position].getIdPlant()) == Fiability.HIGH) {
                fiabilityString += "Fiable";
                holder.describerTitle.setTextColor(Color.parseColor("#0f7025"));
            }

            holder.describerTitle.setText(fiabilityString);
        }

        // Position

        holder.position.setText(data[position].getPosition().getNameCity());

        // Mise en italique et bold du text
        SpannableString content = new SpannableString(CALL_TO_ACTION);
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        content.setSpan(new StyleSpan(Typeface.BOLD), 0, content.length(), 0);
        content.setSpan(new StyleSpan(Typeface.ITALIC), 0, content.length(), 0);
        holder.callToAction.setText(content);


        holder.mainElement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Cliqued on " + data[position] , Toast.LENGTH_SHORT).show();

                Intent i = new Intent(context, PlantPageActivity.class);
                i.putExtra("plantID", data[position].getIdPlant());
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.length;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {


        LinearLayout mainElement;
        ImageView image;
        TextView title;
        TextView describerTitle;
        TextView description;
        TextView position;
        TextView callToAction;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mainElement = itemView.findViewById(R.id.plant_list_design_one_element_main_id);
            image = itemView.findViewById(R.id.plant_list_design_one_element_image_id);
            title = itemView.findViewById(R.id.plant_list_design_one_element_main_title_id);
            describerTitle = itemView.findViewById(R.id.plant_list_design_one_element_describer_title_id);
            description = itemView.findViewById(R.id.plant_list_design_one_element_description_id);
            position = itemView.findViewById(R.id.plant_list_design_one_element_location_id);
            callToAction = itemView.findViewById(R.id.plant_list_design_one_element_call_to_action_id);
        }
    }
}
