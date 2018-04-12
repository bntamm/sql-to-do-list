package com.example.tam.sqltodolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Tam on 1/26/2018.
 */

public class CongViecAdapter extends BaseAdapter{

    private MainActivity context;
    private int layout;
    private List<CongViec> congViecList;

    public CongViecAdapter(MainActivity context, int layout, List<CongViec> congViecList) {
        this.context = context;
        this.layout = layout;
        this.congViecList = congViecList;
    }

    @Override
    public int getCount() {
        return congViecList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder{
        TextView txtTen;
        ImageView imgDelete , imgEdit;
    }
    @Override
    public View getView(int i, View view, ViewGroup parent) {
        ViewHolder holder;

        if(view == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout , null);

            holder.txtTen       = (TextView) view.findViewById(R.id.tvTen);
            holder.imgDelete    = (ImageView) view.findViewById(R.id.imgDelete);
            holder.imgEdit      = (ImageView) view.findViewById(R.id.imgEdit);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();}


        final CongViec congViec = congViecList.get(i);
        holder.txtTen.setText(congViec.getTenCV());





        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.DialogEdit(congViec.getTenCV() , congViec.getIdCV());

            }
        });

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.DialogXoa(congViec.getTenCV() , congViec.getIdCV());
            }
        });
        return view;
    }
}
