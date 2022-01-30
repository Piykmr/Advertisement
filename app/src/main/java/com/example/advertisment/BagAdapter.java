package com.example.advertisment;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

public class BagAdapter extends ArrayAdapter<String> {
    public static String bgname,bgprice;
    public static int img;


    Context context;
    int[] image;
    String[] bagName;
    String[] price;
    public BagAdapter(Context context, String[] bagName, int[] image, String[] price) {
        super(context, R.layout.single_item,R.id.textView1,bagName);
        this.context=context;
        this.image=image;
        this.bagName=bagName;
        this.price=price;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View singleItem = convertView;
        BagViewHolder holder = null;
        if(singleItem == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            singleItem = layoutInflater.inflate(R.layout.single_item,parent,false);
            holder = new BagViewHolder(singleItem);
            singleItem.setTag(holder);
        }
        else{
            holder = (BagViewHolder) singleItem.getTag();
        }
        holder.image.setImageResource(image[position]);
        holder.bagName.setText(bagName[position]);
        holder.price.setText(price[position]);
        singleItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bgname=bagName[position];
                bgprice=price[position];
                img=image[position];


                Intent intent = new Intent(getContext(),MainActivity2.class);
                intent.putExtra("bagName",bgname);
                intent.putExtra("bagPrice",bgprice);
                intent.putExtra("bagImage",img);
                context.startActivity(intent);
            }
        });
        return singleItem;
    }
}