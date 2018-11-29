package com.mao.engage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class SectionAdapter extends RecyclerView.Adapter<SectionAdapter.SectionViewHolder> {
    ArrayList<SectionSesh> data;
    private Context context;

    SectionAdapter(Context context, ArrayList<SectionSesh> data) {
        this.data = data;
        Log.d("BOBOBO ARRAY", "SectionAdapter: " + data.toString() + data.size());
        this.context = context;
    }

    @NonNull
    @Override
    public SectionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.adapter_view, viewGroup,false);
        return new SectionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SectionViewHolder PokemonViewHolder, int position) {
        PokemonViewHolder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class SectionViewHolder extends RecyclerView.ViewHolder {
        Button button;
        SectionSesh section;

        public SectionViewHolder(@NonNull View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Coming soon", Toast.LENGTH_SHORT).show();
                }
            });
        }

        void bind (SectionSesh section) {
            this.section = section;
            button.setText(section.getSection_id());
        }
    }
}
