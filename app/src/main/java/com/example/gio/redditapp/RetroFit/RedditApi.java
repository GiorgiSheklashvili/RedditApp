package com.example.gio.redditapp.RetroFit;

import com.example.gio.redditapp.Response.GifResponse;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Gio on 11/1/2016.
 */

public class RedditApi {
    public static IRetrofitService retrofitService;

    public RedditApi() {
        if (retrofitService == null) retrofitService = new RetrofitClient().getService();
    }

    public void getGifJson(Callback<GifResponse> callback) {
        Call<GifResponse> call = retrofitService.getGifJson();
        call.enqueue(callback);
    }

}
