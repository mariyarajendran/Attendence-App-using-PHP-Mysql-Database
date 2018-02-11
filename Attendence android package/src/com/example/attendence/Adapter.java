package com.example.attendence;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Rajesh RR on 14-08-2016.
 */
public class Adapter extends BaseAdapter{

    Context context;
    ArrayList<Seat> seat;
    public Adapter(Context _context, ArrayList<Seat> Seat) {
        this.context = _context;
        this.seat = Seat;
    }

    @Override
    public int getCount() {
        return seat.size();
    }

    @Override
    public Object getItem(int position) {
        return seat.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        
        if(convertView == null){
        	convertView = layoutInflater.inflate(R.layout.format, parent , false);
        }
        
        ImageView img = (ImageView) convertView.findViewById(R.id.imageView1);
        TextView txt = (TextView) convertView.findViewById(R.id.textView1);
        
        img.setImageResource(seat.get(position).getImg());
        txt.setText(seat.get(position).getSeatno());
        return convertView;
    }
}
