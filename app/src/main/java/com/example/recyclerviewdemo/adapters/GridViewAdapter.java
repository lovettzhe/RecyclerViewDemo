package com.example.recyclerviewdemo.adapters;

import android.view.View;
import android.view.ViewGroup;

import com.example.recyclerviewdemo.R;
import com.example.recyclerviewdemo.beans.ItemBean;

import java.util.List;

/**
 * GridView
 * <p>
 * Created by YaoRuiheng on 2021/7/1.
 */
public class GridViewAdapter extends RecyclerViewBaseAdapter {

    public GridViewAdapter(List<ItemBean> data) {
        super(data);
    }

    @Override
    protected View getSubView(ViewGroup parent, int viewType) {
        //获取条目UI
        return View.inflate(parent.getContext(), R.layout.item_grid_view, null);
    }
}