package com.example.yoshikawaakira.bigtask;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by yoshikawaakira on 2017/12/13.
 */

public class ContactAdapterList extends ArrayAdapter<ContactDetail>{
    private Activity activity;
    private int row;
    private java.util.List<ContactDetail> items;
    private ContactDetail contactDetail;


    public ContactAdapterList(Activity activity, int row, List<ContactDetail> items) {
        super(activity, row, items);
        this.activity = activity;
        this.row = row;
        this.items = items;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;

        if (view == null) {

            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(row, null);
            holder = new ViewHolder();
            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }
        if ((items == null) || ((position + 1) > items.size())) {
            return view;
        }
        contactDetail = items.get(position);

        holder.imageView = view.findViewById(R.id.image);
        holder.name = view.findViewById(R.id.name);


        if (holder.imageView != null && contactDetail.getPath() != null && contactDetail.getPath().trim().length()>0){
            Picasso.with(activity)
                    .load(contactDetail.getPath())
                    .resize(200,200)
                    .into(holder.imageView);
        }

        if (holder.name != null && contactDetail.getName() != null && contactDetail.getName().trim().length()>0){
            holder.name.setText(Html.fromHtml(contactDetail.getName()));
        }
        return view;
    }
    class ViewHolder{
        public TextView name;
        ImageView imageView;
    }
}

