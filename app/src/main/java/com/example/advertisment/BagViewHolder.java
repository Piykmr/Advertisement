package com.example.advertisment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class BagViewHolder {
    ImageView image;
    TextView bagName;
    TextView price;
    BagViewHolder(View v)
    {
        image = v.findViewById(R.id.imageView2);
        bagName = v.findViewById(R.id.textView1);
        price=v.findViewById(R.id.textView2);
    }

}
