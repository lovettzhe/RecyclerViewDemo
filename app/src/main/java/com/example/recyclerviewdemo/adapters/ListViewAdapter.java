package com.example.recyclerviewdemo.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerviewdemo.R;
import com.example.recyclerviewdemo.beans.ItemBean;

import java.util.List;

/**
 * ListView
 * <p>
 * Created by YaoRuiheng on 2021/6/29.
 */
public class ListViewAdapter extends RecyclerViewBaseAdapter {

    /**
     * 普通条目类型
     */
    public static final int TYPE_NORMAL = 0;
    /**
     * 加载更多
     */
    public static final int TYPE_LOADING_MORE = 1;
    private OnPullRefreshListener mListener;

    public ListViewAdapter(List<ItemBean> data) {
        super(data);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = getSubView(parent, viewType);
        if (viewType == TYPE_NORMAL) {
            return new MyHolder(view);
        } else {
            return new LoadingMoreHolder(view);
        }
    }

    /**
     * 绑定holder,设置数据
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_NORMAL && holder instanceof MyHolder) {
            ((MyHolder) holder).setData(mData.get(position), position);
        } else if (getItemViewType(position) == TYPE_LOADING_MORE && holder instanceof LoadingMoreHolder) {
            ((LoadingMoreHolder) holder).update(LoadingMoreHolder.STATE_LOADING);
        }
    }

    /**
     * 定义接口
     */
    public interface OnPullRefreshListener {
        /**
         * 上拉刷新
         *
         * @param loadingMoreHolder
         */
        void onPullRefresh(LoadingMoreHolder loadingMoreHolder);
    }

    /**
     * 设置接口
     */
    public void setOnRefreshListener(OnPullRefreshListener listener) {
        this.mListener = listener;
    }

    @Override
    protected View getSubView(ViewGroup parent, int viewType) {
        View view;
        //获取条目UI,根据类型创建view
        if (viewType == TYPE_NORMAL) {
            view = View.inflate(parent.getContext(), R.layout.item_list_view, null);
        } else {
            view = View.inflate(parent.getContext(), R.layout.item_list_loading_more, null);
        }
        return view;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            //最后一个返回加载更多
            return TYPE_LOADING_MORE;
        }
        return TYPE_NORMAL;
    }

    public class LoadingMoreHolder extends RecyclerView.ViewHolder {
        public static final int STATE_LOADING = 0;
        public static final int STATE_RELOAD = 1;
        public static final int STATE_NORMAL = 2;

        private final LinearLayout mLlLoading;
        private final TextView mTvReload;

        public LoadingMoreHolder(@NonNull View itemView) {
            super(itemView);

            mLlLoading = itemView.findViewById(R.id.ll_loading);
            mTvReload = itemView.findViewById(R.id.tv_reload);
            mLlLoading.setOnClickListener(v -> {
                //加载更多
                loadMore();
            });
        }

        public void update(int state) {
            //重置控件状态
            mLlLoading.setVisibility(View.GONE);
            mTvReload.setVisibility(View.GONE);
            switch (state) {
                case STATE_LOADING:
                    mLlLoading.setVisibility(View.VISIBLE);
                    //触发加载数据
                    loadMore();
                    break;
                case STATE_RELOAD:
                    mTvReload.setVisibility(View.VISIBLE);
                    break;
                case STATE_NORMAL:
                    mLlLoading.setVisibility(View.GONE);
                    mTvReload.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
        }

        private void loadMore() {
            if (mListener != null) {
                mListener.onPullRefresh(this);
            }
        }
    }
}