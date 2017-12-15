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
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by yoshikawaakira on 2017/12/14.
 */

public class ImageAdapter extends ArrayAdapter<ContactDetailImg> {
    Context context;
    List<ContactDetailImg> list;
    int row;
    private ContactDetailImg contactDetail;

    public ImageAdapter(Context a, int row, List<ContactDetailImg> list){
        super(a,row,list);
        context = a;
        this.row = row;
        this.list = list;

    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        ImageAdapter.ViewHolder holder;

        if (view == null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(row, null);
            holder = new ViewHolder();
            view.setTag(holder);

        } else {
            holder = (ImageAdapter.ViewHolder) view.getTag();
        }
        if ((list == null) || ((position + 1) > list.size())) {
            return view;
        }
        contactDetail = list.get(position);

        holder.imageView = view.findViewById(R.id.img);



        if (holder.imageView != null && contactDetail.getImgpath() != null && contactDetail.getImgpath().trim().length()>0){
            Picasso.with(context)
                    .load(contactDetail.getImgpath())
                    .resize(200,200)
                    .into(holder.imageView);
        }


        return view;
    }
    class ViewHolder{
        ImageView imageView;
    }
}






