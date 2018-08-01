package com.dxxx.refreshloadmorerecycleview.recycleview;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dxxx.refreshloadmorerecycleview.R;

public class Refresh_Loadmore_Layout extends FrameLayout {
    boolean isRefrsh;
    boolean isLoadmore;
    RefreshRecycleview refreshRecycleview;
    RefreshRecycleview.RefreshTouchEvent refreshTouchEvent;
    RecyclerView.Adapter adapter;

    public void setAdapter(RecyclerView.Adapter adapter) {
        this.adapter = adapter;
        refreshRecycleview.setAdapter(adapter);
    }

    public void setRefreshTouchEvent(RefreshRecycleview.RefreshTouchEvent refreshTouchEvent) {
        this.refreshTouchEvent = refreshTouchEvent;
    }

    RelativeLayout heard;
    RelativeLayout foot;
    TextView text_foot;
    TextView text_heard;
//是否需要加载更多的数值
    boolean needLoadMore;
    public Refresh_Loadmore_Layout(@NonNull Context context) {
        super(context);
    }

    public Refresh_Loadmore_Layout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray=context.obtainStyledAttributes(attrs,R.styleable.RefreshRecycleview);
        needLoadMore=typedArray.getBoolean(R.styleable.RefreshRecycleview_isNeedLoadMore,true);
        initView();
    }

    public Refresh_Loadmore_Layout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray=context.obtainStyledAttributes(attrs,R.styleable.RefreshRecycleview);
        needLoadMore=typedArray.getBoolean(R.styleable.RefreshRecycleview_isNeedLoadMore,true);
        initView();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Refresh_Loadmore_Layout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        TypedArray typedArray=context.obtainStyledAttributes(attrs,R.styleable.RefreshRecycleview);
        needLoadMore=typedArray.getBoolean(R.styleable.RefreshRecycleview_isNeedLoadMore,true);
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.refresh_loadmore_layout, this, true);
        text_foot = view.findViewById(R.id.text_foot);
        text_heard = view.findViewById(R.id.text_heard);
        heard = view.findViewById(R.id.heard);
        foot = view.findViewById(R.id.foot);
        heard.setVisibility(View.GONE);
        foot.setVisibility(View.GONE);
        // heard.setPadding(0,170,0,0);
        refreshRecycleview = view.findViewById(R.id.recycle);
        refreshRecycleview.addItemDecoration(new RecycleviewDic(10));
        //设置是否需要加载更多
            refreshRecycleview.setNeedLoadMore(needLoadMore);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        refreshRecycleview.setLayoutManager(linearLayoutManager);

        refreshRecycleview.setRefreshTouchEvent(new RefreshRecycleview.RefreshTouchEvent() {
            @Override
            public void onRefrshStart() {
                Log.e("refreshRecycleview", "onRefrshStart");
                heard.setVisibility(View.VISIBLE);
                text_heard.setText("开始刷新");
                foot.setVisibility(View.GONE);
                refreshTouchEvent.onRefrshStart();
            }

            @Override
            public void onRefreshing() {
                text_heard.setText("松手刷新");
                Log.e("refreshRecycleview", "onRefreshing");
                refreshTouchEvent.onRefreshing();
            }

            @Override
            public void onRefresh() {
                text_heard.setText("正在刷新...");
                Log.e("refreshRecycleview", "onRefresh");
                isRefrsh = true;
                refreshTouchEvent.onRefresh();

            }

            @Override
            public void onLoadmoreStart() {

                Log.e("refreshRecycleview", "onLoadmoreStart");
                heard.setVisibility(View.GONE);
                foot.setVisibility(View.VISIBLE);
                text_foot.setText("开始加载");
                refreshTouchEvent.onLoadmoreStart();
            }

            @Override
            public void onLoading() {
                Log.e("refreshRecycleview", "onLoading");
                text_foot.setText("松手加载");
                refreshTouchEvent.onLoading();
            }

            @Override
            public void onLoadmore() {
                Log.e("refreshRecycleview", "onLoadmore");
                text_foot.setText("正在加载...");
                isLoadmore = true;
                refreshTouchEvent.onLoadmore();

            }
        });
    }

    public void stopRefreshOrLoadmore() {
        Log.e("收到关闭", "==");
        if (isRefrsh) {
            Log.e("收到关闭111", "==");
            heard.setVisibility(GONE);
            refreshRecycleview.scrollToPosition(0);
        }
        if (isLoadmore) {
            Log.e("收到关闭222", "==");
            foot.setVisibility(GONE);
            refreshRecycleview.scrollToPosition(adapter.getItemCount()-1);
        }
    }
}
