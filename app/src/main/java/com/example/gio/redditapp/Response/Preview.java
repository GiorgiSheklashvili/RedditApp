package com.example.gio.redditapp.Response;

import android.provider.ContactsContract;

import java.util.ArrayList;

/**
 * Created by Gio on 11/1/2016.
 */

public class Preview {
    ArrayList<Image> images;

    public ArrayList<Image> getImages() {
        return images;
    }

    public void setImages(ArrayList<Image> images) {
        this.images = images;
    }
}
