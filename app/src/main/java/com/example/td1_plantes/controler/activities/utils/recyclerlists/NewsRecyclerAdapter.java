package com.example.td1_plantes.controler.activities.utils.recyclerlists;

import android.content.Context;
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
import com.example.td1_plantes.model.DownloadImageTask;
import com.example.td1_plantes.model.appobjects.News;

public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.ViewHolder> {

    News data[];
    Context context;

    String CALL_TO_ACTION = "< Découvrir >";

    public NewsRecyclerAdapter(News[] data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.news_list_design_one_element, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        new DownloadImageTask((ImageView) holder.image).execute(data[position].getImageURL());
        holder.title.setText(data[position].getTitle());
        holder.description.setText(data[position].getDescription());

        // Date

        holder.date.setText(data[position].getDate());


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
        TextView description;
        TextView date;
        TextView callToAction;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mainElement = itemView.findViewById(R.id.news_list_design_one_element_main_id);
            image = itemView.findViewById(R.id.news_list_design_one_element_image_id);
            title = itemView.findViewById(R.id.news_list_design_one_element_main_title_id);
            description = itemView.findViewById(R.id.news_list_design_one_element_description_id);
            date = itemView.findViewById(R.id.news_list_design_one_element_date_id);
            callToAction = itemView.findViewById(R.id.news_list_design_one_element_call_to_action_id);
        }
    }
}
