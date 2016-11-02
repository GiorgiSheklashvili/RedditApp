package com.example.gio.redditapp.Response;

import java.util.ArrayList;

/**
 * Created by Gio on 11/1/2016.
 */

public class RootData {
    String modhash;
    ArrayList<Child> children;
    public String getModhash() {
        return modhash;
    }

    public void setModhash(String modhash) {
        this.modhash = modhash;
    }

    public ArrayList<Child> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<Child> children) {
        this.children = children;
    }



}
