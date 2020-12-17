package com.amworks.pokedata;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CustomRecyclerAdapter extends RecyclerView.Adapter<CustomRecyclerAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<PokemonData> list;
    RecyclerView mRecyclerView;

    private final View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(final View view) {

            int itemPosition = mRecyclerView.getChildLayoutPosition(view);
            PokemonData item = list.get(itemPosition);
            Intent intent=new Intent(context,PokemonDeatil.class);

            String name=item.getName();
            int height=item.getHeight();
            int id=item.getId();
            String type=item.getType();
            String imageURL=item.getImageURL();
            int weight=item.getWeight();
            intent.putExtra("id",id);
            intent.putExtra("weight",weight);
            intent.putExtra("height",height);
            intent.putExtra("imageURL",imageURL);
            intent.putExtra("name",name);
            intent.putExtra("type",type);

            context.startActivity(intent);

        }
    };

    public CustomRecyclerAdapter(Context context,ArrayList<PokemonData> list,RecyclerView recyclerView) {
        this.context = context;
        this.list=list;
        mRecyclerView=recyclerView;
    }

    @NonNull
    @Override
    public CustomRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_layout, parent,false);
        view.setOnClickListener(onClickListener);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CustomRecyclerAdapter.MyViewHolder holder, int position) {

        holder.id.setText("#"+String.valueOf(list.get(position).getId()));
        holder.name.setText(list.get(position).getName());
        holder.type.setText(list.get(position).getType());

        //Using Glide Library for setting image
        Glide.with(context).load(list.get(position).getImageURL()).into(holder.image);

    }

    @Override
    public int getItemCount() {
//        Log.d("poke_data_success","size="+list.size());   //checking size
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        //all views in layout for a single row of RecyclerView
        TextView id;
        TextView name;
        TextView type;
        ImageView image;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            this.image=itemView.findViewById(R.id.image);
            this.id = itemView.findViewById(R.id.ID);
            this.name = itemView.findViewById(R.id.name);
            this.type = itemView.findViewById(R.id.type);

        }

    }
}
