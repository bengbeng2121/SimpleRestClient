package com.codepath.apps.restclienttemplate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.codepath.apps.restclienttemplate.adapter.EndlessScrollingListener;
import com.codepath.apps.restclienttemplate.adapter.TweetsAdapter;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

/**
 * Created by taq on 29/10/2016.
 */

public class HomeActivity extends AppCompatActivity implements ComposeFragment.ComposeFragmentListener {

    private TweetsAdapter mTweetsAdapter;
    private LinearLayoutManager mLayoutManager;
    private int mCurrentPage = 1;

    @BindView(R.id.rvTweets)
    RecyclerView rvTweets;

    @BindView(R.id.flOverlay)
    FrameLayout flOverlay;

    @BindView(R.id.tbHomeBar)
    Toolbar tbHomeBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        flOverlay.setVisibility(View.VISIBLE);
        setUpViews();
        fetchTweets();
    }

    private void setUpViews() {
        setSupportActionBar(tbHomeBar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_twitter_white);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setTitle(null);

        mTweetsAdapter = new TweetsAdapter(this);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvTweets.setAdapter(mTweetsAdapter);
        rvTweets.setLayoutManager(mLayoutManager);
        rvTweets.addOnScrollListener(new EndlessScrollingListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                mCurrentPage = page;
                fetchTweets();
            }
        });
    }

    private void fetchTweets() {
        RestApplication.getRestClient().getHomeTimeline(mCurrentPage, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                List<Tweet> tweets = RestApplication.GSON.fromJson(response.toString(),
                        new TypeToken<List<Tweet>>() {}.getType());
                if (mCurrentPage == 1) {
                    mTweetsAdapter.setTweets(tweets);
                } else {
                    mTweetsAdapter.addTweets(tweets);
                }
                flOverlay.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(HomeActivity.this, "Call api get tweets failure!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        setUpSearchView(menu.findItem(R.id.action_search));
        return super.onCreateOptionsMenu(menu);
    }

    private void setUpSearchView(MenuItem item) {
        // TODO đổi màu nút back / close sang màu trắng
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_compose:
                ComposeFragment fragment = ComposeFragment.newInstance();
                // FIXME thêm style, gõ text không thấy
                // fragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_FullScreen);
                fragment.show(getSupportFragmentManager(), "compose_fragment");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFinish(String status) {
        final MaterialDialog dialog = new MaterialDialog.Builder(this)
                .content("Posting your tweet...")
                .progress(true, 0)
                .progressIndeterminateStyle(true)
                .show();
        RestApplication.getRestClient().postStatus(status, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Tweet newTweet = RestApplication.GSON.fromJson(response.toString(), Tweet.class);
                mTweetsAdapter.addNewTweet(newTweet);
                rvTweets.scrollToPosition(0);
                dialog.hide();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                dialog.hide();
                Toast.makeText(HomeActivity.this, "Call api post tweet failure!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
