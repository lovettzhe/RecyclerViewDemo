package com.example.recyclerviewdemo.adapters;

import android.view.View;
import android.view.ViewGroup;

import com.example.recyclerviewdemo.R;
import com.example.recyclerviewdemo.beans.ItemBean;

import java.util.List;

/**
 * StaggerView
 * <p>
 * Created by YaoRuiheng on 2021/7/1.
 */
public class StaggerViewAdapter extends RecyclerViewBaseAdapter {
    public StaggerViewAdapter(List<ItemBean> data) {
        super(data);
    }

    @Override
    protected View getSubView(ViewGroup parent, int viewType) {
        return View.inflate(parent.getContext(), R.layout.item_stagger_view, null);
    }
}
