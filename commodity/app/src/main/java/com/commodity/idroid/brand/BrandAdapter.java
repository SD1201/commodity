package com.commodity.idroid.brand;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.commodity.idroid.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BrandAdapter extends BaseAdapter {

    private ArrayList<BrandInfoModel> mlist;

    public ArrayList<BrandInfoModel> getMlist() {
        return mlist;
    }

    public void setMlist(ArrayList<BrandInfoModel> mlist) {
        this.mlist = mlist;
    }

    public BrandAdapter(ArrayList<BrandInfoModel> list) {
        mlist = list;
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int postion) {
        return mlist.get(postion);
    }

    @Override
    public long getItemId(int postion) {
        return postion;
    }

    @Override
    public View getView(int postion, View view, ViewGroup parent) {
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.brand_list_item, parent, false);
        TextView itemEnName = convertView.findViewById(R.id.item_en_name);
        TextView itemName = convertView.findViewById(R.id.item_name);
        TextView itemIndex = convertView.findViewById(R.id.item_index);
        itemEnName.setText(mlist.get(postion).getEn_name());
        itemName.setText(mlist.get(postion).getName());
        itemIndex.setText(mlist.get(postion).getIndex());
        if (postion != 0 && mlist.get(postion-1).getIndex().equals(mlist.get(postion).getIndex())){
            itemIndex.setVisibility(View.GONE);
        }
        return convertView;
    }
}
