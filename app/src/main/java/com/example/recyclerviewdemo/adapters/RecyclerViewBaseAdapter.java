package com.example.recyclerviewdemo.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerviewdemo.R;
import com.example.recyclerviewdemo.beans.ItemBean;

import java.util.List;

/**
 * BaseAdapter
 * <p>
 * Created by YaoRuiheng on 2021/7/1.
 */
public abstract class RecyclerViewBaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected final List<ItemBean> mData;

    public RecyclerViewBaseAdapter(List<ItemBean> data) {
        this.mData = data;
    }

    /**
     * 编写回调的步骤
     * 1.创建接口
     * 2.定义接口内部方法
     * 3.外部设置接口
     * 4.接口方法的调用
     */
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        //设置监听,即设置一个回调的接口
        this.mOnItemClickListener = listener;
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        private final ImageView mIcon;
        private final TextView mTitle;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            //初始化控件
            mIcon = itemView.findViewById(R.id.iv_icon);
            mTitle = itemView.findViewById(R.id.tv_title);
            itemView.setOnClickListener(v -> {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(mPosition);
                }
            });
        }

        private int mPosition;

        /**
         * 设置数据
         *
         * @param itemBean
         */
        public void setData(ItemBean itemBean, int position) {
            this.mPosition = position;
            //设置数据
            mIcon.setImageResource(itemBean.icon);
            mTitle.setText(itemBean.title);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = getSubView(parent, viewType);
        return new MyHolder(view);
    }

    protected abstract View getSubView(ViewGroup parent, int viewType);

    /**
     * 绑定holder,设置数据
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((MyHolder) holder).setData(mData.get(position), position);
    }

    /**
     * 返回条目的个数
     *
     * @return
     */
    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        }
        return 0;
    }
}