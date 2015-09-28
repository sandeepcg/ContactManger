package com.sample.crud.contactmanger.adapters;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sample.crud.contactmanger.R;
import com.sample.crud.contactmanger.listener.OnRecycleItemClick;
import com.sample.crud.contactmanger.listener.RecycleItemListener;
import com.sample.crud.contactmanger.models.ContactModel;
import com.sample.crud.contactmanger.viewholder.ContactListViewHolder;

public class ContactListAdapter extends /*BaseAdapter*/RecyclerView.Adapter<ContactListViewHolder> {
	Context c;
	ArrayList<ContactModel> Data;
	RecycleItemListener itemClick;

	public ContactListAdapter(Activity activity,
							  ArrayList<ContactModel> listData, RecycleItemListener ItemClick) {
		this.c = activity;
		this.Data = listData;
		this.itemClick=ItemClick;
	}

	/*@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return Data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return Data.get(position);
	}*/

	@Override
	public ContactListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contactlist_item, parent,false);

		ContactListViewHolder viewHolder = new ContactListViewHolder(view);
		return viewHolder;
	}

	@Override
	public void onBindViewHolder(ContactListViewHolder holder, final int position) {
		holder.name.setText(Data.get(position).getcontactName());
		holder.email.setText(Data.get(position).getcontactEmail());
		holder.num.setText(Data.get(position).getcreatedNo());
		holder.setClickListener(new OnRecycleItemClick() {
			@Override
			public void onClick(View view, int position) {
				/*Log.e("Postion","...."+position);*/
				itemClick.itemClicked(position);
			}
		});

	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getItemCount() {
		return  (null != Data ? Data.size() : 0);
	}

	/*@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;

		LayoutInflater mInflater = (LayoutInflater) c
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.contactlist_item, null);
			holder = new ViewHolder();
			holder.name = (TextView) convertView.findViewById(R.id.name);
			holder.number = (TextView) convertView.findViewById(R.id.number);
			holder.email = (TextView) convertView.findViewById(R.id.email);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.name.setText(Data.get(position).getcontactName());
		holder.number.setText(Data.get(position).getcreatedNo());
		holder.email.setText(Data.get(position).getcontactEmail());

		return convertView;
	}

	private class ViewHolder {
		TextView name;
		TextView number;
		TextView email;
	}*/

}
