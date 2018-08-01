package com.dxxx.refreshloadmorerecycleview.recycleview;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class RecycleviewDic  extends RecyclerView.ItemDecoration{
    private int space;

    public RecycleviewDic(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if(parent.getChildPosition(view) != 0){
            outRect.top = space;
        }else {

        }}
    }
