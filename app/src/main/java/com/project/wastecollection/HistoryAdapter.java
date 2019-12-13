package com.project.wastecollection;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    ArrayList<String> dustbinName,time,location,date;

    public HistoryAdapter(ArrayList<String> dustbinName, ArrayList<String> date, ArrayList<String> location, ArrayList<String> time) {
        this.dustbinName=dustbinName;
        this.date=date;
        this.time=time;
        this.location=location;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.history_itemlist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    holder.dustbinName.setText("Dust Bin "+dustbinName.get(position));
    holder.location.setText("Located at: "+location.get(position));
    holder.date.setText(date.get(position));
    holder.time.setText(time.get(position));
    }

    @Override
    public int getItemCount() {
        return dustbinName.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView dustbinName,date,location,time;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dustbinName =(TextView) itemView.findViewById(R.id.dustbinName);
            date=(TextView) itemView.findViewById(R.id.date);
            location=(TextView) itemView.findViewById(R.id.location);
            time=(TextView) itemView.findViewById(R.id.time);
        }
    }
}
