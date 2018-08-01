package com.dxxx.refreshloadmorerecycleview.recycleview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class RefreshRecycleview extends RecyclerView {
    RefreshTouchEvent refreshTouchEvent;

    public void setRefreshTouchEvent(RefreshTouchEvent refreshTouchEvent) {
        this.refreshTouchEvent = refreshTouchEvent;
    }

    public interface RefreshTouchEvent {
        void onRefrshStart();

        void onRefreshing();

        void onRefresh();

        void onLoadmoreStart();

        void onLoading();

        void onLoadmore();
    }

    public RefreshRecycleview(Context context) {
        super(context);
    }

    public RefreshRecycleview(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RefreshRecycleview(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    //判断是否滚动到顶部
    public boolean isToTop() {

        RecyclerView.Adapter simpleRecycleviewAdater1 = this.getAdapter();
        if ( simpleRecycleviewAdater1.getItemCount()==0) {
            return true;
        }
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) this.getLayoutManager();
        int position = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
        if (position == 0) {
            return true;
        }
        return false;
    }

    //判断是否到底部
    public boolean isToBottom() {
        if (this.computeVerticalScrollExtent() + this.computeVerticalScrollOffset()
                >= this.computeVerticalScrollRange()) {
            return true;
        } else {
            return false;
        }
    }

    boolean isRefresh;
    boolean isLoadmore;
    double startRawY;
    double itemHeight;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(isToTop()||isToBottom()){
        if (itemHeight == 0) {
            if(this.getChildCount()==0){
                itemHeight=10;
            }else {
                itemHeight = this.getChildAt(0).getHeight();
            }

        }

        int eventType = ev.getAction();
        if (eventType == MotionEvent.ACTION_DOWN) {
            if (isRefresh || isLoadmore) {
                //已经有效
            } else {
                startRawY = ev.getRawY();
            }

        } else if (eventType == MotionEvent.ACTION_MOVE) {
            if (isRefresh || isLoadmore) {
                if (isRefresh) {
                    refreshTouchEvent.onRefreshing();
                } else {
                    refreshTouchEvent.onLoading();
                }
            } else {
                if (ev.getRawY() - startRawY > itemHeight * 1.5) {
                    isRefresh = true;
                    refreshTouchEvent.onRefrshStart();
                }
                if (startRawY - ev.getRawY() > 1.5 * itemHeight) {

                    isLoadmore = true;
                    refreshTouchEvent.onLoadmoreStart();
                }
            }


        } else if (eventType == MotionEvent.ACTION_UP) {

        } else {

        }
        }
        return super.dispatchTouchEvent(ev);

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int eventType = ev.getAction();
        if (eventType == MotionEvent.ACTION_UP) {
            if (isRefresh || isLoadmore) {
                if (isRefresh) {
                    refreshTouchEvent.onRefresh();
                } else {
                    refreshTouchEvent.onLoadmore();
                }
                isRefresh = false;
                isLoadmore = false;
                return true;
            } else {
                return super.onTouchEvent(ev);
            }
        } else {
            return super.onTouchEvent(ev);
        }

    }
}
