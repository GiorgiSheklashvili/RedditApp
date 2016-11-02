package com.example.gio.redditapp.Response;

/**
 * Created by Gio on 11/1/2016.
 */

public class ChildData {
    Preview preview;
    String title,url;

    public Preview getPreview() {
        return preview;
    }

    public void setPreview(Preview preview) {
        this.preview = preview;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
