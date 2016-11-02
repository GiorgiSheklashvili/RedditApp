package com.example.gio.redditapp.Response;

/**
 * Created by Gio on 11/1/2016.
 */

public class GifResponse {
    String kind;
    RootData data;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public RootData getRootData() {
        return data;
    }

    public void setRootData(RootData rootData) {
        this.data = rootData;
    }
}
