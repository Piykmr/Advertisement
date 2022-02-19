package com.example.advertisment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    ArrayList<Model> dataholder = new ArrayList<>();

    public MyAdapter(ArrayList<Model> dataholder) {
        this.dataholder = dataholder;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.coname.setText(dataholder.get(position).getName());
        holder.coemail.setText(dataholder.get(position).getEmail());
        holder.coaddress.setText(dataholder.get(position).getAddress());
       // holder.cophone.setText(dataholder.get(position).getPhone());
        holder.design.setText(dataholder.get(position).getDesign());
        holder.imageView4.setImageResource(dataholder.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return dataholder.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView coname,coemail,coaddress,cophone,design;
        ImageView imageView4;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            coname=itemView.findViewById(R.id.coname);
            coemail=itemView.findViewById(R.id.coemail);
            coaddress=itemView.findViewById(R.id.coaddress);
            cophone=itemView.findViewById(R.id.cophone);
            imageView4=itemView.findViewById(R.id.imageView4);
            design=itemView.findViewById(R.id.design);
            //imageView4.setImageResource(BagAdapter.img);

        }


    }
}
