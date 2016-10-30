package com.codepath.apps.restclienttemplate.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by taq on 31/10/2016.
 */

public abstract class EndlessScrollingListener extends RecyclerView.OnScrollListener {

    private int mVisibleThreshold = 5;
    private int mCurrentPage = 1;
    private int mPreviousTotalItemCount;
    private boolean mLoading = true;
    private int mStaringPageIndex = 1;

    RecyclerView.LayoutManager mLayoutManager;

    public EndlessScrollingListener(LinearLayoutManager layoutManager) {
        this.mLayoutManager = layoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        if (mLayoutManager instanceof LinearLayoutManager) {
            int lastVisibleItemPostion = ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();
            int totalItemCount = mLayoutManager.getItemCount();
            if ( totalItemCount < mPreviousTotalItemCount) {
                mCurrentPage = mStaringPageIndex;
                mPreviousTotalItemCount = totalItemCount;
                if (totalItemCount == 0) {
                    mLoading = true;
                }
            }
            if (mLoading && totalItemCount > mPreviousTotalItemCount) {
                mLoading = false;
                mPreviousTotalItemCount = totalItemCount;
            }
            if (!mLoading && (lastVisibleItemPostion + mVisibleThreshold) > totalItemCount) {
                mLoading = true;
                mCurrentPage++;
                onLoadMore(mCurrentPage, totalItemCount);
            }
        }
    }

    public abstract void onLoadMore(int page, int totalItemsCount);
}
