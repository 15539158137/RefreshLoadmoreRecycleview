package com.dxxx.test;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.dxxx.refreshloadmorerecycleview.recycleview.RefreshRecycleview;
import com.dxxx.refreshloadmorerecycleview.recycleview.Refresh_Loadmore_Layout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Handler handler;
    List<BaseBean> all;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler = new Handler();
        final Refresh_Loadmore_Layout refresh_loadmore_layout = (Refresh_Loadmore_Layout) findViewById(R.id.refresh);
        all = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            all.add(new BaseBean());
        }
        final SimpleRecycleviewAdater simpleRecycleviewAdater = new SimpleRecycleviewAdater(all, MainActivity.this);
        refresh_loadmore_layout.setAdapter(simpleRecycleviewAdater);
        refresh_loadmore_layout.setItemDecoration(20);
        refresh_loadmore_layout.setRefreshTouchEvent(new RefreshRecycleview.RefreshTouchEvent() {
            @Override
            public void onRefrshStart() {
                Log.e("refreshRecycleview", "onRefrshStart");
            }

            @Override
            public void onRefreshing() {
                Log.e("refreshRecycleview", "onRefreshing");
            }

            @Override
            public void onRefresh() {

                Log.e("refreshRecycleview", "onRefresh");
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("发送关闭", "===");
                        refresh_loadmore_layout.stopRefreshOrLoadmore();
                        all.remove(0);
                        simpleRecycleviewAdater.notifyDataSetChanged();
                    }
                }, 2000);

            }

            @Override
            public void onLoadmoreStart() {

                Log.e("refreshRecycleview", "onLoadmoreStart");

            }

            @Override
            public void onLoading() {
                Log.e("refreshRecycleview", "onLoading");

            }

            @Override
            public void onLoadmore() {
                Log.e("refreshRecycleview", "onLoadmore");


                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refresh_loadmore_layout.stopRefreshOrLoadmore();
                        all.add(new BaseBean());
                        simpleRecycleviewAdater.notifyDataSetChanged();
                    }
                }, 2000);

            }
        });

    }
}
