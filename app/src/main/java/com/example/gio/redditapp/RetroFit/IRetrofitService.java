package com.example.gio.redditapp.RetroFit;

import com.example.gio.redditapp.GifRecyclerAdapter;
import com.example.gio.redditapp.Response.GifResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Gio on 11/1/2016.
 */

public interface IRetrofitService {
    String Base="r/";

    @GET(Base+"gif/.json")
    Call<GifResponse> getGifJson();
}
