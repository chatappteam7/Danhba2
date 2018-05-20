package com.example.vinhtruong.danhba;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by vinhtruong on 4/15/2018.
 */

public class DanhBaAdapter extends BaseAdapter {

    private MainActivity context;
    private int layout;
    private ArrayList<DanhBa> danhBaArrayList;

    public DanhBaAdapter(MainActivity context, int layout, ArrayList<DanhBa> danhBaArrayList) {
        this.context = context;
        this.layout = layout;
        this.danhBaArrayList = danhBaArrayList;
    }

    @Override
    public int getCount() {
        return danhBaArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class Viewholder{
        TextView txtTen, txtSdt;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Viewholder viewholder;
        if(view==null){
            viewholder=new Viewholder();
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_danh_ba,null);
            viewholder.txtTen=view.findViewById(R.id.txtTen);
            viewholder.txtSdt=view.findViewById(R.id.txtSdt);
            view.setTag(viewholder);
        }else{
            viewholder= (Viewholder) view.getTag();
        }

        DanhBa danhBa=danhBaArrayList.get(i);
        viewholder.txtTen.setText(danhBa.getTen());
        viewholder.txtSdt.setText(danhBa.getSdt());

        return view;
    }
}
