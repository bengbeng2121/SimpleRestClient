package com.codepath.apps.restclienttemplate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

/**
 * Created by taq on 29/10/2016.
 */

public class HomeActivity extends AppCompatActivity {

    private TweetsAdapter mTweetsAdapter;

    @BindView(R.id.rvTweets)
    RecyclerView rvTweets;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        setUpViews();
        fetchTweets();
    }

    private void setUpViews() {
        mTweetsAdapter = new TweetsAdapter(this);
        rvTweets.setAdapter(mTweetsAdapter);
        rvTweets.setLayoutManager(new LinearLayoutManager(this));
    }

    private void fetchTweets() {
        RestApplication.getRestClient().getHomeTimeline(1, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                List<Tweet> tweets = RestApplication.GSON.fromJson(response.toString(),
                        new TypeToken<List<Tweet>>() {}.getType());
                mTweetsAdapter.setTweets(tweets);
            }
        });
    }
}
