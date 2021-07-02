package com.example.recyclerviewdemo.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.recyclerviewdemo.R;
import com.example.recyclerviewdemo.adapters.GridViewAdapter;
import com.example.recyclerviewdemo.adapters.ListViewAdapter;
import com.example.recyclerviewdemo.adapters.RecyclerViewBaseAdapter;
import com.example.recyclerviewdemo.adapters.StaggerViewAdapter;
import com.example.recyclerviewdemo.beans.ItemBean;
import com.example.recyclerviewdemo.utils.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static androidx.recyclerview.widget.StaggeredGridLayoutManager.HORIZONTAL;
import static androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private RecyclerView mRv;
    private List<ItemBean> mData;
    private RecyclerViewBaseAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化控件
        initView();
        //初始化数据
        initData();
        //默认显示样式
        showList(true, false);
        //初始化监听事件
        initListener();
        //处理下拉刷新事件
        handleDropDownRefresh();
    }

    /**
     * 处理下拉刷新事件
     */
    private void handleDropDownRefresh() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.purple_200, R.color.teal_200, R.color.design_default_color_error);
        mSwipeRefreshLayout.setEnabled(true);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            /**
             * 执行刷新数据的操作
             * 下拉时调用此方法,由于主线程不能执行耗时操作,需要使用子线程
             */
            @Override
            public void onRefresh() {
                //添加一条数据
                ItemBean data = new ItemBean();
                data.title = "我是新添加的数据...";
                data.icon = R.drawable.pineapple;
                mData.add(0, data);
                new Handler().postDelayed(() -> {
                    mAdapter.notifyDataSetChanged();
                    mSwipeRefreshLayout.setRefreshing(false);
                }, 3000);
            }
        });
    }

    /**
     * 初始化事件
     */
    private void initListener() {
        mAdapter.setOnItemClickListener(position -> {
            //处理条目点击事件
            Toast.makeText(MainActivity.this, "你点击的是第 " + position + " 个条目", Toast.LENGTH_SHORT).show();
        });

        //处理上拉加载更多
        if (mAdapter instanceof ListViewAdapter) {
            ((ListViewAdapter) mAdapter).setOnRefreshListener(loadingMoreHolder -> new Handler().postDelayed(() -> {
                Random random = new Random();
                if (random.nextInt() % 2 == 0) {
                    ItemBean data = new ItemBean();
                    data.title = "我是新添加的数据";
                    data.icon = R.drawable.grape;
                    mData.add(data);
                    mAdapter.notifyDataSetChanged();
                    loadingMoreHolder.update(ListViewAdapter.LoadingMoreHolder.STATE_NORMAL);
                } else {
                    loadingMoreHolder.update(ListViewAdapter.LoadingMoreHolder.STATE_RELOAD);
                }
            }, 1000));
        }
    }

    /**
     * 初始化控件
     */
    private void initView() {
        mRv = findViewById(R.id.recycler_view);
        mSwipeRefreshLayout = findViewById(R.id.sr_layout);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        //List<DataBean> ---> Adapter ---> setAdapter ---> showData
        //创建数据集合
        mData = new ArrayList<>();
        for (int i = 0; i < Data.icons.length; i++) {
            ItemBean data = new ItemBean();
            data.icon = Data.icons[i];
            data.title = "我是第 " + i + " 个条目";
            //将数据添加到集合中
            mData.add(data);
        }
    }

    /**
     * 创建菜单
     *
     * @param menu
     *
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * 菜单响应事件
     *
     * @param item
     *
     * @return
     */
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            //ListView
            case R.id.list_view_vertical_standard:
                showList(true, false);
                Log.d(TAG, "点击了ListView中的垂直标准");
                break;
            case R.id.list_view_vertical_reverse:
                showList(true, true);
                Log.d(TAG, "点击了ListView中的垂直反向");
                break;
            case R.id.list_view_horizontal_standard:
                showList(false, false);
                Log.d(TAG, "点击了ListView中的水平标准");
                break;
            case R.id.list_view_horizontal_reverse:
                showList(false, true);
                Log.d(TAG, "点击了ListView中的水平反向");
                break;
            //GridView
            case R.id.grid_view_vertical_standard:
                showGrid(true, false);
                Log.d(TAG, "点击了GridView中的垂直标准");
                break;
            case R.id.grid_view_vertical_reverse:
                showGrid(true, true);
                Log.d(TAG, "点击了GridView中的垂直反向");
                break;
            case R.id.grid_view_horizontal_standard:
                showGrid(false, false);
                Log.d(TAG, "点击了GridView中的水平标准");
                break;
            case R.id.grid_view_horizontal_reverse:
                showGrid(false, true);
                Log.d(TAG, "点击了GridView中的水平反向");
                break;
            //StaggerView
            case R.id.stagger_view_vertical_standard:
                showStagger(true, false);
                Log.d(TAG, "点击了StaggerView中的垂直标准");
                break;
            case R.id.stagger_view_vertical_reverse:
                showStagger(true, true);
                Log.d(TAG, "点击了StaggerView中的垂直反向");
                break;
            case R.id.stagger_view_horizontal_standard:
                showStagger(false, false);
                Log.d(TAG, "点击了StaggerView中的水平标准");
                break;
            case R.id.stagger_view_horizontal_reverse:
                showStagger(false, true);
                Log.d(TAG, "点击了StaggerView中的水平反向");
                break;
            //MultiType
            case R.id.multi_type:
                Intent intent = new Intent(MainActivity.this, MultiTypeActivity.class);
                startActivity(intent);
                Log.d(TAG, "点击多种条目类型");
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 显示样式为StaggerView
     */
    private void showStagger(boolean isVertical, boolean isReverse) {
        //设置布局管理器 垂直or水平
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, isVertical ? VERTICAL : HORIZONTAL);
        //设置标准or反向
        manager.setReverseLayout(isReverse);
        mRv.setLayoutManager(manager);
        //创建适配器
        mAdapter = new StaggerViewAdapter(mData);
        //设置适配器
        mRv.setAdapter(mAdapter);
        initListener();
    }

    /**
     * 显示样式为GridView
     */
    private void showGrid(boolean isVertical, boolean isReverse) {
        //设置布局管理器
        GridLayoutManager manager = new GridLayoutManager(this, 3);
        //设置垂直or水平
        manager.setOrientation(isVertical ? RecyclerView.VERTICAL : RecyclerView.HORIZONTAL);
        //设置标准or反向
        manager.setReverseLayout(isReverse);
        mRv.setLayoutManager(manager);
        //创建适配器
        mAdapter = new GridViewAdapter(mData);
        //设置适配器
        mRv.setAdapter(mAdapter);
        initListener();
    }

    /**
     * 显示样式为ListView
     */
    private void showList(boolean isVertical, boolean isReverse) {
        //设置布局管理器
        LinearLayoutManager manager = new LinearLayoutManager(this);
        //设置垂直or水平
        manager.setOrientation(isVertical ? RecyclerView.VERTICAL : RecyclerView.HORIZONTAL);
        //设置标准or反向
        manager.setReverseLayout(isReverse);
        mRv.setLayoutManager(manager);
        //创建适配器
        mAdapter = new ListViewAdapter(mData);
        //RecyclerView中设置adapter
        mRv.setAdapter(mAdapter);
        initListener();
    }
}