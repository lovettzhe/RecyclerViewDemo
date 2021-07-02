package com.example.recyclerviewdemo.activities;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerviewdemo.R;
import com.example.recyclerviewdemo.adapters.MultiTypeAdapter;
import com.example.recyclerviewdemo.beans.MultiTypeBean;
import com.example.recyclerviewdemo.utils.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 多种条目类型
 */
public class MultiTypeActivity extends AppCompatActivity {

    private static final String TAG = "MultiTypeActivity";
    private RecyclerView mRvMultiType;
    private List<MultiTypeBean> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_type);

        mRvMultiType = findViewById(R.id.rv_multi_type);
        //准备数据
        initData();
        //创建和设置布局管理器
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRvMultiType.setLayoutManager(manager);
        //创建适配器
        MultiTypeAdapter adapter = new MultiTypeAdapter(mData);
        //设置适配器
        mRvMultiType.setAdapter(adapter);
    }

    /**
     * 准备数据
     */
    private void initData() {
        mData = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < Data.icons.length; i++) {
            MultiTypeBean data = new MultiTypeBean();
            data.pic = Data.icons[i];
            data.type = random.nextInt(3);
            Log.d(TAG, "type == " + data.type);
            mData.add(data);
        }
    }
}