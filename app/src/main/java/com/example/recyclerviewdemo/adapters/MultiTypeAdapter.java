package com.example.recyclerviewdemo.adapters;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerviewdemo.R;
import com.example.recyclerviewdemo.beans.MultiTypeBean;

import java.util.List;

/**
 * Created by YaoRuiheng on 2021/7/1.
 */
public class MultiTypeAdapter extends RecyclerView.Adapter {
    //定义常量标识
    public static final int TYPE_FULL_IMAGE = 0;
    public static final int TYPE_LEFT_TITLE_RIGHT_IMAGE = 1;
    public static final int TYPE_THREE_IMAGES = 2;
    private final List<MultiTypeBean> mData;

    public MultiTypeAdapter(List<MultiTypeBean> data) {
        this.mData = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_FULL_IMAGE) {
            view = View.inflate(parent.getContext(), R.layout.item_type_full_image, null);
            return new FullImageHolder(view);
        } else if (viewType == TYPE_LEFT_TITLE_RIGHT_IMAGE) {
            view = View.inflate(parent.getContext(), R.layout.item_type_left_title_right_image, null);
            return new LeftTitleRightImageHolder(view);
        } else {
            view = View.inflate(parent.getContext(), R.layout.item_type_three_images, null);
            return new ThreeImagesHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        }
        return 0;
    }

    /**
     * 根据条件返回条目类型
     *
     * @param position
     *
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        MultiTypeBean multiTypeBean = mData.get(position);
        if (multiTypeBean.type == 0) {
            return TYPE_FULL_IMAGE;
        } else if (multiTypeBean.type == 1) {
            return TYPE_LEFT_TITLE_RIGHT_IMAGE;
        } else {
            return TYPE_THREE_IMAGES;
        }
    }

    private static class FullImageHolder extends RecyclerView.ViewHolder {

        public FullImageHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    private static class LeftTitleRightImageHolder extends RecyclerView.ViewHolder {

        public LeftTitleRightImageHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    private static class ThreeImagesHolder extends RecyclerView.ViewHolder {

        public ThreeImagesHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
