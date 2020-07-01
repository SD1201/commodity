package com.commodity.idroid.brand;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import android.widget.Toast;

import com.commodity.idroid.R;
import com.commodity.idroid.activityUtils.SideBar;
import com.commodity.idroid.category.CategoryModel;
import com.commodity.idroid.utils.ReadTXT;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class BrandActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "BrandActivity";
    // 品牌的数据
    private List<BrandModel.Brand> brandList = new ArrayList<>();
    // 品牌名字的数据
    private List<BrandNameModel.BrandName> brandNameList = new ArrayList<>();
    // 存放整合品牌与品牌名字的数据
    private List<BrandInfoModel> brandInfoModelList = new ArrayList<>();
    // 整合重复品牌，把同一种品牌不同型号结合起来
    private HashMap<String, BrandInfoModel> brandInfoMap = new HashMap<>();
    // 传递给ListView的最终数据
    private ArrayList<BrandInfoModel> combineList = new ArrayList<>();
    // 搜索时候的数据
    private ArrayList<BrandInfoModel> searchList = new ArrayList<>();
    // 读取txt文件品牌的Json字符串
    private String brand;
    // 读取txt文件品牌名字的Json字符串
    private String brandNameInfo;
    // 引入控件
    private ListView listView;
    private SearchView searchView;
    private SideBar sideBar;
    private BrandAdapter adapter;
    private ImageView imageView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand);
        initView();
        CategoryModel.DeviceType receiveDevice = (CategoryModel.DeviceType) getIntent().getSerializableExtra("deviceTypeId");
        String deviceId = receiveDevice.getId();
        String brand = load();
        brandNameInfo = loadInfo();
        parseJsonAndSelectBrand(brand, deviceId);
        parseJson(brandNameInfo);
        combine(brandList, brandNameList);
        adapter = new BrandAdapter(combineList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                BrandInfoModel brandInfoModel = combineList.get(position);
                int size = brandInfoModel.getRemoteId().size();
                showModel(brandInfoModel.getRemoteId().toArray(new String[size]), brandInfoModel.getEn_name());
            }
        });
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // 当输入栏不为空的时候，修改显示数据。为空时，则显示所有数据。
                searchList = new ArrayList<>();
                if (!newText.isEmpty()) {
                    for (BrandInfoModel item : combineList) {
                        if ((item.getEn_name().toLowerCase().contains(newText.toLowerCase()) == true) || (item.getName().indexOf(newText) != -1)) {
                            searchList.add(item);
                        }
                    }
                    if (searchList.isEmpty()) {
                        adapter.setMlist(searchList);
                        adapter.notifyDataSetChanged();
                        imageView.setVisibility(View.VISIBLE);
                    } else {
                        adapter.setMlist(searchList);
                        adapter.notifyDataSetChanged();
                        imageView.setVisibility(View.INVISIBLE);
                    }
                } else {
                    adapter.setMlist(combineList);
                    adapter.notifyDataSetChanged();
                    imageView.setVisibility(View.INVISIBLE);
                }
                return false;
            }
        });
        initSider();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        listView = findViewById(R.id.listView);
        sideBar = findViewById(R.id.sideBar);
        searchView = findViewById(R.id.searchView);
        imageView = findViewById(R.id.imageView);
    }

    /**
     * 初始化sider事件
     */
    private void initSider() {
        sideBar.setOnIndexChoiceChangedListener(new SideBar.OnIndexChoiceChangedListener() {
            @Override
            public void onIndexChoiceChanged(String s) {
                char indexName = s.charAt(0);
                int lastIndex = 0;
                for (int i = 1; i < combineList.size(); i++) {
                    char currentIndexName = combineList.get(i).getEn_name().charAt(0);
                    if (currentIndexName >= indexName) {
                        if (currentIndexName == indexName) {
                            listView.setSelection(i);
                            if (currentIndexName == '#') {
                                listView.setSelection(0);
                            }
                        } else {
                            listView.setSelection(lastIndex);
                        }
                        break;
                    }
                    if (combineList.get(i - 1).getEn_name().charAt(0) != currentIndexName) {
                        lastIndex = i;
                    }
                }
            }
        });
    }

    /**
     * 点击品牌后显示型号
     *
     * @param brandInfoModel
     */
    private void showModel(String[] brandInfoModel, String enName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(enName + "型号" + "(" + brandInfoModel.length + "种)")
                .setItems(brandInfoModel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //TODO
                    }
                });
        builder.create().show();
    }

    /**
     * 整合品牌数据和品牌名字的数据
     *
     * @param brandList
     * @param brandNameList
     */
    private void combine(final List<BrandModel.Brand> brandList, final List<BrandNameModel.BrandName> brandNameList) {
        for (BrandModel.Brand brandItem : brandList) {
            for (BrandNameModel.BrandName brandNameItem : brandNameList) {
                if (brandItem.getBrand_id().equals(brandNameItem.getId())) {
                    BrandInfoModel brandInfoModel = new BrandInfoModel();
                    brandInfoModel.setDeviceTypeId(brandItem.getDevice_type_id());
                    brandInfoModel.setBrandId(brandItem.getBrand_id());
                    brandInfoModel.getRemoteId().add(brandItem.getRemote_id());
                    brandInfoModel.setRank(brandItem.getRank());
                    brandInfoModel.setEn_name(brandNameItem.getEn_name());
                    brandInfoModel.setName(brandNameItem.getName());
                    char index = brandNameItem.getEn_name().charAt(0);
                    if ((index >= 'A' && index <= 'Z') || (index >= 'a' && index <= 'z')) {
                        brandInfoModel.setIndex(brandNameItem.getEn_name().substring(0, 1));
                    } else {
                        brandInfoModel.setIndex("#");
                    }
                    brandInfoModelList.add(brandInfoModel);
                }
            }
        }
        for (int i = 0; i < brandInfoModelList.size(); i++) {
            if (brandInfoMap.get(brandInfoModelList.get(i).getBrandId()) == null) {
                brandInfoMap.put(brandInfoModelList.get(i).getBrandId(), brandInfoModelList.get(i));
            } else {
                brandInfoMap.get(brandInfoModelList.get(i).getBrandId()).getRemoteId().add(brandInfoModelList.get(i).getRemoteId().toString().replaceAll("\\[|]", ""));
            }
        }
        for (BrandInfoModel value : brandInfoMap.values()) {
            combineList.add(value);
        }
        Collections.sort(combineList, new Comparator<BrandInfoModel>() {
            @Override
            public int compare(BrandInfoModel t1, BrandInfoModel t2) {
                return t1.getEn_name().compareTo(t2.getEn_name());
            }
        });

    }

    /**
     * Json解析数据品牌名字数据
     *
     * @param brandNameInfo
     */
    private void parseJson(String brandNameInfo) {

        Gson gson = new Gson();
        BrandNameModel brandNameModel = gson.fromJson(brandNameInfo, BrandNameModel.class);
        for (BrandNameModel.BrandName brandNameItem : brandNameModel.getIrBrand()) {
            brandNameList.add(brandNameItem);
        }
    }

    /**
     * Json解析品牌数据并构建匹配上一级所选择的设备的品牌数据
     *
     * @param brand
     * @param deviceId
     */
    private void parseJsonAndSelectBrand(final String brand, final String deviceId) {

        Gson gson = new Gson();
        final BrandModel brandModel = gson.fromJson(brand, BrandModel.class);
        for (BrandModel.Brand brandItem : brandModel.getIrBrandRemoteRel()) {
            if (brandItem.getDevice_type_id().equals(deviceId)) {
                brandList.add(brandItem);
            }
        }

    }

    /**
     * 读取品牌的数据
     *
     * @return
     */
    private String loadInfo() {
        return ReadTXT.load(this, "IrBrand.txt");
    }

    /**
     * 读取品牌名字的数据
     *
     * @return
     */
    private String load() {
        return ReadTXT.load(this, "IrBrandRemoteRel.txt");
    }

    @Override
    public void onClick(View view) {

    }
}
