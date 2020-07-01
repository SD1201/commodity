package com.commodity.idroid.category;

import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.commodity.idroid.R;
import com.commodity.idroid.brand.BrandActivity;

import java.io.Serializable;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private List<CategoryModel.DeviceType> mList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = (TextView)itemView.findViewById(R.id.category_view_item);
        }
    }

    public CategoryAdapter(List<CategoryModel.DeviceType> list){
        mList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_category_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                CategoryModel.DeviceType sendBrandDevice = mList.get(position);
                Intent intent = new Intent(parent.getContext(), BrandActivity.class);
                intent.putExtra("deviceTypeId", sendBrandDevice);
                parent.getContext().startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoryModel.DeviceType deviceType = mList.get(position);
        holder.categoryName.setText(deviceType.getName());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


}
