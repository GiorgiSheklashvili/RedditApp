package com.example.gio.redditapp.Response;

import java.util.ArrayList;

/**
 * Created by Gio on 11/1/2016.
 */

public class Gif {
    private Source source;
    private ArrayList<Source> resolutions;

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public ArrayList<Source> getResolutions() {
        return resolutions;
    }

    public void setResolutions(ArrayList<Source> resolutions) {
        this.resolutions = resolutions;
    }
}
