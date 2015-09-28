package com.sample.crud.contactmanger.viewholder;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.sample.crud.contactmanger.R;
import com.sample.crud.contactmanger.listener.OnRecycleItemClick;

import static android.support.v7.widget.RecyclerView.*;

/**
 * Created by Sandeep on 9/25/2015.
 */
public class ContactListViewHolder extends RecyclerView.ViewHolder implements OnClickListener {
    public TextView name;
    public TextView num;
    public TextView email;
    private OnRecycleItemClick clickListener;

    public ContactListViewHolder(View itemView) {
        super(itemView);
        this.name = (TextView) itemView.findViewById(R.id.name);
        this.num = (TextView) itemView.findViewById(R.id.number);
        this.email = (TextView) itemView.findViewById(R.id.email);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //Log.e("getAdapterPosition()", "...." + getAdapterPosition());
        clickListener.onClick(v, getAdapterPosition());
    }

    public void setClickListener(OnRecycleItemClick itemClickListener) {
        this.clickListener = itemClickListener;
    }
}
