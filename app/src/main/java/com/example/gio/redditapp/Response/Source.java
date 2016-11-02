package com.example.gio.redditapp.Response;

public class Source {
    String url;
    int width,height;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getUrl() {
        return url.replaceAll("&amp;", "&");
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
