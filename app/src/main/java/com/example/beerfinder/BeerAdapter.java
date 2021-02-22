package com.example.beerfinder;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class BeerAdapter extends RecyclerView.Adapter<BeerAdapter.ViewHolder> {

    private List<Beer> beers;
    private List<Beer> beersFilter;
    private List<Beer> selectedBeers;

    public BeerAdapter(List<Beer> beers) {
        this.beers = beers;
        this.selectedBeers = new ArrayList<>();
        this.beersFilter = new ArrayList<>();
        beersFilter.addAll(beers);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View beerView = inflater.inflate(R.layout.item_beer, parent, false);
        // return a new viewholder
        return new ViewHolder(beerView);
    }

    @Override
    public void onBindViewHolder(@NonNull BeerAdapter.ViewHolder holder, int position) {

        Beer beer = beers.get(position);
        holder.textView_name.setText(beer.getName());
        holder.textView_description.setText(beer.getDescription());
        Picasso.get().load(beer.getBeerUrl()).into(holder.imageView_beerUrl);
        if(selectedBeers.contains(beer)) {
            holder.imageView_favorite.setImageResource(R.drawable.star_yellow);
        }
        else {
            holder.imageView_favorite.setImageResource(R.drawable.star);
        }

    }

    public int getSize() {
        return beersFilter.size();
    }



    @Override
    public int getItemCount() {
        return beers.size();
    }

    public void filter(String filter) {
        beers.clear();
        filter = filter.toLowerCase();
        if(filter.isEmpty()) {
            beers.addAll(beersFilter);
        }
        else {
            for(Beer beer : beersFilter) {
                if(beer.getName().toLowerCase().contains(filter)
                        || beer.getDescription().toLowerCase().contains(filter) ) {
                    beers.add(beer);
                }
            }
            //Log.e("Beer Count", String.valueOf(beers.size()));
        }
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView_name;
        TextView textView_description;
        ImageView imageView_beerUrl;
        ImageView imageView_favorite;
        private final Context context;

        public ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            textView_name = itemView.findViewById(R.id.textView_name);
            textView_description = itemView.findViewById(R.id.textView_description);
            imageView_beerUrl  = itemView.findViewById(R.id.imageView_beer);
            imageView_favorite  = itemView.findViewById(R.id.imageView_star);

            imageView_favorite.setOnClickListener(v -> {
                int selected = getAdapterPosition();
                Beer selectedV = beers.get(selected);
                if(selectedBeers.contains(selectedV)) {
                    selectedBeers.remove(selectedV);
                }
                else {
                    selectedBeers.add(selectedV);
                }
                notifyDataSetChanged();
            });

            imageView_beerUrl.setOnClickListener(v -> {
                ArrayList<String> info = new ArrayList<>();
                int selected = getAdapterPosition();
                Beer selectedV = beers.get(selected);
                Intent intent = new Intent(context, FourthActivity.class);
                info.add(selectedV.getName());
                info.add(selectedV.getAbv() + "%");
                info.add(selectedV.getDate());
                info.add("Description: " + selectedV.getDescription());
                info.add("Food Pairings:" + selectedV.getFoodPairing().replaceAll("[\\[\\](){}]","")
                        .replaceAll("\""," "));
                info.add("Brewer Tips: " + selectedV.getBrewerTips());
                info.add(selectedV.getBeerUrl());
                intent.putStringArrayListExtra("info", info);
                context.startActivity(intent);
            });
        }
    }
}
