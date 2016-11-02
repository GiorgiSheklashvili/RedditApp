package com.example.gio.redditapp;

import android.content.Context;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.gio.redditapp.Response.Child;
import com.example.gio.redditapp.Response.GifResponse;
import com.example.gio.redditapp.Response.Source;
import com.example.gio.redditapp.RetroFit.RedditApi;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements RecyclerItemClickListener {
    GifRecyclerAdapter recyclerAdapter;
    View gifFrame;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    ImageView fullScreenGif;
    int screenHeight = 0;
    int screenWidth = 0;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        screenHeight=getScreenDimens().y;
        screenWidth=getScreenDimens().x;
        gifFrame= findViewById(R.id.gif_frame);
        fullScreenGif=(ImageView) findViewById(R.id.full_screen_gif);
        progressBar=(ProgressBar) findViewById(R.id.progressBar);
        recyclerView=(RecyclerView) findViewById(R.id.gifRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerAdapter=new GifRecyclerAdapter(this,this);
        recyclerView.setAdapter(recyclerAdapter);
        RedditApi redditApi=new RedditApi();
        redditApi.getGifJson(callback);
        setSupportActionBar();
        recyclerView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                scrollImages();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (gifFrame.getVisibility() == View.VISIBLE) {
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.scale_in);
            animation.setInterpolator(new OvershootInterpolator());
            gifFrame.startAnimation(animation);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    gifFrame.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
            return;
        }
        super.onBackPressed();
    }

    public void adjustImage(View v, View image) {
        int[] coords = new int[2];
        v.getLocationOnScreen(coords);
        float distanceFromCenter = screenHeight * 0.5f - (coords[1]);
        float difference = image.getHeight() - (v.getHeight());
        float move = (distanceFromCenter / screenHeight) * difference;
        float y = -(difference * 0.5f) + move;
        if (y > 0) y = 0;
        image.setY(y);
    }
    public void scrollImages() {
        LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int first = manager.findFirstVisibleItemPosition();
        int last = manager.findLastVisibleItemPosition();
        for (int i = first; i <= last; i++) {
            View v = manager.findViewByPosition(i);
            if (v == null)
                continue;
            ImageView image = (ImageView) v.findViewById(R.id.gif_image);
            adjustImage(v, image);
        }
    }
    protected void setSupportActionBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
    Callback<GifResponse> callback = new Callback<GifResponse>() {
        @Override
        public void onResponse(Call<GifResponse> call, Response<GifResponse> response) {

            ArrayList<Child> children = new ArrayList<>();
            for (Child child : response.body().getRootData().getChildren()) {
                if (child.getChildData().getPreview() != null) {
                    if (child.getChildData().getPreview().getImages().get(0).getVariants().getGif() != null)
                        children.add(child);
                }
            }
            recyclerAdapter.setChildren(children);
            recyclerAdapter.notifyDataSetChanged();
        }

        @Override
        public void onFailure(Call<GifResponse> call, Throwable t) {
            Toast.makeText(MainActivity.this, "Something went wrong :/", Toast.LENGTH_SHORT).show();
        }
    };
    private Point getScreenDimens() {
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Point p = new Point();
        wm.getDefaultDisplay().getSize(p);
        return p;
    }

    @Override
    public void onItemClick(final Source gifUrl) {
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.scale_in);
        animation.setInterpolator(new OvershootInterpolator());
        gifFrame.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        fullScreenGif.startAnimation(animation);
        Ion.with(this).load(gifUrl.getUrl()).intoImageView(fullScreenGif).setCallback(new FutureCallback<ImageView>() {
            @Override
            public void onCompleted(Exception e, ImageView result) {
            progressBar.setVisibility(View.GONE);
                ViewGroup.LayoutParams imageParams=fullScreenGif.getLayoutParams();
                ViewGroup.LayoutParams frameParams=gifFrame.getLayoutParams();
                float ratio=screenWidth/gifUrl.getWidth();
                imageParams.height=(int)ratio*gifUrl.getHeight();

                frameParams.width=imageParams.width;
                frameParams.height=imageParams.height;

                gifFrame.setLayoutParams(frameParams);
                fullScreenGif.setLayoutParams(imageParams);
            }
        });

    }
}
