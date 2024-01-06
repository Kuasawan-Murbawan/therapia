package com.husyairi.therapia;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class DoctorAdapter extends RecyclerView.Adapter<DocViewHolder> {

    private Context context;

    private List<DataClass> dataList;

    public DoctorAdapter(Context context, List<DataClass> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    public DocViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new DocViewHolder(view);
    }

    public void onBindViewHolder(@NonNull DocViewHolder holder, int position) {
        Glide.with(context).load(dataList.get(position).getDataImage()).into(holder.recImage);
        holder.recTreatment.setText(dataList.get(position).getDataTreatmentType());
        holder.recDate.setText(dataList.get(position).getDataDate());
        holder.recLocation.setText(dataList.get(position).getDataLocation());


        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DoctorDetailActivity.class);
                intent.putExtra("Image", dataList.get(holder.getAdapterPosition()).getDataImage());
                intent.putExtra("Date", dataList.get(holder.getAdapterPosition()).getDataDate());
                intent.putExtra("Treatment", dataList.get(holder.getAdapterPosition()).getDataTreatmentType());
                intent.putExtra("Description", dataList.get(holder.getAdapterPosition()).getDataDesc());
                intent.putExtra("Time", dataList.get(holder.getAdapterPosition()).getDataTime());
                intent.putExtra("Username", dataList.get(holder.getAdapterPosition()).getUsername());
                intent.putExtra("Location",dataList.get(holder.getAdapterPosition()).getDataLocation());
                intent.putExtra("Posting Date", dataList.get(holder.getAdapterPosition()).getPostingDate());
                intent.putExtra("hasAccept", dataList.get(holder.getAdapterPosition()).getJobAccepted());
                intent.putExtra("hasComplete", dataList.get(holder.getAdapterPosition()).getHasComplete());
                context.startActivity(intent);

            }
        });

    }
    public int getItemCount() {
        return dataList.size();
    }

}

    class DocViewHolder extends RecyclerView.ViewHolder {

        ImageView recImage;
        TextView recTreatment, recLocation, recDate; //recTime, recDesc;
        CardView recCard;

        // initialize date & time


        public DocViewHolder(@NonNull View itemView) {
            super(itemView);

            recImage = itemView.findViewById(R.id.recImage);
            recCard = itemView.findViewById(R.id.recCard);
            recTreatment = itemView.findViewById(R.id.recTreatment);
            recLocation = itemView.findViewById(R.id.recLocation);
            recDate = itemView.findViewById(R.id.recDate);
            // add date & time

        }


    }
