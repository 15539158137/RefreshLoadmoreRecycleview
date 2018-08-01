# RefreshLoadmoreRecycleview
       <com.dxxx.refreshloadmorerecycleview.recycleview.Refresh_Loadmore_Layout
        xmlns:refreshrecycleview="http://schemas.android.com/apk/res-auto"
        refreshrecycleview:isNeedLoadMore="false"
        android:id="@+id/refresh"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        ></com.dxxx.refreshloadmorerecycleview.recycleview.Refresh_Loadmore_Layout>
        
        
        
#
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
