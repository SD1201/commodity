package com.commodity.idroid.category;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.util.Log;

import com.commodity.idroid.R;
import com.commodity.idroid.brand.BrandNameModel;
import com.commodity.idroid.utils.ReadTXT;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CategoryActivity extends AppCompatActivity {

    private static final String TAG = "CategoryActivity";
    // 读取txt文件后的设备数据Json字符串
    private String categoryData;
    // 存放设备数据
    private ArrayList<CategoryModel.DeviceType> list = new ArrayList<>();
    // 构建线程池
    final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            3,
            5,
            1,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(20));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        categoryData = load();
        parseJson(categoryData);
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.category_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(gridLayoutManager);
        CategoryAdapter categoryAdapter = new CategoryAdapter(list);
        recyclerView.setAdapter(categoryAdapter);
    }

    /**
     * Json解析数据
     * @param categoryData
     */
    private void parseJson(String categoryData) {
        Gson gson = new Gson();
        final CategoryModel categoryModel = gson.fromJson(categoryData, CategoryModel.class);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (CategoryModel.DeviceType deviceType : categoryModel.getDOCUMENT().getDevice_type()) {
                    list.add(deviceType);
                }
            }
        };
        threadPoolExecutor.execute(runnable);
    }

    /**
     * 读取txt的设备种类数据
     * @return
     */
    private String load() {
        return ReadTXT.load(this,"IrDeviceType.txt");
    }
}
