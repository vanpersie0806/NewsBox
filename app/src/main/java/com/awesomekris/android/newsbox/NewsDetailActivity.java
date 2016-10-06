package com.awesomekris.android.newsbox;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.squareup.picasso.Picasso;

public class NewsDetailActivity extends AppCompatActivity {

//    public static final String HEADLINE = "HEADLINE";
//    public static final String PUBLICATION_DATE = "PUBLICATION_DATE";
//    public static final String TRAIL_TEXT = "TRAIL_TEXT";
//    public static final String THUMBNAIL = "THUMBNAIL";
//    public static final String BODAY_TEXT_SUMMARY = "BODY_TEXT_SUMMARY";
    private ImageView mThumbnailView;
    private String mHeadline;
//    private String mShortUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mThumbnailView = (ImageView) findViewById(R.id.detail_thumbnail);
        String[] detailNews = getIntent().getStringArrayExtra(NewsDetailActivityFragment.NEWS_DETAIL);
        mHeadline = detailNews[0];
        String mThumbnail = detailNews[3];
        Picasso.with(this).load(mThumbnail).into(mThumbnailView);
//        mShortUrl = detailNews[5];
//        Uri uri = Uri.parse(mShortUrl).buildUpon().build();
//        getSupportActionBar().setTitle(mHeadline);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        final Intent shareIntent = ShareCompat.IntentBuilder.from(this)
                .setType("text/plain")
                .setText(mHeadline)
                .getIntent();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(Intent.createChooser(shareIntent,Intent.ACTION_SEND));
                Snackbar.make(view, "Share news with friends!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Tracker tracker = ((MyApplication)getApplication()).getTracker();
        tracker.setScreenName("News Detail Screen");
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    public Intent getParentActivityIntent() {
        return super.getParentActivityIntent().addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    }
}
